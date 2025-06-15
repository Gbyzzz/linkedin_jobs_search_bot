package com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    private Integer pageSize;
    private Integer pageNumber;
    private SortDirection sortDirection;

    public enum SortDirection {
        ASC, DESC
    }
}