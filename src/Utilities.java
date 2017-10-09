import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Map;

public class Utilities {

    public static ArrayList<String> readDictionary(String testCaseStr){
        //read every line of vocab.txt, max 400 000 words of 30 characters
        ArrayList <String> dictionaryArrayList = new ArrayList<String>();

        try {

            File dictionaryFile = new File("testCases/"+ testCaseStr + "/vocab.txt");
            FileReader dictionaryFileReader = new FileReader(dictionaryFile);
            BufferedReader dictionaryBufferedReader = new BufferedReader(dictionaryFileReader);
            StringBuffer dictionary = new StringBuffer();
            String definedWordPerLine;
            while ((definedWordPerLine = dictionaryBufferedReader.readLine()) != null) {
//                dictionary.append(definedWordPerLine);
//                dictionary.append("\n");
                dictionaryArrayList.add(definedWordPerLine.toLowerCase());
            }
            dictionaryFileReader.close();


//            System.out.println("Contents of dictionary file:");
//            System.out.println(Arrays.toString(dictionaryArrayList.toArray()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionaryArrayList;
    }

    public static ArrayList <String> readSentence(String testCaseStr){
        ArrayList <String> sentenceArrayList = new ArrayList<String>();
        try {

            File sentenceFile = new File("testCases/"+ testCaseStr + "/sentence.txt");
            FileReader sentenceFileReader = new FileReader(sentenceFile);
            BufferedReader sentenceBufferedReader = new BufferedReader(sentenceFileReader);
//            StringBuffer sentenceStringBuffer = new StringBuffer();
            String sentenceLine;

            while ((sentenceLine = sentenceBufferedReader.readLine()) != null) {
                // break the line into individual words
                // the String array contains each word of the current line
                sentenceArrayList = new ArrayList<String>(Arrays.asList(sentenceLine.split(" +")));


                //to lower case
                ListIterator<String> iterator = sentenceArrayList.listIterator();
                while (iterator.hasNext())
                {
                    iterator.set(iterator.next().toLowerCase());
                }


//                sentenceStringBuffer.append(sentenceLine);
//                sentenceStringBuffer.append("\n");
            }

            sentenceFileReader.close();

//            System.out.println("Contents of sentence file:");
//            System.out.println(Arrays.toString(sentenceArrayList.toArray()));



        } catch (IOException e) {
            e.printStackTrace();
        }
        return sentenceArrayList;
    }

    public static int readLevenshteinDistance (String testCaseStr){
        int levenshteinDistance=0;
        try {

            File distanceFile = new File("testCases/"+ testCaseStr + "/MaxDistance.txt");
            FileReader distanceFileReader = new FileReader(distanceFile);
            BufferedReader distanceBufferedReader = new BufferedReader(distanceFileReader);
//            StringBuffer distanceStringBuffer = new StringBuffer();
            String distanceLine;

            while ((distanceLine = distanceBufferedReader.readLine()) != null) {
                levenshteinDistance = Integer.parseInt(distanceLine);

            }

            distanceFileReader.close();

//            System.out.print("Contents of MaxDistance file: ");
//            System.out.println(levenshteinDistance);



        } catch (IOException e) {
            e.printStackTrace();
        }
        return levenshteinDistance;
    }

    private static String arrayListToString(ArrayList<String> anArray) {
        String strArrayList ="";
        for (int i = 0; i < anArray.size(); i++) {
            if (i > 0) {
//                  add a comma space before the next correction
                strArrayList += ", ";
            }

//              add the correction
            strArrayList += anArray.get(i);


        }
        return strArrayList;
    }

    public static void writeResultsToFile(Map<String, ArrayList<String>> resultMap) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("MisspelledWords.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (resultMap.isEmpty()){
            writer.println("0");
        }
        else {
            for (Map.Entry<String, ArrayList<String>> entry : resultMap.entrySet()) {
                String mispelledWord = entry.getKey();
                ArrayList<String> correctionList = entry.getValue();
                writer.println(mispelledWord + ": " + arrayListToString(correctionList));
            }
        }
        writer.close();
    }

    public static void printResults (Map<String, ArrayList<String>> resultMap) {
        for (Map.Entry<String, ArrayList<String>> entry : resultMap.entrySet()) {
            String mispelledWord = entry.getKey();
            ArrayList<String> correctionList = entry.getValue();
            System.out.println(mispelledWord + ": " + arrayListToString(correctionList));
        }
    }

}
