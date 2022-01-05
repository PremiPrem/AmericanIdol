package com.americanidol.dependencymanager.service;
import com.americanidol.dependencymanager.model.Project;
import com.opencsv.bean.CsvToBeanBuilder;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.jdom2.Document;
import org.jdom2.Element;
import com.americanidol.dependencymanager.model.Property;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Service;


import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;


@Service
public class PropertyService {

    String fileName = "src/main/resources/data/ProjectData.csv";

    //To get data from dataCsv File
    private List<Property> properties = new ArrayList<>(Arrays.asList(
            new Property( "app.runtime", "4.3", "DEPLOY", "uuid")
    ));

    public void addProperty(String projectName,String property, String value) throws FileNotFoundException {

        List<Project> projects = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Project.class)
                .build()
                .parse();

        //Map projectName and repoUrl
        HashMap<String, String> project_repo = new HashMap<String, String>();
        for(Project project:projects){
            project_repo.put(project.getProjectName(),project.getRepoUrl());
        }

        String repoUrl=project_repo.get(projectName);

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

            }

                Element maven=new Element("maven.plugin.version");
                Element app_runtime=new Element("app.runtime");

                maven.addContent(property);
                app_runtime.addContent(value);

                Element propertiesEle=new Element("properties");
                propertiesEle.addContent(property);
                propertiesEle.addContent(value);

                projectElements.add(propertiesEle);



            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
            }

            XMLOutputter xmlOutput = new XMLOutputter();

            // display xml
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, System.out);

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


        public void updateProperty(String projectName ,String property, String value) throws FileNotFoundException {
            List<Project> projects = new CsvToBeanBuilder(new FileReader(fileName))
                    .withType(Project.class)
                    .build()
                    .parse();


            HashMap<String, String> project_repo = new HashMap<String, String>();
            for(Project project:projects){
                project_repo.put(project.getProjectName(),project.getRepoUrl());
            }

            String repoUrl=project_repo.get(projectName);

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


                for (int i = 0; i < projectElements.size(); i++) {
                    Element projectElement = projectElements.get(i);

                        if (projectElement.getName().equalsIgnoreCase("properties")) {
                            List<Element> Propertiess = projectElement.getChildren();
                            for (int j = 0; j < Propertiess.size(); j++) {
                                Element propertyy = Propertiess.get(j);
                                if (propertyy.getName().equalsIgnoreCase(property)) {
                                    Propertiess.remove(j);
                                    Propertiess.add(new Element("app.runtime").setText(value));
                                }
                            }
                        }


                }

                while(eventReader.hasNext()) {
                    XMLEvent event = eventReader.nextEvent();
                }

                XMLOutputter xmlOutput = new XMLOutputter();

                // display xml
                xmlOutput.setFormat(Format.getPrettyFormat());
                xmlOutput.output(document, System.out);

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
