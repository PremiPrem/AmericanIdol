package com.americanidol.dependencymanager.controller;

import com.americanidol.dependencymanager.model.Dependency;
import com.americanidol.dependencymanager.model.Property;
import com.americanidol.dependencymanager.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
public class PropertyController {

    @Autowired
    private PropertyService propertyService;



    @RequestMapping(method = RequestMethod.POST,value="/projects/{projectName}/property")
    public  void addProperty( @PathVariable String projectName, @RequestParam String property, @RequestParam String value) throws FileNotFoundException {
        propertyService.addProperty(projectName,property,value);
    }
    @RequestMapping(method = RequestMethod.PUT,value="/projects/{projectName}/property")
    public  void updateProperty( @PathVariable String projectName, @RequestParam String property, @RequestParam String value) throws FileNotFoundException {
        propertyService.updateProperty(projectName,property,value);
//          System.out.println(projectName);
    }
}
