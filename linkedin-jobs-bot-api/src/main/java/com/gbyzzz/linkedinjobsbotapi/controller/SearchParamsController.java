package com.gbyzzz.linkedinjobsbotapi.controller;

import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SearchParamsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search_parameter")
@AllArgsConstructor
public class SearchParamsController {

    private final SearchParamsService searchParamsService;

    @GetMapping("/{id}")
    public List<SearchParams> getAllSearchParams(@PathVariable final Long id) {
        return searchParamsService.findAllByUserId(id);
    }

}
