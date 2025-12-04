package com.iglesia.adventistas.modules.news.entity;

import com.iglesia.adventistas.modules.categories.entity.Category;
import com.iglesia.adventistas.modules.departments.entity.Department;
import com.iglesia.adventistas.modules.users.entity.User;
import com.iglesia.adventistas.shared.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, unique = true, length = 255)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "featured_image", columnDefinition = "TEXT")
    private String featuredImage;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "draft";

    @Builder.Default
    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Builder.Default
    @Column(name = "allow_comments", nullable = false)
    private Boolean allowComments = true;

    @Builder.Default
    @Column(name = "views_count", nullable = false)
    private Integer viewsCount = 0;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}