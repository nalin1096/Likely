//@author - Nalin Gupta 2014065
//			Sahar Siddiqui 2014091
package com.Likely;

import java.util.List;

public class LikeAnalysis {
	FrequencyAnalysis fa;
	TimeAnalysis ta;
	
	public LikeAnalysis (List<Status> status, int start) {
		fa = new FrequencyAnalysis(status,start);
		ta = new TimeAnalysis(status, start);
	}
	
	public int analysis(Status status) {
		int likes = (int) ((0.05*ta.analysis(status)) + (0.95 *fa.analysis(status)));
		return likes;
	}
}
