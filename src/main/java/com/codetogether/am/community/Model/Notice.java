package com.codetogether.am.community.Model;

import lombok.Data;

@Data
public class Notice {
    private Integer id;
    private String accountId;
    private Integer questionId;
    private Boolean visited;
}
