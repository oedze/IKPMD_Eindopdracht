package ikpmd.ikpmd.testapplication.models;

public class Step {

    private String id;
    private int number;
    private String details;
    private String expectedResult;

    public Step() {}

    public Step(String id, String details, String expectedResult, int number) {
        this.id = id;
        this.details = details;
        this.expectedResult = expectedResult;
        this.number = number;
    }

    public Step(String details, String expectedResult) {
        this.details = details;
        this.expectedResult = expectedResult;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }
}
