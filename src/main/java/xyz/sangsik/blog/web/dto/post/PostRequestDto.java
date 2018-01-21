package xyz.sangsik.blog.web.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.sangsik.blog.domain.Post;
import xyz.sangsik.blog.service.CategoryService;

import java.util.Map;

@Getter
@Setter
@Component
@NoArgsConstructor
public class PostRequestDto {

    @Autowired
    CategoryService categoryService;

    private Long category;
    private String title;
    private String content;

    public void bindingRequest(PostRequestDto requestDto) {
        this.category = requestDto.getCategory();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public Post toEntity() {
        return new Post(this.categoryService.get(category), this.title, this.content);
    }
}