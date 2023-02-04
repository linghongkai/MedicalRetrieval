package com.medicalretrieval.Repository;

import com.medicalretrieval.pojo.Document;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DocumentRepository extends ElasticsearchRepository<Document,Long> {
    List<Document> findByTitle(String title);

    List<Document> findByAuthor(Set<String> authors);

    @NotNull Page<Document> findAll(@NotNull Pageable pageable);






}
