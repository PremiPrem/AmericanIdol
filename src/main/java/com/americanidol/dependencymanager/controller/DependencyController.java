package com.americanidol.dependencymanager.controller;

import com.americanidol.dependencymanager.model.Dependency;
import com.americanidol.dependencymanager.model.Project;
import com.americanidol.dependencymanager.service.DependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class DependencyController {

    @Autowired
    private DependencyService dependencyService;


    // Method call to add new dependency
    @RequestMapping(method = RequestMethod.POST,value="/projects/{projectName}/dependency")
    public  void addDependency( @PathVariable String projectName, @RequestParam(required = false) String groupId,@RequestParam(required = false) String artifactId,@RequestParam(required = false) String version) throws FileNotFoundException {
        dependencyService.addDependency(projectName,groupId,artifactId,version);
    }

    // Method call to update dependency
    @RequestMapping(method = RequestMethod.PUT,value="/projects/{projectName}/dependency")
    @ResponseBody
    public void updateDependency(@PathVariable String projectName, @RequestParam(required = false) String groupId,@RequestParam(required = false) String artifactId,@RequestParam(required = false) String version) throws FileNotFoundException {

        dependencyService.updateDependency(projectName,groupId,artifactId,version);

    }

}
