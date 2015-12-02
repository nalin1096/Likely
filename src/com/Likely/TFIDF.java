//@author - Nalin Gupta 2014065
//			Sahar Siddiqui 2014091
package com.Likely;

import java.util.List;

public class TFIDF {
	public double compute (List<Status> st, String status, String term) {
		return tf(status, term) * itf(st,term);
	}
	
	public double tf (String status, String term) {
		double count = 0;
		String[] words = status.split("\\s+");
		for (String word: words) {
			if (word.equalsIgnoreCase(term)) {
				count++;
			}
		}
		return count/words.length;
	}
	
	public double itf (List<Status> st, String term) {
		double count = 0;
		for (int i=0;i<st.size();i++) {
			String[] words = st.get(i).getStatus().split("\\s+");
			for(String word: words) {
				if (word.equalsIgnoreCase(term)) {
					count++;
					break;
				}
			}
		}
		return Math.log(st.size() / count);
	}
}
