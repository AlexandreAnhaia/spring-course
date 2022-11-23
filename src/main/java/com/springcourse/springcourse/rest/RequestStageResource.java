package com.springcourse.springcourse.rest;

import com.springcourse.springcourse.DTO.RequestStageSaveDTO;
import com.springcourse.springcourse.domain.RequestStage;
import com.springcourse.springcourse.service.RequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "request-stages")
public class RequestStageResource {

    @Autowired
    private RequestStageService requestStageService;

    @PostMapping
    public ResponseEntity<RequestStage> save(@RequestBody @Valid RequestStageSaveDTO requestStageSaveDTO){
        RequestStage requestStage = requestStageSaveDTO.transformToRequestStage();
        RequestStage createdRequestStage = requestStageService.save(requestStage);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequestStage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestStage> getById(@PathVariable (name = "id") Long id) {
        RequestStage stage = requestStageService.getById(id);
        return ResponseEntity.ok(stage);
    }

}
