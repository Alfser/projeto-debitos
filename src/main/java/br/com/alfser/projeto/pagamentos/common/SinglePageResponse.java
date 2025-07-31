package br.com.alfser.projeto.pagamentos.common;

import org.springframework.data.domain.Page;

import java.util.List;

public record SinglePageResponse<T>(
        List<T> results,
        PaginationMetadata pagination
) {
    public record PaginationMetadata(
            long totalElements,
            int pageSize,
            int currentPage,
            int previousPage,
            Integer nextPage
    ) {
        public PaginationMetadata(Page<?> page) {
            this(
                    page.getTotalElements(),
                    page.getSize(),
                    page.getNumber(),
                    Math.max(page.getNumber() - 1, 0),
                    page.hasNext() ? page.getNumber() + 1 : null
            );
        }
    }
}