package com.medicalretrieval.service;

import com.medicalretrieval.MedicalRetrievalApplication;
import com.medicalretrieval.pojo.user.BrowseHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MedicalRetrievalApplication.class)
public class BrowseHistoryServiceTest {

    @Autowired
    BrowseHistoryService browseHistoryService;

    @Test
    public void findByUserId() {
        for(int i=1;i<20;i++){
            BrowseHistory browseHistory = new BrowseHistory();
            browseHistory.setUserId((long) i);
            browseHistory.setTitle("title"+i);
            browseHistory.setAuthors("authors"+i);
            browseHistory.setOpenDate(new Date());
            browseHistoryService.save(browseHistory);
        }
    }

    @Test
    public void find(){
        Sort sort = Sort.by(Sort.Direction.DESC,"openDate");
        System.out.println(browseHistoryService.findAll(sort));
    }
}
