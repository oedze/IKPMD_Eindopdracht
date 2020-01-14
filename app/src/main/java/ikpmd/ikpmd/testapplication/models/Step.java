package ikpmd.ikpmd.testapplication.models;

public class Step {

    public String id;
    public int number;
    public String details;
    public String expectedResult;

    public Step() {}

    public Step(String id, String details, String expectedResult, int number) {
        this.id = id;
        this.details = details;
        this.expectedResult = expectedResult;
        this.number = number;
    }

}
