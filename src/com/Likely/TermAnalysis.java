//@author - Nalin Gupta 2014065
//			Sahar Siddiqui 2014091
package com.Likely;

public class TermAnalysis {
	private int totalLikes;
	private int totalStatus;
	private int avgLikes;
	
	public TermAnalysis (int totalLikes, int totalStatus) {
		this.totalLikes = totalLikes;
		this.totalStatus = totalStatus;
		this.avgLikes = totalLikes/totalStatus;
	}
	public int getTotalLikes() {
		return totalLikes;
	}
	public void setTotalLikes(int totalLikes) {
		this.totalLikes = totalLikes;
	}
	public int getTotalStatus() {
		return totalStatus;
	}
	public void setTotalStatus(int totalStatus) {
		this.totalStatus = totalStatus;
	}
	public int getAvgLikes() {
		return avgLikes;
	}
	public void setAvgLikes(int avgLikes) {
		this.avgLikes = avgLikes;
	}
}
