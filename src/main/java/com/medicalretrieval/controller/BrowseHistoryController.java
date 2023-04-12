package com.medicalretrieval.controller;

import com.medicalretrieval.pojo.user.BrowseHistory;
import com.medicalretrieval.service.BrowseHistoryService;
import com.medicalretrieval.utils.Page4Navigator;
import com.medicalretrieval.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bro")
public class BrowseHistoryController {

    @Autowired
    BrowseHistoryService browseHistoryService;

    @PostMapping("/")
    void addBrowse(@RequestBody BrowseHistory browseHistory){
        browseHistoryService.save(browseHistory);
    }

    @GetMapping("/{userId}")
    Page4Navigator<BrowseHistory> queryBrowse(@PathVariable String userId, int curr, int size){
        Sort sort = Sort.by(Sort.Direction.DESC,"openDate");
        Pageable pageable = PageRequest.of(curr-1,size);
        List<BrowseHistory> byUserId = browseHistoryService.findByUserId(Long.valueOf(userId), sort);
        Page<BrowseHistory> page = new PageImpl<>(byUserId,pageable,byUserId.size());

        return new Page4Navigator<BrowseHistory>(page,5);
    }

    @DeleteMapping("/{userId}")
    Object deleteBro(@PathVariable String userId,Long id){
        browseHistoryService.deleteById(id);
        return Result.success();
    }

}
