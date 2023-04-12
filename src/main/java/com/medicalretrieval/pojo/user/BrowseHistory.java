package com.medicalretrieval.pojo.user;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class BrowseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(128) not null default '' ")
    private String title;

    @Column(columnDefinition = "varchar(128) not null default '' ")
    private String authors;
    private Date openDate;
    private Long userId;



}
