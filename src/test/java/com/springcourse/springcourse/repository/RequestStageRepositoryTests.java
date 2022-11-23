package com.springcourse.springcourse.repository;

import com.springcourse.springcourse.domain.Enumeration.RequestState;
import com.springcourse.springcourse.domain.Enumeration.Role;
import com.springcourse.springcourse.domain.Request;
import com.springcourse.springcourse.domain.RequestStage;
import com.springcourse.springcourse.domain.User;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class RequestStageRepositoryTests {

    @Autowired
    private RequestStageRepository requestStageRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void AsaveTest() {
        User owner = new User(null, "Alex", "alex@teste.com", "123", Role.ADMINISTRATOR, null, null);
        userRepository.save(owner);

        Request request = new Request(null, "Novo Laptop", "Pretendo obter um laptop", new Date(), RequestState.OPEN, owner, null);
        requestRepository.save(request);

        RequestStage stage = new RequestStage(null, "Test request Stage", new Date(), RequestState.CLOSED, request, owner);

        RequestStage created = requestStageRepository.save(stage);

        assertThat(created.getId()).isEqualTo(1L);
    }

    @Test
    public void getByIdTest() {
        Optional<RequestStage> result = requestStageRepository.findById(1L);
        RequestStage stage = result.get();

        assertThat(stage.getDescription()).isEqualTo("Test request Stage");
    }

    @Test
    public void listByRequestIdTest() {
        List<RequestStage> stages = requestStageRepository.findAllByRequestId(1L);

        assertThat(stages.size()).isEqualTo(1);
    }
}
