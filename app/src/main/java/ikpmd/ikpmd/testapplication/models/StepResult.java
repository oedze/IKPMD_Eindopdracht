package ikpmd.ikpmd.testapplication.models;

public class StepResult {

    public String id;
    public String actualResult;
    public String passed;

    public StepResult() {}

    public StepResult(String id, String actualResult, String passed) {
        this.id = id;
        this.actualResult = actualResult;
        this.passed = passed;
    }

}
