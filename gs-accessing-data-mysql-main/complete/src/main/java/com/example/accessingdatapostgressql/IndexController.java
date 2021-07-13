package com.example.accessingdatapostgressql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping(path="/demo")
@Controller
public class IndexController {
	@Autowired
	private UserRepository userRepository;
    @RequestMapping("/")
    public String home(Map<String, Object> model) {
        model.put("message", "HowToDoInJava Reader !!");
        List<Users> luser= new ArrayList<Users>();
        Iterable<Users> Ituser= userRepository.findAll();
        Ituser.forEach(i->luser.add(i));
        System.out.println(luser);
        for(int i=0;i<luser.size();i++) {
            model.put("name"+i, luser.get(i).getName());
            model.put("email"+i, luser.get(i).getEmail());
            }
        return "index";
    }
     
    @RequestMapping("/next")
    public String next(Map<String, Object> model) {
        model.put("message", "You are in new page !!");
        
        return "next";
    }
 
}