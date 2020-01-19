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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
