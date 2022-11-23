package com.springcourse.springcourse.DTO;

import com.springcourse.springcourse.domain.Request;
import com.springcourse.springcourse.domain.RequestStage;
import com.springcourse.springcourse.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestSaveDTO {

    @NotBlank(message = "Subject required")
    private String subject;

    private String description;

    @NotNull(message = "owner required")
    private User owner;

    private List<RequestStage> stages = new ArrayList<RequestStage>();

    public Request transformToRequest() {
        Request request = new Request(null, this.subject, this.description, null, null, this.owner, this.stages);
        return request;
    }
}
