package com.springcourse.springcourse.repository;

import com.springcourse.springcourse.domain.Enumeration.RequestState;
import com.springcourse.springcourse.domain.Enumeration.Role;
import com.springcourse.springcourse.domain.Request;
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
public class RequestRepositoryTests {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void AsaveTest() {
        User user = new User(null, "Alex", "alex@teste.com", "123", Role.ADMINISTRATOR, null, null);
        userRepository.save(user);
        Request request = new Request(null, "Novo Laptop", "Pretendo obter um laptop", new Date(), RequestState.OPEN, user, null);
        Request createdRequest = requestRepository.save(request);

        assertThat(createdRequest.getId()).isEqualTo(1L);
    }

    @Test
    public void updateTeste() {
        User owner = new User();
        owner.setId(1L);

        Request request = new Request(1L, "Novo Laptop DELL", "Pretendo obter um laptop atualizado", null, RequestState.OPEN, owner, null);

        Request updateRequest = requestRepository.save(request);

        assertThat(updateRequest.getDescription()).isEqualTo("Pretendo obter um laptop atualizado");
    }

    @Test
    public void getByIdTest() {
        Optional<Request> result = requestRepository.findById(1L);
        Request requestGet = result.get();

        assertThat(requestGet.getSubject()).isEqualTo("Novo Laptop");
    }

    @Test
    public void listTest() {
        List<Request> requests = requestRepository.findAll();
        assertThat(requests.size()).isEqualTo(1);
    }

    @Test
    public void listByOwnerIdTest() {
        List<Request> requests = requestRepository.findAllByOwnerId(1L);
        assertThat(requests.size()).isEqualTo(1);
    }

    @Test
    public void updateStatusTest() {
        int affectedRows = requestRepository.updateStatus(1L, RequestState.IN_PROGRESS);
        assertThat(affectedRows).isEqualTo(1);
    }
}
