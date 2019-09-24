package org.deafop.deafopreport;

import java.io.Serializable;

public class Report implements Serializable {
    private String id;
    private String project;
    private String manager;
    private String title;
    private String date;
    private String description;
    private String file_url;

public Report(){}

    public Report( String project, String manager, String title, String date, String description, String file_url) {
        this.setId(id);
        this.setProject(project);
        this.setManager(manager);
        this.setTitle(title);
        this.setDate(date);
        this.setDescription(description);
        this.setFile_url(file_url);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }


    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
}
