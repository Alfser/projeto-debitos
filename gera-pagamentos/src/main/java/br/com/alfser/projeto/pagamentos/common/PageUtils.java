package br.com.alfser.projeto.pagamentos.common;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PageUtils {

    public static Sort parseSort(String sort) {
        if (sort == null ) {
            return Sort.by(Sort.Direction.DESC, "idPagamento"); // Default sort
        }
        List<Sort.Order> orders = new ArrayList<>();
        if (!sort.contains(",")) {
            orders.add(new Sort.Order(Sort.Direction.DESC, sort.trim()));
            return Sort.by(orders);
        }
        String[] parts = sort.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException(
                    "Invalid sort parameter format. Expected 'field,direction' but got: " + sort
            );
        }

        try {
            Sort.Direction direction = Sort.Direction.fromString(parts[1]);
            orders.add(new Sort.Order(direction, parts[0]));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid sort direction. Must be 'asc' or 'desc' in parameter: " + sort
            );
        }
        return Sort.by(orders);
    }
}
