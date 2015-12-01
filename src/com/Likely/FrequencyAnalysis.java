package com.Likely;

import java.util.HashMap;
import java.util.List;

public class FrequencyAnalysis {
	private List<Status> status;
	private TFIDF tfidf = new TFIDF();
	private HashMap <String,TermAnalysis> wordMap = new HashMap<>();
	
	public FrequencyAnalysis (List<Status> status) {
		this.status = status;
	}
	
	public int analysis (String post) {
		generateWordMap();
		double val = computeAnalysis(post);
		return (int)val;
	}
	
	public void generateWordMap () {
		int totalLikes;
		int totalStatus;
		int avgLikes;
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