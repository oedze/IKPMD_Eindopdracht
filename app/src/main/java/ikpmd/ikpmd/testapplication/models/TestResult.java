package ikpmd.ikpmd.testapplication.models;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class TestResult {

    @Exclude
    private String id;
    private String passed;
    private String testId;
    @Exclude
    private List<StepResult> stepResults;

    public TestResult() {}

    public TestResult(String id, String passed, String testId) {
        this.id = id;
        this.passed = passed;
        this.testId = testId;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getPassed() {return passed;}

    public void setPassed(String passed) {this.passed = passed;}

    public String getTestId() {
        return testId;
    }

    @Exclude
    public List<StepResult> getStepResults() {
        return stepResults;
    }

    @Exclude
    public void setStepResults(List<StepResult> stepResults) {
        this.stepResults = stepResults;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }
}
