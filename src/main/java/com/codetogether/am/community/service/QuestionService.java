package com.codetogether.am.community.service;

import com.codetogether.am.community.Model.Question;
import com.codetogether.am.community.Model.User;
import com.codetogether.am.community.dto.PaginationDTO;
import com.codetogether.am.community.dto.QuestionDTO;
import com.codetogether.am.community.mapper.QuestionMapper;
import com.codetogether.am.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {
        Integer limit = size;
        Integer totalCount = questionMapper.count();
        if(page<1)
            page=1;
        Integer totalPage;
        if(totalCount%limit==0){
            totalPage=totalCount/limit;
        }else {
            totalPage=totalCount/limit+1;
        }
        if(page>totalPage)
            page=totalPage;
        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.list(offset,limit);
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for(Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        paginationDTO.setQuestionDTOs(questionDTOs);
        paginationDTO.setPagination(totalCount,limit,page);
        return paginationDTO;
    }
}
