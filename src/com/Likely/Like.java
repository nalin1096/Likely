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
