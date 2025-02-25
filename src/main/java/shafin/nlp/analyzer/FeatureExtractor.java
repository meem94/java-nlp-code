package shafin.nlp.analyzer;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import shafin.nlp.tokenizer.SentenceSpliter;
import shafin.nlp.util.RegexUtil;

/*
 * Author : Shafin Mahmud
 * Email  : shafin.mahmud@gmail.com
 * Date	  : 02-10-2016 SUN
 */
public class FeatureExtractor {

	private static final String BOUNDARY = "\\s|\\/|\\(|\\)|\\.|-|–|\\'|\"|!|,|,|\\?|;|’|‘|\\+|৷ |৷|।|—|:";
	public static final String WORD_BOUNDARY_START = "(^|" + BOUNDARY + ")";
	public static final String WORD_BOUNDARY_END = "($|" + BOUNDARY + "\\s)";

	public static final String NON_WORD_FORMING_CHARS_REGEX = "[-.,—’‘';:\\(\\)\\/\\s–]+";

	public static int getOccurrenceOrderInSentence(LinkedList<String> sentences, String phrase) {
		String wildPhrase = getWildPhraseRegex(phrase);
		for (int i = 0; i < sentences.size(); i++) {
			if (RegexUtil.containsPattern(sentences.get(i), wildPhrase)) {
				return i + 1;
			}
		}
		return 0;
	}

	public static double getNormalizedOccurrenceOrderInSentence(LinkedList<String> sentences, String phrase) {
		int length = sentences.size();
		int order = getOccurrenceOrderInSentence(sentences, phrase);
		return order / (double) length;
	}

	public static int getTermOccurrenceCount(String text, String term) {
		String wildPhrase = WORD_BOUNDARY_START + getWildPhraseRegex(term) + WORD_BOUNDARY_END;
		return RegexUtil.countMatches(text, wildPhrase);
	}

	public static String getWildPhraseRegex(String phrase) {
		BanglaWordAnalyzer wordAnalyzer = new BanglaWordAnalyzer(new StringReader(phrase));
		List<String> wordTokens = wordAnalyzer.getTokenList();
		wordAnalyzer.close();

		int size = wordTokens.size();

		StringBuffer wildPhrase = new StringBuffer();
		for (int i = 0; i < size; i++) {
			wildPhrase.append(wordTokens.get(i));
			if (i != size - 1) {
				wildPhrase.append(NON_WORD_FORMING_CHARS_REGEX);
			}
		}
                                    System.err.println(wildPhrase.toString());
		return wildPhrase.toString();
	}

	public static void main(String[] args) {
		String text = "ছোটবেলার স্মৃতি আজও আমাদের নাড়া দেয়, অতীতের সঙ্গে বর্তমানের যোগসূত্র গড়ে তোলে৷ কিন্তু সেই স্মৃতি আচমকা হারিয়ে গেলে পায়ের তলা থেকে যেন মাটি সরে যায়৷ বিজ্ঞানীরা সেই বন্ধ দরজার চাবি খোঁজার চেষ্টা করছেন৷ আমাদের স্মৃতিশক্তি না থাকলে কেমন হতো? আত্মজীবনিমূলক স্মৃতিকেই আমরা ‘মনে করা' বলি৷ সেখানে ঘটনাগুলি শুধু চিন্তা হিসেবে জমা থাকে না, তার সঙ্গে জড়িত আবেগও জুড়ে থাকে৷ এই প্রক্রিয়া সাধারণত ৩ বছর বয়স থেকে শুরু হয়৷ এবং সেটা সবসময় অন্যের সঙ্গে ভাবের আদান-প্রদানের সময় ঘটে৷ তখন নিজস্ব এক সত্তার সৃষ্টি হয়৷ আমরা তখন বুঝতে পারি, কোথা থেকে এসেছি, আমরা কে, অন্যদের থেকে আমরা কীভাবে বাকিদের থেকে আলাদা৷ জীবনের ইতিহাস হারিয়ে ফেললে সেই সত্তা সৃষ্টির পেছনের মুহূর্তগুলিও উধাও হয়ে যায়৷ এমনটা ঘটলে সবকিছু গোলমাল হয়ে যায়৷ সাবিনের বয়স তখন বিশের গোড়ায়৷ একদিন সকালে উঠে তিনি আর কিছুই মনে করতে পারলেন না৷ নিজের অতীতের ছবির দিকে তাকালেই আশা জাগে, হয়ত কিছু মনে পড়ে যাবে৷ তিনি বলেন, ‘‘শৈশব পুরোটা হারিয়ে ফেলেছি৷ এখন এ সব ছবি দেখলে কল্পনাশক্তি কাজে লাগিয়ে তার কিছু অর্থ বার করতে পারি, কিন্তু সক্রিয়ভাবে কিছুই মনে করতে পারি না৷'' সাবিনে কিন্তু অন্য অনেক কাজ করতে পারেন৷ সাইকেল চালানো, মনে মনে অঙ্ক কষা ইত্যাদি৷ জন্ম তারিখও মনে আছে৷ শুধু আত্মজীবনিমূলক স্মৃতি পুরোপুরি মুছে গেছে৷ সাবিনে বলেন, ‘‘একেবারে ফাঁকা৷ অনুভূতিও শূন্যতায় ভরা৷ অতএব কোনো আবেগই আসে না৷'' কী ঘটেছিল সাবিনের সঙ্গে? সত্যি কি তাঁর ছোটবেলার স্মৃতি মুছে দেওয়া হয়েছে? গোটা বিশ্বের বিজ্ঞানীরা অ্যামনেসিয়া বা স্মৃতিবিলোপের রহস্য ভেদ করে মানুষের স্মৃতির বিষয়টি ভালো করে বুঝতে চান৷ স্মিডার ক্লিনিকস এক আন্তর্জাতিক স্নায়ুবিজ্ঞান কেন্দ্র৷ এখানে অ্যামেনেসিয়ার স্নায়ুগত ও মানসিক প্রভাবের চিকিৎসা হয়৷ নিউরোলজিস্টরা অ্যামনেসিয়ার নিউরো-বায়োলজিকাল প্রক্রিয়ার দুর্বল অংশ খুঁজছেন৷ এমআরআই স্ক্যানারের মধ্যে সাবিনে-কে কিছু আবেগ-জাগানো ছবি দেখানো হচ্ছে৷ স্বাভাবিক মানুষ এমন ছবি দেখলে স্বাভাবিক আচরণ করে৷ কিন্তু সাবিনে-এর ক্ষেত্রে ভিন্ন ফলাফল দেখা গেল৷ মস্তিষ্কের যে অংশ আবেগ নিয়ন্ত্রণ করে, সেটি অতি সক্রিয় হয়ে উঠেছে৷ প্রত্যেকটি ছবি চরম ভীতি জাগিয়ে তুলছে৷ অর্থাৎ তথ্য ঠিকমত প্রক্রিয়াকরণ করা হচ্ছে না৷ এর একটা কারণ শৈশবের কোনো ‘ট্রমা' বা চরম দুঃখজনক ঘটনা হতে পারে৷ নিউরোলজিস্ট প্রো. রজার স্মিট বলেন, ‘‘এমন অবরুদ্ধ স্মৃতি চিকিৎসার মাধ্যমে কীভাবে উদ্ধার করা যেতে পারে, সেটা গবেষণার বিষয়৷ একটি প্রক্রিয়ায় চরম দুঃখজনক ঘটনার স্মৃতি আবার জাগিয়ে তুলে বন্ধ দরজা খোলার চেষ্টা করা হয়৷ অন্য প্রক্রিয়ায় স্মৃতিভ্রষ্ট মানুষকে নতুন করে তার জীবনের কাহিনি শেখানোর চেষ্টা হয়৷'' সাবিনে কয়েক বছর ধরে স্মৃতিভাণ্ডার ফিরে পাবার চেষ্টা করছেন৷ বার বার তিনি শৈশবের স্মৃতি হাতড়াচ্ছেন৷ অভিজ্ঞতাগুলি চিরকালের জন্য হারিয়ে যায় নি বলে তাঁর আশা৷ কোথাও যেন তালাবন্ধ রয়েছে৷ মস্তিষ্ক যেন তার উপর লিখে রেখেছে, ‘সাবধান! খুললেই বিপদ'৷";
		LinkedList<String> SENTENCES = SentenceSpliter.getSentenceTokenListBn1(text);
		String phrase = "খুললেই বিপদ";
		System.out.println(getNormalizedOccurrenceOrderInSentence(SENTENCES, phrase));
	}
}
