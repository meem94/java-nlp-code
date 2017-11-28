package shafin.nlp.tokenizer;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import shafin.nlp.util.RegexUtil;

public class SentenceSpliter {

	public static String[] UNICODE_SPACE_CHARACTERS = { "\u0020", "\u00A0", "\u180E", "\u1680", "\u2000", "\u2001",
			"\u2002", "\u2003", "\u2004", "\u2005", "\u2006", "\u2007", "\u2008", "\u2009", "\u200A", "\u200B",
			"\u202F", "\u205F", "\u3000", "\uFEFF" };

	public static final String ENG_SPLIT_REGEX = "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?)\\s";
	public static final String BAN_SPLIT_REGEX = "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\?|৷|।|!)";

	public static  HashSet<String>  getSentenceTokenArrayBn() throws FileNotFoundException, IOException {
		String text =  new Scanner(new File("resources/input.txt")).useDelimiter("\\Z").next();
                                    HashSet<String> arr = getSentenceTokenListBn(text);
                                    return  arr;
	}

	public static HashSet<String> getSentenceTokenListBn(String text) {
		text = replaceAll(UNICODE_SPACE_CHARACTERS, " ", text);
		text = StringUtils.normalizeSpace(text);
//                                     
		LinkedList<String> list = RegexUtil.getSplittedTokens(text, BAN_SPLIT_REGEX);
		HashSet<String> tokens=new HashSet<String>();  
                                    for(String s: list)
                                    {
                                        String[] parts = s.split("[\\(|\\)|,|;|?|‘’|''|।|\\{|\\}|\\[|\\]]+");
                                        for(String ss : parts)
                                        {
                                            tokens.add(ss);
                                           
                                        }
                                    }
                                    return  tokens;
	}

	public static String replaceAll(String[] shitStrings, String replaceWith, String text) {
		StringBuffer sb = new StringBuffer(text);
		for (int i = 0; i < shitStrings.length; i++) {
			sb = new StringBuffer(sb.toString().replaceAll(shitStrings[i], replaceWith));
		}
		return sb.toString();
	}
                    public static LinkedList<String> getSentenceTokenListBn1(String text) {
		text = replaceAll(UNICODE_SPACE_CHARACTERS, " ", text);
		text = StringUtils.normalizeSpace(text);
//                                     
		LinkedList<String> list = RegexUtil.getSplittedTokens(text, BAN_SPLIT_REGEX);
		LinkedList<String> tokens=new LinkedList<>();  
                                    for(String s: list)
                                    {
                                        String[] parts = s.split("[\\(|\\)|,|;|?|‘’|''|।|\\{|\\}|\\[|\\]]+");
                                        tokens.addAll(Arrays.asList(parts));
                                    }
                                    return  tokens;
	}


	public static void main(String[] args) throws IOException {
                                    FileWriter write = new FileWriter( "E:/Project300/output.txt" , false);
                                    PrintWriter print_line = new PrintWriter( write );    
		String text =  new Scanner(new File("E:/Project300/input.txt")).useDelimiter("\\Z").next();
                           
		for (String string : getSentenceTokenListBn(text))
                                    {
//                                             String[] parts = string.split("[\\(|\\)|,|;|?|‘’|''|।|\\{|\\}|\\[|\\]]+");
//                                             for(String s : parts)
                                                print_line.println(string);                                 
                                    }
                                    print_line.close();
	}
}
