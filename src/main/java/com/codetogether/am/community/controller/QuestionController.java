package com.codetogether.am.community.controller;

import com.codetogether.am.community.Model.Question;
import com.codetogether.am.community.Model.User;
import com.codetogether.am.community.dto.QuestionDTO;
import com.codetogether.am.community.mapper.QuestionMapper;
import com.codetogether.am.community.mapper.UserMapper;
import com.codetogether.am.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
    Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        User creator = userMapper.findById(questionDTO.getCreator());
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("creator",creator);
        return "question";
    }
}
