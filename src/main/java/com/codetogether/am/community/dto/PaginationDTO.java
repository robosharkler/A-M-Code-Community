package com.codetogether.am.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOs;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer currentPage;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer limit, Integer page) {
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
        this.currentPage=page;
        this.totalPage = totalPage;

            pages.clear();
        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }
        if(page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }

        if(page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }

        if(pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }

        if(pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }


    }
}

