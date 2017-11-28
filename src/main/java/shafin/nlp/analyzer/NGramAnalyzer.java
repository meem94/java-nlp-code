package shafin.nlp.analyzer;

import java.io.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import shafin.nlp.tokenizer.BanglaWordTokenizer;
import shafin.nlp.tokenizer.NoneWordTokenFilter;
import shafin.nlp.tokenizer.SentenceSpliter;

/*
 * Author : Shafin Mahmud
 * Email  : shafin.mahmud@gmail.com
 * Date	  : 02-10-2016 SUN
 */
public class NGramAnalyzer extends Analyzer {

	private final Reader reader;

	private final int minWords;
	private final int maxWords;

	public NGramAnalyzer(final Reader r, int minWords, int maxWords) {
		if (minWords > maxWords)
			throw new IllegalArgumentException("MaxWords cant be Smaller That MinWords!");

		this.reader = r;
		this.minWords = minWords;
		this.maxWords = maxWords;
	}

	/*
	 * fieldName - the name of the fields content passed to the
	 * Analyzer.TokenStreamComponents sink as a reader. e.g title, author,
	 * article
	 */
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer tokenizer = new BanglaWordTokenizer(reader);

		/*
		 * A ShingleFilter constructs shingles (token n-grams) from a token
		 * stream. In other words, it creates combinations of tokens as a single
		 * token. For example, the sentence
		 * "please divide this sentence into shingles" might be tokenized into
		 * shingles "please divide", "divide this", "this sentence",
		 * "sentence into", and "into shingles".
		 */
		ShingleFilter shingleFilter = new ShingleFilter(tokenizer, minWords, maxWords);
		// makes it false to no one word phrases outin the output.
		shingleFilter.setOutputUnigrams(true);
		// if not enough for minimum, show anyway.
		shingleFilter.setOutputUnigramsIfNoShingles(true);
 	
		TokenStream noneAlphabetFilter = new NoneWordTokenFilter(shingleFilter);
		return new TokenStreamComponents(tokenizer, noneAlphabetFilter);
	}
                  public static HashSet<String> generateNGramTokens() throws IOException {
                                    
		HashSet<String> ngrams=new HashSet<String>();  
                                    HashSet<String> list=new HashSet<String>();  
                                    for(String text :  SentenceSpliter.getSentenceTokenArrayBn()){
                                            
                                            NGramAnalyzer analyzer = new NGramAnalyzer(new StringReader(text), 2, 3);                
                                            ngrams = analyzer.getNGramTokens();
                                             for (String nn : ngrams) 
                                                    list.add(nn);
      
                                    }
                                    return list;
	}

	public HashSet<String> getNGramTokens() throws IOException {
                                    
		 HashSet<String> nGramTokens = new HashSet<String>();  

		TokenStream tokenStream = tokenStream("content", reader);
		CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

		tokenStream.reset();
		while (tokenStream.incrementToken()) {
			String term = charTermAttribute.toString();
			nGramTokens.add(term.trim());
		}
		return nGramTokens;
	}
                    
//                                  BnStopWordFilter stopWordFilter = new BnStopWordFilter();
//
//		String text = "আমি বাংলায় কথা বলি";
//		NGramAnalyzer analyzer = new NGramAnalyzer(new StringReader(text), 2, 3);
//                                    
//		HashSet<String> ngrams = analyzer.getNGramTokens();
//		analyzer.close();
//                                    
//                                    HashSet<String> set=new HashSet<String>();  
//		 for(String ngram : ngrams){ 
//                                             set.add(stopWordFilter.doesContainStopWordInBoundary(ngram)); 
//                                    }
//                                     for(String s : set){ 
//                                             System.out.println(s);
//                                    }
//		 
	public static void main(String[] args) throws IOException {
                                   
                                    FileWriter write = new FileWriter( "E:/Project300/output.txt" , false);
                                    PrintWriter print_line = new PrintWriter( write );    

                                    HashSet<String> ngrams=new HashSet<String>();  
                                    for(String text :  SentenceSpliter.getSentenceTokenArrayBn()){
                                           
                                            NGramAnalyzer analyzer = new NGramAnalyzer(new StringReader(text), 2, 3);                
                                            ngrams = analyzer.getNGramTokens();
                                             for (String nn : ngrams) {
                                                    print_line.println(nn);

                                             }
                                          
                                    }
                                    
                                    
		
                                    print_line.close();
	}

}
