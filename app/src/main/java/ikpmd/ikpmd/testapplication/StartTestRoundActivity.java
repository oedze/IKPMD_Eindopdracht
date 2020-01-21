package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.models.Round;
import ikpmd.ikpmd.testapplication.models.Step;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.services.FirebaseService;
import ikpmd.ikpmd.testapplication.services.RoundService;

public class StartTestRoundActivity extends AppCompatActivity {

    Project project;
    Round round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test_round);

        // TODO get project and test from bundle
        project = new Project("uasuLd4k2DgbLOdh3QKw","IPSEN3","ipsen3 project");
        RoundService.project = project;
        RoundService.currentTestIndex = 0;

        final Intent intent_gotoTestStart = new Intent(this, TestStartActivity.class);

        Button gotoTestStart =  findViewById(R.id.button_startround_start);

        gotoTestStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_gotoTestStart);
            }
        });

        FirebaseService.getCollection("projects/" + project.getId() + "/tests", new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                RoundService.tests = new ArrayList();
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    Test test = document.toObject(Test.class);
                    test.setId(document.getId());
                    RoundService.tests.add(test);
                }

                // Sort tests
                Collections.sort(RoundService.tests, new Comparator<Test>() {
                    @Override
                    public int compare(Test o1, Test o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}
