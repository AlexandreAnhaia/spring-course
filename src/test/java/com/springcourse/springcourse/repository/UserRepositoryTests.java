package com.springcourse.springcourse.repository;

import com.springcourse.springcourse.domain.Enumeration.Role;
import com.springcourse.springcourse.domain.User;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Ignore
    public void AsaveTest() {
        User user = new User(null, "Alex", "alex@teste.com", "123", Role.ADMINISTRATOR, null, null);
        User createdUser = userRepository.save(user);

        assertThat(createdUser.getId()).isEqualTo(1L);
    }

    @Ignore
    public void updateTest() {
        User user = new User(1L, "Alex Teste", "alex@teste.com", "123", Role.ADMINISTRATOR, null, null);
        User updateUser = userRepository.save(user);

        assertThat(updateUser.getName()).isEqualTo("Alex Teste");
    }

    @Ignore
    public void getByIdTest() {
        Optional<User> result = userRepository.findById(1L);
        User user = result.get();

        assertThat(user.getPassword()).isEqualTo("123");
    }

    @Ignore
    public void listTest() {
        List<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(1);
    }

    @Ignore
    public void loginTest() {
        Optional<User> result = userRepository.login("alex@teste.com", "123");
        User loggedUser = result.get();

        assertThat(loggedUser.getId()).isEqualTo(1L);
    }

    @Test
    public void updateRoleTest() {
        int affectedRows = userRepository.updateRole(2L, Role.ADMINISTRATOR);
        assertThat(affectedRows).isEqualTo(1);
    }

}
