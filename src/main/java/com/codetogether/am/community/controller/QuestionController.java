package com.codetogether.am.community.controller;

import com.codetogether.am.community.Model.Question;
import com.codetogether.am.community.Model.Reply;
import com.codetogether.am.community.Model.User;
import com.codetogether.am.community.dto.QuestionDTO;
import com.codetogether.am.community.dto.ReplyDTO;
import com.codetogether.am.community.mapper.QuestionMapper;
import com.codetogether.am.community.mapper.ReplyMapper;
import com.codetogether.am.community.mapper.UserMapper;
import com.codetogether.am.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ReplyMapper replyMapper;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
    Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        User Qcreator = userMapper.findById(questionDTO.getCreator());
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("creator",Qcreator);

        List<Reply> replies = replyMapper.findByQuestionId(questionDTO.getId());
        List<ReplyDTO> replyDTOs = new ArrayList<>();
        for(Reply r : replies) {
            ReplyDTO replyDTO =new ReplyDTO();
            List<User> users = userMapper.findByAccountId(r.getAccountId());
            replyDTO.setup(r,users.get(users.size()-1));
            replyDTOs.add(replyDTO);
        }
        model.addAttribute("replies",replyDTOs);
        Question question = questionMapper.getById(id);
        question.setViewCount(question.getViewCount()+1);
        question.setCommentCount(replyDTOs.size());
        questionMapper.update(question);
        return "question";
    }
}
