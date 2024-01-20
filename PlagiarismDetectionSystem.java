import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class s_40192955_detector {

    private static final int TRIE_SIZE=36;
    private static final Pattern PATTERN = Pattern.compile("[a-zA-Z0-9$_]+");
    static class TrieNode
    {
        TrieNode[] children = new TrieNode[TRIE_SIZE];
        boolean isEndOfWord;

        TrieNode(){
            isEndOfWord = false;
            for (int i = 0; i < TRIE_SIZE; i++)
                children[i] = null;
        }
    };

    static TrieNode rootPattern = new TrieNode();

    static int getIndex(char ch){
        if(ch > 96 && ch<123){
            return ch - 'a';
        }
        else if(ch > 47 && ch < 58){
            return ch - 48 + 26;
        }
        return -1;
    }
    static void insertIntoTrie(String key,TrieNode root)
    {
        int level;
        int length = key.length();
        int index;
        TrieNode node = root;

        for (level = 0; level < length; level++)
        {
            index = getIndex(key.charAt(level));
            if(index < 0){
                return;
            }
            if (node.children[index] == null)
                node.children[index] = new TrieNode();

            node = node.children[index];
        }
        node.isEndOfWord = true;
    }

    static boolean searchInTrie(String key,TrieNode root)
    {
        int level;
        int length = key.length();
        int index;
        TrieNode node = root;
        for (level = 0; level < length; level++)
        {
            index = getIndex(key.charAt(level));
            if(index < 0){
                return false;
            }
            if (node.children[index] == null)
                return false;

            node = node.children[index];
        }

        return (node.isEndOfWord);
    }

    private static void cleanTrie(TrieNode root){
        for (int i = 0; i < TRIE_SIZE; i++){
            if(root.children[i] != null){
                root.children[i].isEndOfWord = false;
                cleanTrie(root.children[i]);
            }
        }
    }

    public static  String readFile(String filePath) throws FileNotFoundException,java.io.IOException {
        String fileData = "";
        try {
            File file = new File(filePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String text = null;
            while ((text = bufferedReader.readLine()) != null)
                fileData = fileData + text + "\n";
        }catch (Exception e){
            e.printStackTrace();
        }

        return fileData;

    }

    public static List<String> getListOfWords(String fileData){
        List<String> fileDataList = new ArrayList<>(Arrays.asList(fileData.split(" ")));

        for (int i = 0; i < fileDataList.size(); i++){
            fileDataList.set(i,fileDataList.get(i).toLowerCase());
        }
        return fileDataList;
    }

    public static boolean patternSearch(String fileData, String pattern)
    {
        int hashValue1 = 0;
        int hashValue2 = 0;

        for (int i = 0; i < pattern.length(); ++i) {

            hashValue1 += getIndex(pattern.charAt(i));

            hashValue2 += getIndex(fileData.charAt(i));
        }


        int j = 0;

        for (int i = 0; i <= fileData.length() - pattern.length(); ++i) {

            if (hashValue2 == hashValue1) {
                for (j = 0; j < pattern.length(); ++j) {

                    if (pattern.charAt(j)
                            != fileData.charAt(i + j)) {
                        break;
                    }
                }
            }

            if (j == pattern.length()) {
                insertIntoTrie(pattern,rootPattern);
                return true;
            }

            if (i == fileData.length() - pattern.length())
                break;

            hashValue2 = (hashValue2 - getIndex(fileData.charAt(i)))
                    + getIndex(fileData.charAt(i + pattern.length()));
        }
        return false;
    }

    // plagiarism detection in Source Code

    public static int getLCS(final String a, final String b) {
        final int aLength = a.length();
        final int bLength = b.length();
        final int[][] dp = new int[aLength + 1][bLength + 1];
        for (int i = 1; i <= aLength; i++) {
            for (int j = 1; j <= bLength; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        return dp[aLength][bLength];
    }
    public static String removeBlockComments(String fileData) {
        final StringBuilder fileDataSB = new StringBuilder();
        final int size = fileData.length();
        int count = 0;
        for (int i = 0; i < size; ++i) {
            if ((i < (size - 1)) && (fileData.charAt(i) == '/') && (fileData.charAt(i + 1) == '*')) {
                ++count;
            } else if ((i > 0) && (fileData.charAt(i - 1) == '*') && (fileData.charAt(i) == '/')) {
                --count;
            } else if (count == 0) {
                fileDataSB.append(fileData.charAt(i));
            }
        }
        fileData = fileDataSB.toString();
        return fileData;
    }
    public static String removeSingleLineComments(String fileData) {
        final StringBuilder fileDataSB = new StringBuilder();
        final int size = fileData.length();
        for (int i = 0; i < size; ++i) {
            if ((i < (size - 1)) && (fileData.charAt(i) == '/') && (fileData.charAt(i + 1) == '/')) {
                for (++i; i < size; ++i) {
                    final String charString = String.valueOf(fileData.charAt(i));
                    if (charString.equals("\n") || charString.equals("\r")) {
                        break;
                    }
                }
            } else {
                fileDataSB.append(fileData.charAt(i));
            }
        }
        fileData = fileDataSB.toString();
        return fileData;
    }

    public static String removeMultipleSpaces(String fileData) {
        if (fileData.isEmpty()) {
            return fileData;
        }
        fileData = fileData.replaceAll("[ ]+", " ");
        fileData = fileData.replaceAll("[\n]+","\n");
        return fileData;
    }

    public static String removeComments(final String before) {
        return removeSingleLineComments(removeBlockComments(before));
    }

    public static String reprocessingFileData(String fileData){
        if(fileData.isEmpty())
            return fileData;
        fileData = removeMultipleSpaces(fileData);
        return fileData;
    }

    public static Map<String, Integer> getFrequency(final List<String> fileTokenList) {
        return fileTokenList.parallelStream().collect(Collectors.groupingBy(token -> token, Collectors.summingInt(token -> 1)));
    }

    public static double getQuadraticSumScore(final Collection<Integer> tokenCollection) {
        return tokenCollection.stream().reduce(0, (score1, score2) -> score1 + (score2 * score2));
    }

    public static List<String> getTokenList(String fileData) {
        final List<String> tokenList = new ArrayList<>();
        final Matcher match = PATTERN.matcher(fileData);
        while (match.find()) {
            tokenList.add(match.group());
        }
        return tokenList;
    }

    public static double getCosineScore(Map<String, Integer> fileFrequency1, Map<String, Integer> fileFrequency2) {
        double fileSummingScore = fileFrequency1.keySet().parallelStream().filter(fileFrequency2::containsKey)
                .collect(Collectors.summarizingDouble(key -> fileFrequency1.get(key) * fileFrequency2.get(key))).getSum();
        final double fileQuadraticSumScore1 = getQuadraticSumScore(fileFrequency1.values());
        final double fileQuadraticSumScore2 = getQuadraticSumScore(fileFrequency2.values());
        return fileSummingScore / Math.sqrt(fileQuadraticSumScore1 * fileQuadraticSumScore2);
    }

    public static boolean plagiarismDetectionUsingCosineSimilarity(String fileData1, String fileData2) {
        fileData1 = removeComments(fileData1);
        if (fileData1.isEmpty()) {
            return false;
        }
        List<String> fileTokenList1 = getTokenList(fileData1);
        if (fileTokenList1.isEmpty()) {
            return false;
        }
        fileData2 = removeComments(fileData2);
        if (fileData2.isEmpty()) {
            return false;
        }
        List<String> fileTokenList2 = getTokenList(fileData2);
        if (fileTokenList2.isEmpty()) {
            return false;
        }
        double score = getCosineScore(getFrequency(fileTokenList1), getFrequency(fileTokenList2))*100;
        //System.out.println("Matched in Source Code % : "+ score+" %");
        return score >=40?true:false;
    }

    public static boolean plagiarismDetectionUsingRabinKarp (String fileData1,String fileData2){
        fileData1 = fileData1.replaceAll("\\p{IsPunctuation}", "");
        fileData2 = fileData2.replaceAll("\\p{IsPunctuation}", "");
        List <String> fileDataList1 = getListOfWords(fileData1);
        List <String> fileDataList2 = getListOfWords(fileData2);

        int matchedCount = 0;
        String pattern = "";

        //file 1 and pattern from file 2
        for(int i = 1; i<fileDataList2.size(); i++){
            pattern = fileDataList2.get(i-1)+" "+fileDataList2.get(i);
            if(searchInTrie(pattern,rootPattern)){

                matchedCount++;
            }
            else if(patternSearch(fileData1,pattern)){
                matchedCount++;
            }
        }
        int score = matchedCount*200/(fileDataList2.size() + fileDataList1.size()-2);

        //System.out.println("Matched in Docs % : "+ score +" %");

        cleanTrie(rootPattern);

        return score>=40?true:false;
    }
    public static boolean isItProgram(String fileData){
        if(fileData.contains("import java."))
        {
            return true;
        }
        if(fileData.contains("#include")) {
            return true;
        }

        if(fileData.contains("[import ][a-z]*[\n]")){
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException{

        String fileData1 = readFile(args[0]);
        String fileData2 = readFile(args[1]);

        fileData1 = reprocessingFileData(fileData1);
        fileData2 = reprocessingFileData(fileData2);

        boolean s1 = isItProgram(fileData1);
        boolean s2 = isItProgram(fileData2);
        if(s1 == true && s2 == true){
            if (plagiarismDetectionUsingCosineSimilarity(fileData1, fileData2)) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }
        else if(s1 == true && s2!=true){
            System.out.println(0);
        }
        else if(s1 !=true && s2==true){
            System.out.println(0);
        }
        else{
            if (plagiarismDetectionUsingRabinKarp(fileData1, fileData2)) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }

        return;
    }

}

