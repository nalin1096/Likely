//@author - Nalin Gupta 2014065
//			Sahar Siddiqui 2014091
package com.Likely;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimeAnalysis {
	private List<Status> status = new ArrayList<>();
	private HashMap<String,TermAnalysis> timeMap = new HashMap<>();
	int start;
	
	public TimeAnalysis (List<Status> status, int start) {
		this.status = status;
		this.start = start;
	}
	public TimeAnalysis () {
		
	}
	
	public HashMap<String, TermAnalysis> getTimeMap() {
		return timeMap;
	}

	public void setTimeMap(HashMap<String, TermAnalysis> timeMap) {
		this.timeMap = timeMap;
	}

	public int analysis (Status status) {
		generateMap();
		int likes = getLikes(status);
		return likes;
	}
	
	public void generateMap () {
		int totalLikes;
		int totalStatus;
		TermAnalysis tf;
		
		for (int i=start;i<status.size();i++) {
			String time = status.get(i).getHour();
			if (!timeMap.containsKey(time)) {
				if (!status.get(i).getLikes().isEmpty()){
					totalLikes = status.get(i).getLikes().size();
					totalStatus = 1;
					tf = new TermAnalysis (totalLikes,totalStatus);
					timeMap.put(time, tf);
				}
				else {
					totalLikes = 0;
					totalStatus = 1;
					tf = new TermAnalysis (totalLikes,totalStatus);
					timeMap.put(time, tf);
				}
			
			}
			else {
				if (!status.get(i).getLikes().isEmpty()){
					totalLikes = timeMap.get(time).getTotalLikes() + status.get(i).getLikes().size();
					totalStatus = timeMap.get(time).getTotalStatus() + 1;
					tf = new TermAnalysis(totalLikes,totalStatus);
					timeMap.put(time, tf);
				}
				else {
					totalLikes = timeMap.get(time).getTotalLikes();
					totalStatus = timeMap.get(time).getTotalStatus() + 1;
					tf = new TermAnalysis(totalLikes,totalStatus);
					timeMap.put(time, tf);
				}
			}
		}
	}
	
	public int getLikes (Status status) {
		String time = status.getHour();
		if (!timeMap.containsKey(time)) {
			return 0;
		}
		else {
			return timeMap.get(time).getAvgLikes();
		}
	}
}
