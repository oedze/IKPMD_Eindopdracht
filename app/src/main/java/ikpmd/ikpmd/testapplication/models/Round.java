package ikpmd.ikpmd.testapplication.models;

public class Round {

    public String id;

    public Round() {}

    public Round(String id, String tester) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
