//@author - Nalin Gupta 2014065
//			Sahar Siddiqui 2014091
package com.Likely;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;

public class Post {
	@Facebook
	private List <Like> likes = new ArrayList<>();
	@Facebook
	private String story;
	@Facebook
	private String message;
	@Facebook
	private String created_time;
	@Facebook
	private String caption;
	
	
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Like> getLikes() {
		return likes;
	}
	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
}
