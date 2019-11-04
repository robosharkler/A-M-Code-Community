package com.codetogether.am.community.controller;

import com.codetogether.am.community.Model.Notice;
import com.codetogether.am.community.Model.Question;
import com.codetogether.am.community.Model.Reply;
import com.codetogether.am.community.Model.User;
import com.codetogether.am.community.mapper.NoticeMapper;
import com.codetogether.am.community.mapper.ReplyMapper;
import com.codetogether.am.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ReplyController {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private NoticeMapper noticeMapper;

    @PostMapping("/reply")
    public String doPublish(
            @RequestParam("reply") String description,
            @RequestParam(name = "qId") Integer questionId,
            HttpServletRequest httpServletRequest,
            Model model
    ){

        if(description==null|| description.equals("")){
            model.addAttribute("error","Empty description")  ;
            String res = "redirect:/question/"+questionId;
            return "redirect:/question/"+questionId;
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
            String res = "redirect:/question/"+questionId;
            return "redirect:/question/"+questionId;
        }
        Reply reply = new Reply();
        reply.setDescription(description);
        reply.setAccountId(user.getAccountId());
        reply.setGmtCreate(System.currentTimeMillis());
        reply.setQuestionId(questionId);
        replyMapper.insert(reply);
        Notice notice = new Notice();
        notice.setAccountId(user.getAccountId());
        notice.setQuestionId(questionId);
        notice.setVisited(false);
        noticeMapper.insert(notice);
        String res = "redirect:/question/"+questionId;
        return "redirect:/question/"+questionId;
    }
}
