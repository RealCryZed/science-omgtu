package ru.omgtu.scienceomgtu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.omgtu.scienceomgtu.repository.RoleRepository;
import ru.omgtu.scienceomgtu.repository.UserRepository;
import ru.omgtu.scienceomgtu.service.UserService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        System.out.println(auth.getAuthorities());
        modelAndView.setViewName("login");

        System.out.println(userService.findUserByUsername("admin"));

        return modelAndView;
    }
}
