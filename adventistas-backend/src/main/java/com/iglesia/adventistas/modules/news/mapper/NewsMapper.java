package com.iglesia.adventistas.modules.news.mapper;

import com.iglesia.adventistas.modules.news.dto.CreateNewsRequest;
import com.iglesia.adventistas.modules.news.dto.NewsDTO;
import com.iglesia.adventistas.modules.news.entity.News;

public interface NewsMapper {
    NewsDTO toDTO(News news);

    News toEntity(CreateNewsRequest request);
}