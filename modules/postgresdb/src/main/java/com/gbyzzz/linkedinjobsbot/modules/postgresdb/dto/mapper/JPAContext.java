package com.gbyzzz.linkedinjobsbot.modules.postgresdb.dto.mapper;


import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.FilterParams;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;
import jakarta.persistence.EntityManager;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

public class JPAContext {
    private final EntityManager em;

    private SearchParams searchParams;

    public JPAContext(EntityManager em) {
        this.em = em;
    }

    @BeforeMapping
    public void setEntity(@MappingTarget SearchParams searchParams) {
        this.searchParams = searchParams;
        // you could do stuff with the EntityManager here
    }

    @AfterMapping
    public void establishRelation(@MappingTarget FilterParams filterParams) {
        filterParams.setSearchParams(searchParams);
        // you could do stuff with the EntityManager here
    }
}
