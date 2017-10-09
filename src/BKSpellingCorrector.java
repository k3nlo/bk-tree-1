import edu.gatech.gtri.bktree.*;
import edu.gatech.gtri.bktree.BkTreeSearcher.Match;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// BK Tree Implementation from https://github.com/gtri/bk-tree

public class BKSpellingCorrector {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        //define the distance
        Metric<String> levenshteinDistance = new Metric<String>() {
            @Override
            public int distance(String lhs, String rhs) {

                LevenshteinDistance levenshteinDistanceCalculator = new LevenshteinDistance();

                return levenshteinDistanceCalculator.levenshteinDistance(lhs, rhs);
            }
        };

        String testcase = "testcase6";

        //instantiate a bkTree as a dictionary
        MutableBkTree<String> bkTree = new MutableBkTree<>(levenshteinDistance);
        try {

            File dictionaryFile = new File("testCases/" + testcase + "/vocab.txt");
            FileReader dictionaryFileReader = new FileReader(dictionaryFile);
            BufferedReader dictionaryBufferedReader = new BufferedReader(dictionaryFileReader);
            StringBuffer dictionary = new StringBuffer();
            String definedWordPerLine;
            while ((definedWordPerLine = dictionaryBufferedReader.readLine()) != null) {
                bkTree.add(definedWordPerLine.toLowerCase());
            }
            dictionaryFileReader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


//        bkTree.addAll("berets", "carrot", "egrets", "marmot", "packet", "pilots", "purist");


        BkTreeSearcher<String> searcher = new BkTreeSearcher<>(bkTree);

        Utilities util = new Utilities();

        //read sentence.txt
        ArrayList <String> sentence = util.readSentence(testcase);

        int desiredLevenshteinDistance = util.readLevenshteinDistance(testcase);

        Map<String, ArrayList<String>> misspelledWordsCorrectionsMap = new LinkedHashMap<String, ArrayList<String>>();

        //for each word
        for (int i = 0; i < sentence.size(); i++) {

            ArrayList<String> correctionsList = new ArrayList<String>();

            String searchedWord = sentence.get(i);

            //exact match

            Set<Match<? extends String>> exactMatch = searcher.search(searchedWord, 0);

//            System.out.println(exactMatch);

            if (exactMatch.toString() != "[]"){
//                System.out.println("'"+searchedWord+ "' is in the BK Tree dictionary.");
            }

            else {

                Set<Match<? extends String>> correction = searcher.search(searchedWord, desiredLevenshteinDistance);
//                System.out.print(searchedWord+": ");
                for (Match<? extends String> match : correction) {
                    correctionsList.add(match.getMatch());
//                    System.out.print(match.getMatch()+", ");
                }
//                System.out.print("\n");

                misspelledWordsCorrectionsMap.put(searchedWord, correctionsList);
            }

        }
        util.printResults(misspelledWordsCorrectionsMap);
        util.writeResultsToFile(misspelledWordsCorrectionsMap);



        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Running Tine: "+ totalTime);
    }


}
