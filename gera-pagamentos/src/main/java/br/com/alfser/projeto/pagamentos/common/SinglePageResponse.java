package br.com.alfser.projeto.pagamentos.common;

import org.springframework.data.domain.Page;

import java.util.List;

public record SinglePageResponse<T>(
        List<T> results,
        PaginationMetadata pagination
) {
    public SinglePageResponse(Page<T> page) {
        this(
                page.getContent(),
                new PaginationMetadata(page)
        );
    }

    public record PaginationMetadata(
            long totalElements,
            int pageSize,
            int currentPage,
            Integer previousPage,
            Integer nextPage
    ) {
        public PaginationMetadata(Page<?> page) {
            this(
                    page.getTotalElements(),
                    page.getSize(),
                    page.getNumber() + 1,
                    page.hasPrevious() ? page.getNumber() : null,
                    page.hasNext() ? page.getNumber() + 2 : null
            );
        }
    }
}