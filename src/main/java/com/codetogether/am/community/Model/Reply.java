package com.codetogether.am.community.Model;

import lombok.Data;

@Data
public class Reply {
    private Integer id;
    private String description;
    private String accountId;
    private Long gmtCreate;
    private Integer questionId;
}
