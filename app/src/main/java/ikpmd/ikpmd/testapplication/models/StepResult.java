package ikpmd.ikpmd.testapplication.models;

public class StepResult {

    public String actualResult;
    public String passed;

    public StepResult() {}

    public StepResult(String actualResult, String passed) {
        this.actualResult = actualResult;
        this.passed = passed;
    }

}
