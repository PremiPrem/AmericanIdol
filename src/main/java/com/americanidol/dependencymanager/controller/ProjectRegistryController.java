package com.americanidol.dependencymanager.controller;


import com.americanidol.dependencymanager.model.Project;
import com.americanidol.dependencymanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectRegistryController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping("/projects")
    public List<Project> project(){
        return projectService.getAllProject();
    }
}
