package com.americanidol.dependencymanager.model;

public class Dependency {

    private String groupId;
    private String artifactId;
    private String version;
    private String action;
    private String jobId;

    public Dependency(){

    }

    public Dependency(String groupId, String artifactId, String version, String action,String jobId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.action = action;
        this.jobId=jobId;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
