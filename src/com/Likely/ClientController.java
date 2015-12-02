//@author - Nalin Gupta 2014065
//			Sahar Siddiqui 2014091
package com.Likely;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView getRootPage () {
		
		ModelAndView model = new ModelAndView("Login");
		return model;
	}
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public void getHomePage (HttpServletResponse response) throws IOException {	
		List<Post> post = new ArrayList<>();
		List<Status> status = new ArrayList<>();
		JsonMapper jsonMapper = new DefaultJsonMapper();
		LikeAnalysis la;
		String name;
		
		FacebookClient facebookClient = new DefaultFacebookClient("CAACEdEose0cBAEZA8M1b8gObHoFtgewldG5i5kw2BKQu53dEfgoZAXYs4l7YmiHiwPFWmpHO0SbdmOY76tRfQfoUnJP4nZBx08x2kaRPb4hOx9QTK0x3MEBRCK2d74H7EeHeZAZAvk6g8BEDXSfO133oEzTl4VdMZBQszPeAZBonYomZAFxrZCtNbOIM18MyHsPTOBdn8ASwngIVu6qIpNchR",Version.LATEST);
		JsonObject temp = facebookClient.fetchObject("me", JsonObject.class, Parameter.with("fields", "posts.limit(300){likes.limit(300){name},story,message,created_time,caption},name"));
		post = jsonMapper.toJavaList(temp.getString("posts"), Post.class);
		name = jsonMapper.toJavaObject(temp.getString("name"), String.class);
		
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
		
		double[] params1 = new double[200];
		double[] params2 = new double[200];
		double[] knownlikes = new double[200];
		int ctr=0;
		FrequencyAnalysis fa = new FrequencyAnalysis(status,21);
		for (int i=21;i<status.size();i++) {
			params1[ctr] = fa.analysis(status.get(i));
			ctr++;
		}
		ctr=0;
		TimeAnalysis ta = new TimeAnalysis(status,21);
		for (int i=21;i<status.size();i++) {
			params2[ctr] = ta.analysis(status.get(i));
			ctr++;
		}
		ctr=0;
		for (int i=21;i<status.size();i++) {
			if (!status.get(i).getLikes().isEmpty())
				knownlikes[ctr] = status.get(i).getLikes().size();
			else
				knownlikes[ctr]=0;
			ctr++;
		}
		double[][] tee = new double[200][2];
		for (int i=0;i<params1.length;i++) {
			tee[i][0] = params1[i];
		}
		for (int i=0;i<params2.length;i++) {
			tee[i][1] = params2[i];
		}
		Matrix paramMatrix = new Matrix(tee);
		Matrix knownMatrix = new Matrix(knownlikes);
		MLR multi = new MLR(paramMatrix,knownMatrix);
		Matrix betaValues = multi.calculateBetaValues();
		for (int i=0;i<betaValues.getData().length;i++) {
			for (int j=0;j<betaValues.getData()[0].length;j++) {
				System.out.println(betaValues.getData()[i][j]);
			}
		}

//		int ctr1=0,ctr2=0;
//		int likes;
//		la = new LikeAnalysis(status,21);
//		
//		for (int i=0;i<=20;i++){
//			likes = la.analysis(status.get(i));
//			response.getWriter().print(likes + " ");
//			
//			if (status.get(i).getLikes().isEmpty()) {
//				response.getWriter().print("0");
//			}
//			else {
//				response.getWriter().print(status.get(i).getLikes().size());
//			}
//			response.getWriter().print(" " + status.get(i).getStatus() + "\n");
//			if (Math.abs(likes - status.get(i).getLikes().size()) <5) {
//				ctr1++;
//			}
//			else if (Math.abs(likes - status.get(i).getLikes().size()) <10) {
//				ctr2++;
//			}
//		}
//		int bad = 20 - (ctr1 + ctr2);
//		response.getWriter().println("\nTotal posts used for training : 180");
//		response.getWriter().println("Total posts used for testing : 20");
//		response.getWriter().println("With +- 5 Likes : " + ctr1);
//		response.getWriter().println("With +- 10 Likes: " + ctr2);
//		response.getWriter().println("Not in Range of Expected Likes: " + bad);
	}
	@RequestMapping(value="/status",method=RequestMethod.GET)
	public ModelAndView getNewStatus() {
		ModelAndView model = new ModelAndView("newStatus");
		return model;
	}
	@RequestMapping(value="/predict",method=RequestMethod.GET)
	public void getPredicted(@RequestParam("status") String s,HttpServletResponse response) throws IOException {
		List<Post> post = new ArrayList<>();
		List<Status> status = new ArrayList<>();
		JsonMapper jsonMapper = new DefaultJsonMapper();
		LikeAnalysis la;
		String name;
		
		FacebookClient facebookClient = new DefaultFacebookClient("CAACEdEose0cBAFG2ocPiJqo1pxPWj7gywXRv7dgXB7r4GcJtQZBscs2acFYjrMkW4hhJVqEmQRu2ko2B92YFzK41OLzZArKrHeIaZAMcIyZCgtGf0oS2ZCHtGZCQfijpGJRjlaXeBl2STGOE8n8iZBIHmLmlAOQ8zJtQQCOpIQ5c3g084IQpsteIslRQvHav97k3qKh0ZAr5A8ZCrzBI3X4nq",Version.LATEST);
		JsonObject temp = facebookClient.fetchObject("me", JsonObject.class, Parameter.with("fields", "posts.limit(300){likes.limit(300){name},story,message,created_time,caption},name"));
		post = jsonMapper.toJavaList(temp.getString("posts"), Post.class);
		name = jsonMapper.toJavaObject(temp.getString("name"), String.class);
		
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
		int likes;
		la = new LikeAnalysis(status,0);
		List<Like> l = new ArrayList<>();
		Status status_temp = new Status(s,"2015-11-07T16:53:24+0000",l);
		likes = la.analysis(status_temp);
		response.getWriter().print("Predcited Number of Likes :" + likes);
	}
}
