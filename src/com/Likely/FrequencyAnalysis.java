//@author - Nalin Gupta 2014065
//			Sahar Siddiqui 2014091
package com.Likely;

import java.util.HashMap;
import java.util.List;

public class FrequencyAnalysis {
	private List<Status> status;
	private TFIDF tfidf = new TFIDF();
	private HashMap <String,TermAnalysis> wordMap = new HashMap<>();
	String st;
	int start;
	
	public FrequencyAnalysis (List<Status> status, int start) {
		this.status = status;
		this.start = start;
	}
	
	public FrequencyAnalysis () {
		
	}
	
	public HashMap<String, TermAnalysis> getWordMap() {
		return wordMap;
	}

	public void setWordMap(HashMap<String, TermAnalysis> wordMap) {
		this.wordMap = wordMap;
	}

	public int analysis (Status status) {
		generateWordMap();
		double val = computeAnalysis(status.getStatus());
		return (int)val;
	}
	
	public void generateWordMap () {
		int totalLikes;
		int totalStatus;
		TermAnalysis tf;
		
		
		for (int j=21;j<status.size();j++) {
			String[] words = status.get(j).getStatus().split(" ");
			for (int i=0;i<words.length;i++) {
				if (!wordMap.containsKey(words[i])) {
					if (!status.get(j).getLikes().isEmpty()){
						totalLikes = status.get(j).getLikes().size();
						totalStatus = 1;
						tf = new TermAnalysis (totalLikes,totalStatus);
						wordMap.put(words[i], tf);
					}
					else {
						totalLikes = 0;
						totalStatus = 1;
						tf = new TermAnalysis (totalLikes,totalStatus);
						wordMap.put(words[i], tf);
					}
				
				}
				else {
					if (!status.get(j).getLikes().isEmpty()){
						totalLikes = wordMap.get(words[i]).getTotalLikes() + status.get(j).getLikes().size();
						totalStatus = wordMap.get(words[i]).getTotalStatus() + 1;
						tf = new TermAnalysis(totalLikes,totalStatus);
						wordMap.put(words[i], tf);
					}
					else {
						totalLikes = wordMap.get(words[i]).getTotalLikes();
						totalStatus = wordMap.get(words[i]).getTotalStatus() + 1;
						tf = new TermAnalysis(totalLikes,totalStatus);
						wordMap.put(words[i], tf);
					}
				}
			}
		}
	}
	
	public double computeAnalysis (String post) {
		int ctr = 0;
		double likes = 0;
		String[] words = post.split(" ");
		for (String word: words) {
			if (wordMap.containsKey(word)) {
				ctr++;
				double imp = tfidf.compute(status,post,word);
				likes = likes + (imp * wordMap.get(word).getAvgLikes());
			}
			else {
				ctr++;
			}
		}
		return likes/ctr;
	}
	
}
