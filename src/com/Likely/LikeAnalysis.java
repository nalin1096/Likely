package com.Likely;

import java.util.List;

public class LikeAnalysis {
	FrequencyAnalysis fa;
	TimeAnalysis ta;
	
	public LikeAnalysis (List<Status> status) {
		fa = new FrequencyAnalysis(status);
		ta = new TimeAnalysis(status);
	}
	
	public int analysis(Status status) {
		int likes = (int) ((0.05*ta.analysis(status)) + (0.95 *fa.analysis(status.getStatus())));
		return likes;
	}
}
