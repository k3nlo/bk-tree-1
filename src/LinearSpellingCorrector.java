import java.io.*;
import java.util.*;


class LinearSpellingCorrector {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        String testcase = "testcase6";

        Utilities util = new Utilities();

        //read every line of vocab.txt
        ArrayList <String> dictionary = util.readDictionary(testcase);

        //read sentence.txt
        ArrayList <String> sentence = util.readSentence(testcase);

        //
        int desiredLevenshteinDistance = util.readLevenshteinDistance(testcase);

        //flag each word not in the dictionary

        //to store misspelled word en their correction
        Map<String, ArrayList<String>> misspelledWordsCorrectionsMap = linearSpellingCorrector(dictionary, sentence, desiredLevenshteinDistance);

        //print
        util.printResults(misspelledWordsCorrectionsMap);

        //write to text file
        util.writeResultsToFile(misspelledWordsCorrectionsMap);


        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Running Tine: "+ totalTime);

    }

    public static Map<String, ArrayList<String>> linearSpellingCorrector(ArrayList<String> dictionary, ArrayList<String> sentence, int desiredLevenshteinDistance){
        Map<String, ArrayList<String>> misspelledWordsCorrectionsMap = new LinkedHashMap<String, ArrayList<String>>();


        for (int i = 0; i < sentence.size(); i++){

            ArrayList<String> correctionsList = new ArrayList<String>();

            String searchedWord = sentence.get(i);

            if (dictionary.contains(searchedWord)){
//                System.out.println("the Word '"+searchedWord + "' is in the Dictionary.");
            }

            else {
                String misspelledWord = searchedWord;
//                System.out.println("the Word '"+searchedWord + "' is NOT in the Dictionary.");

                LevenshteinDistance levenshteinDistanceCalculator = new LevenshteinDistance();

//                int levenTest = levenshteinDistanceCalculator.computeLevenshteinDistance("algoithms", "algorithms");
//                System.out.println("Leven Test value: "+ levenTest);

                for (int j=0; j<dictionary.size(); j++){

                    int lvshtnD = levenshteinDistanceCalculator.levenshteinDistance(misspelledWord, dictionary.get(j));

                    if (lvshtnD <= desiredLevenshteinDistance){
                        //add a possible correction
                        correctionsList.add(dictionary.get(j));
//                      System.out.println("'"+misspelledWord + "' is close to '"+dictionary.get(j)+"'");
                    }

                }
                misspelledWordsCorrectionsMap.put(misspelledWord,correctionsList);
            }
        }

        return misspelledWordsCorrectionsMap;
    }

}