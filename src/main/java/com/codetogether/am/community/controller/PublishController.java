package com.codetogether.am.community.controller;

import com.codetogether.am.community.Model.Question;
import com.codetogether.am.community.Model.User;
import com.codetogether.am.community.mapper.QuestionMapper;
import com.codetogether.am.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest httpServletRequest,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null|| title.equals("")){
            model.addAttribute("error","Empty title")  ;
            return "publish";
        }
        if(description==null|| description.equals("")){
            model.addAttribute("error","Empty description")  ;
            return "publish";
        }
        if(tag==null|| tag.equals("")){
            model.addAttribute("error","Empty tag")  ;
            return "publish";
        }
        User user = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if(user!=null){
                        httpServletRequest.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if(user==null){
            model.addAttribute("error","No Login")  ;
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionMapper.create(question);
        return "redirect:/";
    }
}
