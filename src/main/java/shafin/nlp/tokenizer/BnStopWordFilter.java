package shafin.nlp.tokenizer;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import org.json.JSONObject;
import org.json.JSONException;
import shafin.nlp.analyzer.BanglaWordAnalyzer;
import shafin.nlp.analyzer.NGramAnalyzer;
import shafin.nlp.util.FileHandler;

public class BnStopWordFilter {

    public static final String PROJECT_DIR = "F:/java-shafin.nlp-code/";
    public final List<String> STOP_WORDS;
    public int success_code;
    public String result;

    public BnStopWordFilter() {
        List<String> words = FileHandler.readFile("resources/stopword.txt");
        this.STOP_WORDS = new ArrayList<>();
        for (String word : words) {
            this.STOP_WORDS.add(word.trim());
        }
//		Collections.sort(this.STOP_WORDS);
//		FileHandler.writeListToFile(PROJECT_DIR + "resources/stopword.txt", this.STOP_WORDS);
    }
    public boolean doesContainStopWordInBoundary1(String ngram) {
		BanglaWordAnalyzer wordAnalyzer = new BanglaWordAnalyzer(new StringReader(ngram));
		List<String> wordTokens = wordAnalyzer.getTokenList();
		wordAnalyzer.close();

		int size = wordTokens.size();
		if (size > 0) {
			String firstWord = wordTokens.get(0);
			String lastWord = wordTokens.get(size - 1);

			if (Collections.binarySearch(STOP_WORDS, firstWord) >= 0
					| Collections.binarySearch(STOP_WORDS, lastWord) >= 0) {
				return true;
			}
			return false;
		}

		return true;
	}
    public String doesContainStopWordInBoundary(String ngram) {
        BanglaWordAnalyzer wordAnalyzer = new BanglaWordAnalyzer(new StringReader(ngram));
        List<String> wordTokens = wordAnalyzer.getTokenList();
        wordAnalyzer.close();

        int size = wordTokens.size();
        int f = 0, l = 0;
        if (size > 0) {
            String firstWord = wordTokens.get(0);
            String lastWord = wordTokens.get(size - 1);
            //System.out.print(ngram + "-> " + firstWord + " " + lastWord);

            if (Collections.binarySearch(STOP_WORDS, firstWord) >= 0) {
                if (size > 1) {
                    String[] arr = ngram.split(" ", 2);
                    ngram = arr[1];
                } else {
                    ngram = "";
                }

            }
            if (size > 1) {
                lastWord = wordTokens.get(size - 1);
//                System.out.print(ngram + "-> " + firstWord + " " + lastWord);
                if (Collections.binarySearch(STOP_WORDS, lastWord) >= 0) {
                    // System.out.print("  yes");
                    int start = ngram.lastIndexOf(lastWord);
                    ngram = ngram.substring(0, start);
                    //ngram = ngram.replaceAll(" [^ ]+$", "");
                }

            }
           // System.out.println(" = " + ngram);

        }
        return ngram;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        FileWriter write = new FileWriter("resources/output.txt", false);
        PrintWriter print_line = new PrintWriter(write);

        BnStopWordFilter stopWordFilter = new BnStopWordFilter();

//		String text = "এটি আমার মাতৃভাষা";
//		NGramAnalyzer analyzer = new NGramAnalyzer(new StringReader(text), 2, 3);
        HashSet<String> ngrams = NGramAnalyzer.generateNGramTokens();
//		analyzer.close();

        HashSet<String> set = new HashSet<String>();
        for (String ngram : ngrams) {
            set.add(stopWordFilter.doesContainStopWordInBoundary(ngram));
        }
        HashSet<String> finalSet = new HashSet<String>();
        VerbSuffixFilter filter = new VerbSuffixFilter();
        for (String s : set) {
            if (filter.doesStartOrEndsWithVerbSuffix(s) == false) {
                finalSet.add(s);
                BanglaWordAnalyzer wordAnalyzer = new BanglaWordAnalyzer(new StringReader(s));
                List<String> wordTokens = wordAnalyzer.getTokenList();
                wordAnalyzer.close();
                int size = wordTokens.size();
                String lastWord = wordTokens.get(size - 1);
               // System.out.println(s + " " + lastWord);
                String url = "http://pipilika.com:92/PipilikaStemmerAPI/process?query=" + URLEncoder.encode(lastWord, "UTF-8");

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                //add reuqest header
                con.setRequestMethod("POST");
                con.setRequestProperty("apikey", "5a0c5b67e9d0e");

                //String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
                // Send post request
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());

                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                //
                
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                String list1;
                list1 = response.toString();
                JSONObject tt44 = null;
                try {
                    tt44 = new JSONObject(list1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
            String result1;
            int success_code1;
                JSONObject pp = tt44.getJSONObject("result");
                result1 = pp.getString(lastWord);
                success_code1 = tt44.getInt("code");
                System.out.println(result1);
                print_line.println(s);
        }catch (JSONException e) {
            System.out.println("JSON e somossha");
        }
            }
            for (String ss : finalSet) //print_line.println(ss);
            {

            }
        }

        print_line.close();

    }
}
