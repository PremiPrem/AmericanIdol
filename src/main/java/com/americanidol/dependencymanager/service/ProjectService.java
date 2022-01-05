package com.americanidol.dependencymanager.service;

import com.americanidol.dependencymanager.model.Project;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

@Service
public class ProjectService {

    String fileName = "src/main/resources/data/ProjectData.csv";

    //To get data from dataCsv File
    List<Project> projects = new CsvToBeanBuilder(new FileReader(fileName))
            .withType(Project.class)
            .build()
            .parse();

    public ProjectService() throws FileNotFoundException {
    }


    public List<Project> getAllProject () {

        //Map projectName and repoUrl
        HashMap<String, String> project_repo = new HashMap<String, String>();

       for(Project project:projects){
           project_repo.put(project.getProjectName(),project.getRepoUrl());
        }

       //System.out.println(project_repo.get("first-service-name"));
        return projects;
    }


}
