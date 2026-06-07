package com.suman.notedown.util;

import com.suman.notedown.dto.pageDtos.PageResponseDTO;
import org.springframework.data.domain.Page;

public class PaginationUtility {
    public static <T> PageResponseDTO<T> toPageResponseDTO(Page<T> page) {
        return new PageResponseDTO<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
