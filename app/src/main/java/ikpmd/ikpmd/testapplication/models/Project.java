package ikpmd.ikpmd.testapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {

    public String id;
    public String name;
    public String description;
    public List<Test> tests;

    public Project() {}

    public Project(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }



    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Exclude
    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
