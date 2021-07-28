package com.example.SpringMVCExample;



import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;

import com.example.SpringMVCExample.model.Users;
import com.example.SpringMVCExample.service.UserService;
import com.google.gson.Gson;


/**
 * Handles requests for the application home page.
 */
@SessionAttributes("user")
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private UserService userService;
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	@Bean
	public Gson  gson() {
	    return new Gson();
	}
	@Autowired(required=true)
	@Qualifier(value="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		 List<Users> luser= new ArrayList<Users>();
	        List<Users> Ituser= userService.listUsers();
	       
	        System.out.println(luser);
	        for(int i=0;i<Ituser.size();i++) {
	            model.addAttribute("name"+i, Ituser.get(i).getName());
	            model.addAttribute("email"+i, Ituser.get(i).getEmail());
	            }
	        model.addAttribute("Ituser", Ituser);
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Locale locale, Model model) {
		return "login";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String submitForm(@ModelAttribute("user") Users user, BindingResult result, SessionStatus status) {
		//model.addAttribute("name", user.getName());
		//model.addAttribute("email", user.getEmail());
		userService.addUser(user);
		status.setComplete();
		return "success";
	}
	 @RequestMapping(value = "/userform",method = RequestMethod.GET)
	    public String setupForm(Model model) 
	    {
		 Users user = new Users();
	         model.addAttribute("user", user);
	         return "userform";
	    }
	 @RequestMapping(value = "/success", method = RequestMethod.GET)
	    public String success(Model model) 
	    {
	         return "addSuccess";
	    }
}