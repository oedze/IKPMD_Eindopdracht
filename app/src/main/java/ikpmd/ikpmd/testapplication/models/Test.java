package ikpmd.ikpmd.testapplication.models;

public class Test {

    public String id;
    public String author;
    public String description;
    public String reviewer;
    public String version;
    public String[] prerequisites;
    public String[] data;

    public Test() {}

    public Test(String id, String author, String description, String reviewer, String version, String[] prerequisites, String[] data) {
        this.id = id;
        this.author = author;
        this.description = description;
        this.reviewer = reviewer;
        this.version = version;
        this.prerequisites = prerequisites;
        this.data = data;
    }

}
