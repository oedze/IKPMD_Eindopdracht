package ikpmd.ikpmd.testapplication.models;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class TestResult {

    @Exclude
    private String id;
    private String passed;
    private String testId;
    @Exclude
    private List<StepResult> stepResults;
    private List<Step> stepNames = new ArrayList<>();
    private Test test;

    public TestResult() {}

    public TestResult(String id, String passed, String testId) {
        this.id = id;
        this.passed = passed;
        this.testId = testId;
    }

    @Exclude
    public Test getTest() {
        return test;
    }

    @Exclude
    public void setTest(Test test) {
        this.test = test;
    }

    @Exclude
    public List<Step> getStepNames() {
        return stepNames;
    }

    @Exclude
    public void setStepNames(List<Step> stepNames) {
        this.stepNames = stepNames;
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
