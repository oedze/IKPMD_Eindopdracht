package ikpmd.ikpmd.testapplication.models;

public class Step {

    public String id;
    public String details;
    public String expectedResult;

    public Step() {}

    public Step(String id, String details, String expectedResult) {
        this.id = id;
        this.details = details;
        this.expectedResult = expectedResult;
    }

}
