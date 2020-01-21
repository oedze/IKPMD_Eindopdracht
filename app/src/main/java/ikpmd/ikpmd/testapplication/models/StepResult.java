package ikpmd.ikpmd.testapplication.models;

public class StepResult {

    public String id;
    public String actualResult;
    public String passed;
    public String stepId;

    public StepResult() {}

    public StepResult(String id, String actualResult, String passed, String stepId) {
        this.id = id;
        this.actualResult = actualResult;
        this.passed = passed;
        this.stepId = stepId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActualResult() {
        return actualResult;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }
}
