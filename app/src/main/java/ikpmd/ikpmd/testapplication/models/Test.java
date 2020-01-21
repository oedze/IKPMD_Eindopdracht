package ikpmd.ikpmd.testapplication.models;

import androidx.annotation.NonNull;

import java.util.List;

public class Test {

    public String id;
    public String author;
    public String description;
    public String name;
    public String version;
    public List<String> prerequisites;
    public List<TestData> data;
    public List<Step> steps;

    public Test() {}

    public Test(String id, String author, String description, String version, List<String> prerequisites, List<TestData> data) {
        this.id = id;
        this.author = author;
        this.description = description;
        this.version = version;
        this.prerequisites = prerequisites;
        this.data = data;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
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

    public List<TestData> getData() {
        return data;
    }

    public void setData(List<TestData> data) {
        this.data = data;
    }
}
