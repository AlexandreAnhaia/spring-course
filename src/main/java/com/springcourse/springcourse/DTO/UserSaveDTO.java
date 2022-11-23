package com.springcourse.springcourse.DTO;

import com.springcourse.springcourse.domain.Enumeration.Role;
import com.springcourse.springcourse.domain.Request;
import com.springcourse.springcourse.domain.RequestStage;
import com.springcourse.springcourse.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserSaveDTO {

    @NotBlank(message = "name required")
    private String name;

    @Email(message = "email required correctly")
    private String email;

    @Size(min = 7, max = 99, message = "Password must be between 7 and 99")
    private String password;

    @NotNull
    private Role role;

    private List<Request> requests = new ArrayList<Request>();
    private List<RequestStage> stages = new ArrayList<RequestStage>();

    public User transformToUser() {
        User user = new User(null, this.name, this.email, this.password, this.role, this.requests, this.stages);
        return user;
    }
}
