package com.springcourse.springcourse.service;

import com.springcourse.springcourse.domain.Enumeration.RequestState;
import com.springcourse.springcourse.domain.Request;
import com.springcourse.springcourse.domain.RequestStage;
import com.springcourse.springcourse.exception.NotFoundException;
import com.springcourse.springcourse.model.PageModel;
import com.springcourse.springcourse.model.PageRequestModel;
import com.springcourse.springcourse.repository.RequestRepository;
import com.springcourse.springcourse.repository.RequestStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestStageService {

    @Autowired
    private RequestStageRepository requestStageRepository;

    @Autowired
    private RequestRepository requestRepository;

    public RequestStage save(RequestStage requestStage) {
        requestStage.setRealizationDate(new Date());

        RequestStage createdStage = requestStageRepository.save(requestStage);
        Long requestId = requestStage.getRequest().getId();
        RequestState state = requestStage.getState();
        requestRepository.updateStatus(requestId, state);
        return createdStage;
    }

    public RequestStage getById(Long id) {
        Optional<RequestStage> result = requestStageRepository.findById(id);
        return result.orElseThrow(() -> new NotFoundException("There are not request stage with id = " + id));
    }

    public List<RequestStage> listAllByRequestId(Long requestId) {
        List<RequestStage> stages = requestStageRepository.findAllByRequestId(requestId);
        return stages;
    }

    public PageModel<RequestStage> listAllByRequestIdOnLazyModel(Long requestId, PageRequestModel pr) {
        Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
        Page<RequestStage> page = requestStageRepository.findAllByRequestId(requestId, pageable);

        PageModel<RequestStage> pm = new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
        return pm;
    }

}
