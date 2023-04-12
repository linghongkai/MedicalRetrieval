package com.medicalretrieval.pojo.elasticsearch;

import lombok.Data;

@Data
public class SearchDoc {

    private String linkOfTitle;//And Or Not
    private String searchTypeOfTitle;//精确 or 模糊
    private String title;

    private String linkOfAuthor;//And Or Not
    private String searchTypeOfAuthor;//精确 or 模糊
    private String author;

    private String linkOfContent;//And Or Not
    private String searchTypeOfContent;//精确 or 模糊
    private String content;

    private int current;
    private int pageSize;

}
