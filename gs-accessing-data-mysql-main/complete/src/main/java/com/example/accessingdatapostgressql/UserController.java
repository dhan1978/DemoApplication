package com.example.accessingdatapostgressql;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;
@Controller
public class UserController {
	@RequestMapping(value = "/demo/user", method = RequestMethod.GET)
	   public ModelAndView student() {
	      return new ModelAndView("users", "command", new Users());
	   }
	   @RequestMapping(value = "/demo/addUser", method = RequestMethod.POST)
	      public String addStudent(@ModelAttribute("SpringWeb")Users student, 
	   
	   ModelMap model) {
	      model.addAttribute("name", student.getName());
	      model.addAttribute("email", student.getEmail());
	     
	      
	      return "result";
	   }
}
