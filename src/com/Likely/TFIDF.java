package com.Likely;

import java.util.List;

public class TFIDF {
//	public double tfidf (List<Post> posts, String status, String term) {
//		return tf(status, term) * itf(posts,term);
//	}
//	
//	public double tf (String status, String term) {
//		double count = 0;
//		String[] words = status.split("\\s+");
//		for (String word: words) {
//			if (word.equalsIgnoreCase(term)) {
//				count++;
//			}
//		}
//		return count/words.length;
//	}
//	
//	public double itf (List<Post> posts, String term) {
//		double count = 0;
//		for (int i=0;i<posts.size();i++) {
//			String[] words = posts.get(i).getStatus().split("\\s+");
//			for(String word: words) {
//				if (word.equalsIgnoreCase(term)) {
//					count++;
//					break;
//				}
//			}
//		}
//		return Math.log(posts.size() / count);
//	}
}
