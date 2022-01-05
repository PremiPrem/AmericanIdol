package com.americanidol.dependencymanager.service;

import com.americanidol.dependencymanager.model.Dependency;
import com.americanidol.dependencymanager.model.Project;
import com.opencsv.bean.CsvToBeanBuilder;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Stream;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Service
public class DependencyService {
    String fileName = "src/main/resources/data/ProjectData.csv";

    public static String gID;
    public static String aID;

    private List<Dependency> dependencies=new ArrayList<>(Arrays.asList(
            new Dependency("com.mysql", "jdbc-driver", "45.4.1", "DEPLOY","UUID")
            ));

    // add new Dependency
    public void addDependency(String projectName,String groupId,String artifactId,String version) throws FileNotFoundException {

        //To get data from dataCsv File
        List<Project> projects = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Project.class)
                .build()
                .parse();

        //Map projectName and repoUrl
        HashMap<String, String> project_repo = new HashMap<String, String>();
        for(Project project:projects){
            project_repo.put(project.getProjectName(),project.getRepoUrl());
        }
        //To get appropriate repoUrl using projectName
        String repoUrl=project_repo.get(projectName);

        //Give your project root path before /temp....
        String cloneDirectoryPath = "D://DependencyManager/temp/init_space/File/";

        try {
            System.out.println("Cloning "+repoUrl+" into "+repoUrl);
            Map<String,String> map = new HashMap<>();
            map.put(repoUrl,cloneDirectoryPath);


            for (Map.Entry<String, String> entry : map.entrySet()) {
                Git.cloneRepository()
                        .setURI(String.valueOf(entry.getKey()))
                        .setDirectory(Paths.get(entry.getValue()).toFile())
                        .call();
            }


            System.out.println("Completed Cloning");
        } catch (GitAPIException e) {
            System.out.println("Exception occurred while cloning repo");
            e.printStackTrace();
        }

        //Checking for the directory exist or not
        File f = new File("D://DependencyManager/temp/work_space");
        if (f.exists() && f.isDirectory()) {
            System.out.println("Exists");
            DependencyService a=new DependencyService();
            a.copyDir();

        }
        else{
            System.out.println("NOT Exists");
            f.mkdir();
            DependencyService a=new DependencyService();
            a.copyDir();
        }
        //xml reader
        try (InputStream in = new FileInputStream("src/main/java/com/americanidol/dependencymanager/pomNew.xml");
             OutputStream out = System.out;) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(
                    new FileReader("src/main/java/com/americanidol/dependencymanager/pomNew.xml"));
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(new File("src/main/java/com/americanidol/dependencymanager/pomNew.xml"));

            Element rootElement = document.getRootElement();
            List<Element> projectElements = rootElement.getChildren(); //getting all the children of the project

            List<Element> el = null;

            //To work with dependencies parent tag

            for(int i = 0;i < projectElements.size();i++) {
                Element projectElement = projectElements.get(i);

                if(projectElement.getName().equalsIgnoreCase("dependencies")) {
                    List<Element> dependencies = projectElement.getChildren();
                    el=dependencies;
                    Element groupIdd=new Element("groupId");
                    Element artifactIdd=new Element("artifactId");
                    Element versionn=new Element("version");

                    groupIdd.addContent(groupId);
                    artifactIdd.addContent(artifactId);
                    versionn.addContent(version);

                   // To add new element value
                    Element dependencyEle=new Element("dependency");
                    dependencyEle.addContent(groupId);
                    dependencyEle.addContent(artifactId);
                    dependencyEle.addContent(version);

                    el.add(dependencyEle);

                }

            }
            //To write file
            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
            }

            XMLOutputter xmlOutput = new XMLOutputter();

            // display xml
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, System.out);

            // you can give your project pom file path (same pom file)

            try (FileOutputStream output =
                         new FileOutputStream("src/main/java/com/americanidol/dependencymanager/pomNew2.xml")) {
                xmlOutput.output(document, output);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }


    //Method to get backup from init_space to workspace
    public void copyDir(){
        try {
            // source & destination directories
            Path src = Paths.get("D://DependencyManager/temp/init_space");
            Path dest = Paths.get("D://DependencyManager/temp/work_space");

            // create stream for `src`
            Stream<Path> files = Files.walk(src);

            // copy all files and folders from `src` to `dest`
            files.forEach(file -> {
                try {
                    Files.copy(file, dest.resolve(src.relativize(file)),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // close the stream
            files.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //Method to update Dependency
    public void updateDependency(String projectName,String groupId,String artifactId,String version) throws FileNotFoundException {


        //To get data from dataCsv File
        List<Project> projects = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Project.class)
                .build()
                .parse();

        //Map projectName and repoUrl
        HashMap<String, String> project_repo = new HashMap<String, String>();
        for(Project project:projects){
            project_repo.put(project.getProjectName(),project.getRepoUrl());
        }
        //To get appropriate repoUrl using projectName
        String repoUrl=project_repo.get(projectName);

        //Give your project root path before /temp....
        String cloneDirectoryPath = "D://DependencyManager/temp/init_space/File/";

        try {
            System.out.println("Cloning "+repoUrl+" into "+repoUrl);
            Map<String,String> map = new HashMap<>();
            map.put(repoUrl,cloneDirectoryPath);


            for (Map.Entry<String, String> entry : map.entrySet()) {
                Git.cloneRepository()
                        .setURI(String.valueOf(entry.getKey()))
                        .setDirectory(Paths.get(entry.getValue()).toFile())
                        .call();
            }


            System.out.println("Completed Cloning");
        } catch (GitAPIException e) {
            System.out.println("Exception occurred while cloning repo");
            e.printStackTrace();
        }

        //Checking for the directory exist or not
        File f = new File("D://DependencyManager/temp/work_space");
        if (f.exists() && f.isDirectory()) {
            System.out.println("Exists");
            DependencyService a=new DependencyService();
            a.copyDir();

        }
        else{
            System.out.println("NOT Exists");
            f.mkdir();
            DependencyService a=new DependencyService();
            a.copyDir();
        }

        try (InputStream in = new FileInputStream("src/main/java/com/americanidol/dependencymanager/pomNew.xml");
             OutputStream out = System.out;) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(
                    new FileReader("src/main/java/com/americanidol/dependencymanager/pomNew.xml"));
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(new File("src/main/java/com/americanidol/dependencymanager/pomNew.xml"));

            Element rootElement = document.getRootElement();
            List<Element> projectElements = rootElement.getChildren(); //getting all the children of the project

            for(int i = 0;i < projectElements.size();i++) {
                Element projectElement = projectElements.get(i);

                    if(projectElement.getName().equalsIgnoreCase("dependencies")) {
                        List<Element> dependencies=projectElement.getChildren(); //Dependencies list

                        for(int j = 0;j <dependencies.size();j++) {
                            Element dependency = dependencies.get(j); //Specific dependency

                            List<Element> dependencyChildren=dependency.getChildren();

                            for(int k = 0;k <dependencyChildren.size();k++) {
                                Element dependencyelement = dependencyChildren.get(k);//groupid,arId,Vrtion

// gid, aid is the local variable to store the user given values to groupId and Artifact Id then only we can check the if condition
                                if(dependencyelement.getName().equalsIgnoreCase("groupId")) {
                                    gID=dependencyelement.getValue();
                                }
                                if(dependencyelement.getName().equalsIgnoreCase("artifactId")) {
                                    aID=dependencyelement.getValue();

                                }
                                if (dependencyelement.getName().equalsIgnoreCase("version")){
                                    if (gID.equalsIgnoreCase(groupId) && aID.equalsIgnoreCase(artifactId)){
                                        dependencyChildren.remove(k);
                                        dependencyChildren.add(new Element("version").setText(version));
                                        gID=null;
                                    }
                                }
                            }

                        }
                    }
            }

            //write to the updates
            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
            }

            XMLOutputter xmlOutput = new XMLOutputter();

            // display xml
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, System.out);

            // pom file of your project in workspace
            try (FileOutputStream output =
                         new FileOutputStream("src/main/java/com/americanidol/dependencymanager/pomNew2.xml")) {
                xmlOutput.output(document, output);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }

}
