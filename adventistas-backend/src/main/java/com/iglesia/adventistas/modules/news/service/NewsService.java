package com.iglesia.adventistas.modules.news.service;

import com.iglesia.adventistas.modules.news.dto.CreateNewsRequest;
import com.iglesia.adventistas.modules.news.dto.NewsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {

    NewsDTO createNews(CreateNewsRequest request, Long authorId);

    NewsDTO updateNews(Long id, CreateNewsRequest request);

    NewsDTO getNewsById(Long id);

    NewsDTO getNewsBySlug(String slug);

    Page<NewsDTO> getAllNews(Pageable pageable);

    Page<NewsDTO> getPublishedNews(Pageable pageable);

    void deleteNews(Long id);

    void publishNews(Long id);

    Page<NewsDTO> getNewsByCategory(Long categoryId, Pageable pageable);
}