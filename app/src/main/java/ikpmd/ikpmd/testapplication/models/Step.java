package ikpmd.ikpmd.testapplication.models;

public class Step {

    public String details;
    public String expectedResult;

    public Step() {}

    public Step(String details, String expectedResult) {
        this.details = details;
        this.expectedResult = expectedResult;
    }

}
