package com.example.SpringBootDemo.controller;

import com.example.SpringBootDemo.Repository.UserRepository;
import com.example.SpringBootDemo.model.User;
import com.example.SpringBootDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DeffController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model){
        model.addAttribute("name", name);
        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }
}
