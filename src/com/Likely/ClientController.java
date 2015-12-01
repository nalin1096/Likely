package com.Likely;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;

@Controller
public class ClientController {
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public ModelAndView getHomePage () {	
		List<Post> post = new ArrayList<>();
		List<Status> status = new ArrayList<>();
		JsonMapper jsonMapper = new DefaultJsonMapper();
		FrequencyAnalysis fa;
		String name;
		
		FacebookClient facebookClient = new DefaultFacebookClient("CAACEdEose0cBAFbxnCYeLtuUw6Wz7rrKayOmNYLWED69cB6TQYPCzIHeiW8aE0L9DWH2Q5EQUSZAih4D1ge7Vw3qAitUpZB4tM9QXYKWZC2Afmjy2mNkU1ZCTSlA7ztxZCrjmTfRUKbcahZAHdEq6UisOu1MefTyk7pT3O1W9OV8mlqWTgB0SVvjhSztXFmxp2GrYsVRUazglVM0mkaHAP",Version.LATEST);
		JsonObject temp = facebookClient.fetchObject("me", JsonObject.class, Parameter.with("fields", "posts.limit(300){likes.limit(300){name},story,message,created_time,caption},name"));
		post = jsonMapper.toJavaList(temp.getString("posts"), Post.class);
		name = jsonMapper.toJavaObject(temp.getString("name"), String.class);
		
		System.out.println(name);
		
		for (int i=0;i<post.size();i++) {
			if (post.get(i).getMessage() == null && post.get(i).getStory() == null) {
				continue;
			}
			else if (post.get(i).getMessage() != null) {
				Status st = new Status(post.get(i).getMessage(),post.get(i).getCreated_time(),post.get(i).getLikes());
				status.add(st);
			}
			else if (post.get(i).getStory() != null && post.get(i).getStory().equals(name + " " + "shared a link.")) {
				Status st = new Status(post.get(i).getCaption(),post.get(i).getCreated_time(),post.get(i).getLikes());
				status.add(st);	
			}
			else {
				Status st = new Status(post.get(i).getStory(),post.get(i).getCreated_time(),post.get(i).getLikes());
				status.add(st);	
			}
		}
		String time = status.get(0).getCreated_time();
		String[] t = time.split("T\\+");
		System.out.println(t[1]);
//		for (int i=0;i<status.size();i++) {
//			if (!status.get(i).getLikes().isEmpty())
//				System.out.println(i + " " +status.get(i).getStatus() + " " + status.get(i).getLikes().size());
//			else 
//				System.out.println(i + " " +status.get(i).getStatus() + " 0");
//		}
		
//		double error = 0;
//		int i;
//		int ctr1=0,ctr2=0;
//		fa = new FrequencyAnalysis(status);
//		for (i=0;i<=20;i++){
//			System.out.print(fa.analysis(status.get(i).getStatus()) + " ");
//			if (status.get(i).getLikes().isEmpty()) {
//				System.out.print("0");
//			}
//			else {
//				System.out.print(status.get(i).getLikes().size());
//			}
//			System.out.print(" " + status.get(i).getStatus() + "\n");
//			if (Math.abs(fa.analysis(status.get(i).getStatus()) - status.get(i).getLikes().size()) <5) {
//				ctr1++;
//			}
//			else if (Math.abs(fa.analysis(status.get(i).getStatus()) - status.get(i).getLikes().size()) <10) {
//				ctr2++;
//			}
//			error = error +  ((fa.analysis(status.get(i).getStatus()) - status.get(i).getLikes().size()) * (fa.analysis(status.get(i).getStatus()) - status.get(i).getLikes().size()));
//		}
//		int bad = 20 - (ctr1 + ctr2);
//		error = error/i;
//		System.out.println("ERROR: " + error);
//		System.out.println("AMAZING : " + ctr1);
//		System.out.println("GOOD: " + ctr2);
//		System.out.println("BAAAAD: " + bad);
			
		
		ModelAndView model = new ModelAndView("HomePage");
		return model;
	}
}
