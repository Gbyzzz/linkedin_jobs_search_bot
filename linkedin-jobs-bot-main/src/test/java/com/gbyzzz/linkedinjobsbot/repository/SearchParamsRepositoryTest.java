package com.gbyzzz.linkedinjobsbot.repository;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.initializer.PostgreSQLContainerInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class SearchParamsRepositoryTest implements PostgreSQLContainerInitializer {

    @Autowired
    SearchParamsRepository searchParamsRepository;

    @Test
    void findSearchParamsByUserProfile_ChatId() {
        List<SearchParams> searchParams1 = searchParamsRepository.findSearchParamsByUserProfile_ChatId(1L);
        List<SearchParams> searchParams2 = searchParamsRepository.findSearchParamsByUserProfile_ChatId(2L);
        List<SearchParams> searchParams3 = searchParamsRepository.findSearchParamsByUserProfile_ChatId(3L);

        assertEquals(2, searchParams1.size());
        assertEquals(1, searchParams1.get(0).getId());
        assertEquals(2, searchParams1.get(1).getId());
        assertEquals(2, searchParams2.size());
        assertEquals(3, searchParams2.get(0).getId());
        assertEquals(4, searchParams2.get(1).getId());
        assertEquals(0, searchParams3.size());
    }

    @Test
    void countSearchParamsByUserProfile_ChatId() {
        int count1 = searchParamsRepository.countSearchParamsByUserProfile_ChatId(1L);
        int count2 = searchParamsRepository.countSearchParamsByUserProfile_ChatId(2L);
        int count3 = searchParamsRepository.countSearchParamsByUserProfile_ChatId(3L);

        assertEquals(2, count1);
        assertEquals(2, count2);
        assertEquals(0, count3);
    }

    @Test
    void findTopByUserProfileChatIdAndIdGreaterThanOrderByIdAsc() {
        Optional<SearchParams> first1 = searchParamsRepository.findTopByUserProfileChatIdAndIdGreaterThanOrderByIdAsc(1L, 0L);
        Optional<SearchParams> next1 = searchParamsRepository.findTopByUserProfileChatIdAndIdGreaterThanOrderByIdAsc(1L, first1.get().getId());
        Optional<SearchParams> first2 = searchParamsRepository.findTopByUserProfileChatIdAndIdGreaterThanOrderByIdAsc(2L, 0L);
        Optional<SearchParams> next2 = searchParamsRepository.findTopByUserProfileChatIdAndIdGreaterThanOrderByIdAsc(2L, first2.get().getId());
        Optional<SearchParams> first3 = searchParamsRepository.findTopByUserProfileChatIdAndIdGreaterThanOrderByIdAsc(3L, 0L);


        assertEquals(1, first1.get().getId());
        assertEquals(2, next1.get().getId());
        assertEquals(3, first2.get().getId());
        assertEquals(4, next2.get().getId());
        assertThrows(NoSuchElementException.class, () -> first3.get());

    }

    @Test
    void findTopByUserProfileChatIdAndIdLessThanOrderByIdDesc() {
        Optional<SearchParams> last1 = searchParamsRepository.findTopByUserProfileChatIdAndIdLessThanOrderByIdDesc(1L, 100L);
        Optional<SearchParams> previous1 = searchParamsRepository.findTopByUserProfileChatIdAndIdLessThanOrderByIdDesc(1L, last1.get().getId());
        Optional<SearchParams> last2 = searchParamsRepository.findTopByUserProfileChatIdAndIdLessThanOrderByIdDesc(2L, 100L);
        Optional<SearchParams> previous2 = searchParamsRepository.findTopByUserProfileChatIdAndIdLessThanOrderByIdDesc(2L, last2.get().getId());
        Optional<SearchParams> last3 = searchParamsRepository.findTopByUserProfileChatIdAndIdLessThanOrderByIdDesc(3L, 100L);


        assertEquals(2, last1.get().getId());
        assertEquals(1, previous1.get().getId());
        assertEquals(4, last2.get().getId());
        assertEquals(3, previous2.get().getId());

        assertThrows(NoSuchElementException.class, () -> last3.get());
    }

    @Test
    void findTopByUserProfileChatIdAndIdGreaterThanOrderByIdDesc() {
        Optional<SearchParams> last1 = searchParamsRepository.findTopByUserProfileChatIdAndIdGreaterThanOrderByIdDesc(1L, 0L);
        Optional<SearchParams> last2 = searchParamsRepository.findTopByUserProfileChatIdAndIdGreaterThanOrderByIdDesc(2L, 0L);
        Optional<SearchParams> last3 = searchParamsRepository.findTopByUserProfileChatIdAndIdGreaterThanOrderByIdDesc(3L, 0L);


        assertEquals(2, last1.get().getId());
        assertEquals(4, last2.get().getId());
        assertThrows(NoSuchElementException.class, () -> last3.get());
    }
}