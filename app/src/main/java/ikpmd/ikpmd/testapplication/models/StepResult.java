package ikpmd.ikpmd.testapplication.models;

public class StepResult {

    public String id;
    public String actualResult;
    public String passed;
    public String testId;

    public StepResult() {}

    public StepResult(String id, String actualResult, String passed, String testId) {
        this.id = id;
        this.actualResult = actualResult;
        this.passed = passed;
        this.testId = testId;
    }

}
