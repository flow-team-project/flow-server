package org.devridge.api.domain.community.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.devridge.api.domain.community.dto.request.ProjectRequest;
import org.devridge.api.domain.community.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Void> createProject(@RequestBody ProjectRequest request) {
        Long projectId = projectService.createProject(request);
        return ResponseEntity.created(URI.create("/api/project/" + projectId)).build();
    }

    @GetMapping
    public ResponseEntity<?> getAllProject() {

        return ResponseEntity.ok().body(projectService.getAllProject());
    }

}
