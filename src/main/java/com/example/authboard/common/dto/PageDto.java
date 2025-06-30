package com.example.authboard.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDto<T> {

    private List<T> content;
    private int number;
    private int size;
    private boolean first;
    private boolean last;
    private int totalPage;
    private long totalElements;

    public PageDto(Page<T> page) {
        this.content = page == null ? Collections.emptyList() : page.getContent();
        this.number = page == null ? 0 : page.getNumber() + 1;
        this.size = page == null ? 0 : page.getSize();
        this.first = page == null ? true : page.isFirst();
        this.last = page == null ? true : page.isLast();
        this.totalPage = page == null ? 0 : page.getTotalPages();
        this.totalElements = page == null ? 0 : page.getTotalElements();
    }
}
