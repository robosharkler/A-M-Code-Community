package com.codetogether.am.community.dto;

import com.codetogether.am.community.Model.Reply;
import com.codetogether.am.community.Model.User;
import com.codetogether.am.community.mapper.UserMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Data
public class ReplyDTO {

    private Integer id;
    private String description;
    private String accountId;
    private Long gmtCreate;
    private Integer questionId;
    private User creator;
    public void setup(Reply reply, User user) {
        id = reply.getId();
        description = reply.getDescription();
        accountId = reply.getAccountId();
        gmtCreate = reply.getGmtCreate();
        questionId = reply.getQuestionId();
        creator = user;
    }
}
