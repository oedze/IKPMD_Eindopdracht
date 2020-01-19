package ikpmd.ikpmd.testapplication.models;

import androidx.annotation.NonNull;

import java.util.List;

public class Test {

    public String id;
    public String project;
    public String author;
    public String description;
    public String name;
    public String reviewer;
    public String version;
    public List<String> prerequisites;
    public List<String> data;

    public Test() {}

    public Test(String id, String project, String author, String description, String reviewer, String version, List<String> prerequisites, List<String> data) {
        this.id = id;
        this.project = project;
        this.author = author;
        this.description = description;
        this.reviewer = reviewer;
        this.version = version;
        this.prerequisites = prerequisites;
        this.data = data;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
