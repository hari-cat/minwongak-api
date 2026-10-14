package org.example.aptboardapi.common.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T> (
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
    public static <T> PageResponse<T> from(Page<T> p) {
        return new PageResponse<>(
                p.getContent(),
                p.getNumber() + 1,
                p.getSize(),
                p.getTotalElements(),
                p.getTotalPages(),
                p.hasNext()
        );
    }

}
