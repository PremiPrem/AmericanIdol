package com.americanidol.dependencymanager.model;

import com.opencsv.bean.CsvBindByPosition;

public class Project {
    @CsvBindByPosition(position = 0)
    private String projectName;
    @CsvBindByPosition(position = 1)
    private String repoUrl;
    @CsvBindByPosition(position = 2)
    private String activeBranch;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getActiveBranch() {
        return activeBranch;
    }

    public void setActiveBranch(String activeBranch) {
        this.activeBranch = activeBranch;
    }
}