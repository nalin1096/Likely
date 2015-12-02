//@author - Nalin Gupta 2014065
//			Sahar Siddiqui 2014091
package com.Likely;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;

@Controller
public class AppController
{
	@Autowired
	private HttpServletRequest req;
	
	private static final long serialVersionUID = 1L;
	private String code="";
	@RequestMapping(value="/app",method=RequestMethod.GET)
	public void getHomePage (HttpServletResponse response) throws IOException {
		code = req.getParameter("code");
		if (code == null || code.equals("")) {
			throw new RuntimeException(
					"ERROR: Didn't get code parameter in callback.");
		}
		FBConnection fbConnection = new FBConnection();
		String accessToken = fbConnection.getAccessToken(code);
		String[] words = accessToken.split("=");
		String[] w = words[1].split("&");
		String access = w[0];
	
		JsonMapper jsonMapper = new DefaultJsonMapper();
		String name;
		
		FacebookClient facebookClient = new DefaultFacebookClient(access,Version.LATEST);
		JsonObject temp = facebookClient.fetchObject("me", JsonObject.class, Parameter.with("fields", "name"));
		name = jsonMapper.toJavaObject(temp.getString("name"), String.class);
		response.getWriter().println("Welcome to Likely " + name);
	}
}
