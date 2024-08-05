package com.gbyzzz.linkedinjobsbot;

import com.gbyzzz.linkedinjobsbot.initializer.Postgres;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;


@ActiveProfiles("test")
@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
//@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
//@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
//        classes = {CourseRepository.class, ScheduleRepository.class, UserRepository.class,
//        UserService.class, AdminController.class}))
//@SpringBootTest
//@AutoConfigureMockMvc
@ContextConfiguration(initializers = {
        Postgres.Initializer.class
})
public abstract class IntegrationTestBase {


    @BeforeAll
    static void init(){
        Postgres.container.start();
    }

}
