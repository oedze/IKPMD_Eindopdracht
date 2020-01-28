package ikpmd.ikpmd.testapplication.models;

import com.google.firebase.firestore.Exclude;

public class Round {

    @Exclude
    private String id;
    private String date;
    private String tester;

    public Round() {}

    public Round(String id, String date, String tester) {
        this.id = id;
        this.date = date;
        this.tester = tester;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }
}
