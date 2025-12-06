package com.iglesia.adventistas.modules.news.service;

import com.iglesia.adventistas.modules.categories.entity.Category;
import com.iglesia.adventistas.modules.categories.repository.CategoryRepository;
import com.iglesia.adventistas.modules.departments.entity.Department;
import com.iglesia.adventistas.modules.departments.repository.DepartmentRepository;
import com.iglesia.adventistas.modules.news.dto.CreateNewsRequest;
import com.iglesia.adventistas.modules.news.dto.NewsDTO;
import com.iglesia.adventistas.modules.news.entity.News;
import com.iglesia.adventistas.modules.news.mapper.NewsMapper;
import com.iglesia.adventistas.modules.news.repository.NewsRepository;
import com.iglesia.adventistas.modules.users.entity.User;
import com.iglesia.adventistas.modules.users.repository.UserRepository;
import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;
import com.iglesia.adventistas.shared.util.SlugUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DepartmentRepository departmentRepository;
    private final NewsMapper newsMapper;

    @Override
    public NewsDTO createNews(CreateNewsRequest request, Long authorId) {
        log.info("Creating news with title: {}", request.getTitle());

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        News news = newsMapper.toEntity(request);

        // Generar slug único
        String baseSlug = SlugUtils.generateSlug(request.getTitle());
        String finalSlug = baseSlug;
        int counter = 1;

        while (newsRepository.existsBySlug(finalSlug)) {
            finalSlug = baseSlug + "-" + counter;
            counter++;
        }

        news.setSlug(finalSlug);
        news.setAuthor(author);
        news.setCategory(category);

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado"));
            news.setDepartment(department);
        }

        News savedNews = newsRepository.save(news);
        log.info("News created with ID: {}", savedNews.getId());

        return newsMapper.toDTO(savedNews);
    }

    @Override
    public NewsDTO updateNews(Long id, CreateNewsRequest request) {
        log.info("Updating news with ID: {}", id);

        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Noticia no encontrada"));

        news.setTitle(request.getTitle());
        news.setSummary(request.getSummary());
        news.setContent(request.getContent());
        news.setFeaturedImage(request.getFeaturedImage());
        news.setIsFeatured(request.getIsFeatured());
        news.setAllowComments(request.getAllowComments());

        if (!news.getCategory().getId().equals(request.getCategoryId())) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            news.setCategory(category);
        }

        News updatedNews = newsRepository.save(news);
        return newsMapper.toDTO(updatedNews);
    }

    @Override
    @Transactional(readOnly = true)
    public NewsDTO getNewsById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Noticia no encontrada"));
        return newsMapper.toDTO(news);
    }

    @Override
    @Transactional(readOnly = true)
    public NewsDTO getNewsBySlug(String slug) {
        News news = newsRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Noticia no encontrada"));

        // Incrementar contador de vistas
        news.setViewsCount(news.getViewsCount() + 1);
        newsRepository.save(news);

        return newsMapper.toDTO(news);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NewsDTO> getAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .map(newsMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NewsDTO> getPublishedNews(Pageable pageable) {
        return newsRepository.findAllPublished(pageable)
                .map(newsMapper::toDTO);
    }

    @Override
    public void deleteNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Noticia no encontrada"));

        news.setDeletedAt(LocalDateTime.now());
        newsRepository.save(news);
    }

    @Override
    public void publishNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Noticia no encontrada"));

        news.setStatus("published");
        news.setPublishedAt(LocalDateTime.now());
        newsRepository.save(news);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NewsDTO> getNewsByCategory(Long categoryId, Pageable pageable) {
        log.info("Getting published news by category ID: {}", categoryId);
        return newsRepository.findPublishedByCategory(categoryId, pageable)
                .map(newsMapper::toDTO);
    }
}