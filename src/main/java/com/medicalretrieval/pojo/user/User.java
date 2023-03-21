package com.medicalretrieval.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "varchar(128) not null default '' ")
    private String account="";
    @Column(columnDefinition = "varchar(128) not null default '' ")
    private String password="";
    @Column(columnDefinition = "varchar(128) not null default '' ")
    private String email="";

    @Column(columnDefinition = "int not null default '0'")
    private int sex = 0;

    @Column(columnDefinition = "varchar(128) not null default '' ")
    private String telephone="";

    @Column(columnDefinition = "int not null default '0' ")
    private int permissionGroupId=0;

    @Column(columnDefinition = "varchar(128) not null default '' ")
    private String avatar="http://192.168.43.144:8080/avatar/default_avatar_man.png";

    @Column(columnDefinition = "int not null default '0' ")
    private int disabled=0;


}
