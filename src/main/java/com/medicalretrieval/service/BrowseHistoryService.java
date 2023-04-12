package com.medicalretrieval.service;

import com.medicalretrieval.pojo.user.BrowseHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrowseHistoryService extends JpaRepository<BrowseHistory,Long> {


    List<BrowseHistory> findByUserId(Long userId, Sort sort);

}
