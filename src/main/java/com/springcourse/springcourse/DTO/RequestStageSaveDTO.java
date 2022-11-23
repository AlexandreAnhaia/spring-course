package com.springcourse.springcourse.DTO;

import com.springcourse.springcourse.domain.Enumeration.RequestState;
import com.springcourse.springcourse.domain.Request;
import com.springcourse.springcourse.domain.RequestStage;
import com.springcourse.springcourse.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestStageSaveDTO {

    private String description;

    @NotNull(message = "state required")
    private RequestState state;

    @NotNull(message = "request required")
    private Request request;

    @NotNull(message = "owner required")
    private User owner;

    public RequestStage transformToRequestStage() {
        RequestStage stage = new RequestStage(null, this.description, null, this.state, this.request, this.owner);
        return stage;
    }
}
