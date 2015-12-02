//@author - Nalin Gupta 2014065
//			Sahar Siddiqui 2014091
package com.Likely;

import com.restfb.Facebook;

public class Like {
	@Facebook
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
