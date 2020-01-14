package ikpmd.ikpmd.testapplication.models;

public class TestResult {

    public String id;
    public String tester;
    public String date;

    public TestResult() {}

    public TestResult(String id, String tester, String date) {
        this.id = id;
        this.tester = tester;
        this.date = date;
    }

}
