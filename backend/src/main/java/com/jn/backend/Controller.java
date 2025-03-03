package com.jn.backend;

import com.jn.backend.dto.RequestDto;
import com.jn.backend.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/traffic-lights")
public class Controller {

    private final Service service;

    @Autowired
    public Controller(Service service){
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto> startSimulation(@RequestBody RequestDto request){
        return ResponseEntity.ok(service.processSimulation(request));
    }

}
