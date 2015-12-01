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
//@SessionAttributes("customerObj")
public class ClientController {
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public ModelAndView getHomePage () {	
		List<Post> post = new ArrayList<>();
		List<Status> status = new ArrayList<>();
		JsonMapper jsonMapper = new DefaultJsonMapper();
		FacebookClient facebookClient = new DefaultFacebookClient("CAACEdEose0cBAGfXphYv9vZBEhBZB78cV2XVX4qukJSP25PeBw8qWPGnTxKUZB01RRFYWaRvn5fxsiZBiGBPAzYggChnATyPEGHPWkQfgPkVzWJ3JfgeX9CgRqbjZCKAwQYZABCX3greDttYBZCL2OFf9yds8XN4s7uJ249Mah2EqSrA1YoSQieIKcKlxq4CI6DAhRTigjZCVaXBEvVclR4m",Version.LATEST);
		JsonObject temp = facebookClient.fetchObject("me", JsonObject.class, Parameter.with("fields", "posts.limit(300){likes.limit(300){name},story,message,created_time}"));
		post = jsonMapper.toJavaList(temp.getString("posts"), Post.class);
		
		for (int i=0;i<post.size();i++) {
			if (post.get(i).getMessage() == null && post.get(i).getStory() == null) {
				continue;
			}
			else if (post.get(i).getMessage() != null) {
				Status st = new Status(post.get(i).getMessage(),post.get(i).getCreated_time(),post.get(i).getLikes());
				status.add(st);
			}
			else if (post.get(i).getStory() != null) {
				Status st = new Status(post.get(i).getStory(),post.get(i).getCreated_time(),post.get(i).getLikes());
				status.add(st);
			}
		}
		for (int i=0;i<status.size();i++) {
			System.out.println(i + " " +status.get(i).getStatus());
		}
//		TFIDF obj = new TFIDF();
//		String words[] = post.get(188).getStatus().split(" ");
//		for (String word: words) {
//			System.out.println(word + ": "+ obj.tfidf(post, post.get(188).getStatus(), word));
//		}
		
		ModelAndView model = new ModelAndView("HomePage");
		return model;
	}
}
