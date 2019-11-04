package com.codetogether.am.community.controller;

import com.codetogether.am.community.Model.Notice;
import com.codetogether.am.community.Model.Question;
import com.codetogether.am.community.Model.User;
import com.codetogether.am.community.dto.PaginationDTO;
import com.codetogether.am.community.mapper.NoticeMapper;
import com.codetogether.am.community.mapper.QuestionMapper;
import com.codetogether.am.community.mapper.UserMapper;
import com.codetogether.am.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private NoticeMapper noticeMapper;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          HttpServletResponse response,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size){
        User user=null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if(user !=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }else{
            return "redirect:/";
        }
        if("logout".equals(action)){
            //清除session
            Enumeration<String> em = request.getSession().getAttributeNames();
            while(em.hasMoreElements()){
                request.getSession().removeAttribute(em.nextElement().toString());
            }
            request.getSession().invalidate();

            Cookie cookie = new Cookie("token",null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:/";
        }


        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","Questions");
        }

        if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","Replies");
        }
        PaginationDTO paginationDTO = questionService.list(user.getName(), page, size);
        model.addAttribute("paginationDTO",paginationDTO);
        List<Notice> notices = noticeMapper.findByAccountId(user.getAccountId());
        model.addAttribute("notices",notices);
        List<Question> questions = new ArrayList<>();
        for(Notice n:notices){
            questions.add(questionMapper.getById(n.getQuestionId()));
        }
        model.addAttribute("noticeQuestions",questions);

        if("replies".equals(action)){
            for(Notice n:notices){
                n.setVisited(true);
                noticeMapper.update(n);
            }
        }
        return "profile";
    }
}
