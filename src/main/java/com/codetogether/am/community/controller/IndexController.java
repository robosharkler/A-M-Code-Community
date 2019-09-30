package com.codetogether.am.community.controller;

import com.codetogether.am.community.Model.Question;
import com.codetogether.am.community.Model.User;
import com.codetogether.am.community.dto.PaginationDTO;
import com.codetogether.am.community.dto.QuestionDTO;
import com.codetogether.am.community.mapper.QuestionMapper;
import com.codetogether.am.community.mapper.UserMapper;
import com.codetogether.am.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){

        Cookie[] cookies = request.getCookies();

        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if(user !=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        PaginationDTO paginationDTO = questionService.list(page,size);
        model.addAttribute("paginationDTO",paginationDTO);
        return "index";
    }
}
