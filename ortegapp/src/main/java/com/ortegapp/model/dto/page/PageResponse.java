package com.ortegapp.model.dto.page;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {

    private List<T> content;

    private boolean first;

    private boolean last;

    private int totalPages;

    private long totalElements;

    public  PageResponse(Page<T> page){
        this.content = page.getContent();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }

}
