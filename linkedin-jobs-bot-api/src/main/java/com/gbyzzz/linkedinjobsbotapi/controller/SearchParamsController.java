package com.gbyzzz.linkedinjobsbotapi.controller;

import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SearchParamsService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search_parameter")
@CrossOrigin(origins = "*") 
@AllArgsConstructor
public class SearchParamsController {

    private final SearchParamsService searchParamsService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public List<SearchParams> getAllSearchParams(@PathVariable final Long id) {
        return searchParamsService.findAllByUserId(id);
    }

}
