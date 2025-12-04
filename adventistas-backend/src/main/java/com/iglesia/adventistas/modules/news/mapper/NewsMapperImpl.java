package com.iglesia.adventistas.modules.news.mapper;

import com.iglesia.adventistas.modules.news.dto.CreateNewsRequest;
import com.iglesia.adventistas.modules.news.dto.NewsDTO;
import com.iglesia.adventistas.modules.news.entity.News;
import org.springframework.stereotype.Component;

@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsDTO toDTO(News news) {
        if (news == null) {
            return null;
        }

        return NewsDTO.builder()
                .id(news.getId())
                .title(news.getTitle())
                .slug(news.getSlug())
                .summary(news.getSummary())
                .content(news.getContent())
                .featuredImage(news.getFeaturedImage())
                .status(news.getStatus())
                .isFeatured(news.getIsFeatured())
                .viewsCount(news.getViewsCount())
                .publishedAt(news.getPublishedAt())
                .createdAt(news.getCreatedAt())
                .authorName(news.getAuthor() != null ? news.getAuthor().getFirstName() : null)
                .categoryName(news.getCategory() != null ? news.getCategory().getName() : null)
                .departmentName(news.getDepartment() != null ? news.getDepartment().getName() : null)
                .build();
    }

    @Override
    public News toEntity(CreateNewsRequest request) {
        if (request == null) {
            return null;
        }

        return News.builder()
                .title(request.getTitle())
                .summary(request.getSummary())
                .content(request.getContent())
                .featuredImage(request.getFeaturedImage())
                .isFeatured(request.getIsFeatured())
                .allowComments(request.getAllowComments())
                .status("draft")
                .viewsCount(0)
                .build();
    }
}
