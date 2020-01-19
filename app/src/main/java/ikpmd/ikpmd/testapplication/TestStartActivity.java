package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.models.Round;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.services.FirebaseService;

public class TestStartActivity extends AppCompatActivity {

    private Project project;
    private Test test;
    private Round round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_start);

        getTest("uasuLd4k2DgbLOdh3QKw", "test1");
        // TODO haal project, test en round uit bundle
    }

    private void getTest(String projectId, String testId) {

        FirebaseService.getDocument("projects/" + projectId + "/tests/", testId,
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Test test = documentSnapshot.toObject(Test.class);
                        insertTestValues(test);
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


    }

    private void insertTestValues(Test test) {

        TextView textName = findViewById(R.id.text_teststart_name);
        TextView textVersion = findViewById(R.id.text_teststart_version);
        TextView textAuthor = findViewById(R.id.text_teststart_author);
        TextView textReviewer = findViewById(R.id.text_teststart_reviewer);
        TextView textDescription = findViewById(R.id.text_teststart_description);
        TextView textPrerequisites = findViewById(R.id.text_teststart_prerequisites);

        textName.setText(test.getName());
        textVersion.setText("Version "+test.getVersion());
        textAuthor.setText("Written by "+test.getAuthor());
        textReviewer.setText("Reviewed by "+test.getReviewer());
        textDescription.setText(test.getDescription());

        String prerequisitesString = "";
        for (String prerequisite : test.getPrerequisites()) {
            prerequisitesString += " - " + prerequisite + "\n";
        }

        textPrerequisites.setText("Prerequisites:\n"+prerequisitesString);

    }

}
