package ikpmd.ikpmd.testapplication.models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;

public class Test {

    private String id;
    private String author;
    private String description;
    private String name;
    private String version;
    private List<String> prerequisites;
    private List<TestData> data;
    private List<Step> steps;

    public Test() {}



    @NonNull
    @Override
    public String toString() {
        return name;
    }


    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    @Exclude
    public List<TestData> getData() {
        return data;
    }

    public void setData(List<TestData> data) {
        this.data = data;
    }

    @Exclude
    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}
