package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.models.TestResult;
import ikpmd.ikpmd.testapplication.services.FirebaseService;
import ikpmd.ikpmd.testapplication.services.ProjectService;
import ikpmd.ikpmd.testapplication.services.RoundService;

public class TestRoundResultActivity extends AppCompatActivity {

    private boolean testsReceived = false;
    private boolean testResultsReceived = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_round_result);

        loadTests(ProjectService.activeProjecId);
        loadTestResults(ProjectService.activeProjecId, RoundService.activeRoundId);
    }

    private void loadTests(String projectId) {

        FirebaseService.getCollection("projects/" + projectId + "/tests", new OnSuccessListener<QuerySnapshot>() {
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

                testsReceived = true;
                if (testResultsReceived)
                    insertTestResultValues();

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void loadTestResults(String projectId, String roundId) {

        FirebaseService.getCollection("projects/" + projectId + "/rounds/" + roundId + "/testresults", new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                RoundService.tests = new ArrayList();
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    TestResult testResult = document.toObject(TestResult.class);
                    testResult.setId(document.getId());
                    RoundService.testResults.add(testResult);
                }

                testResultsReceived = true;
                if (testsReceived)
                    insertTestResultValues();

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void insertTestResultValues() {

        ProgressBar progressStep = findViewById(R.id.progress_roundresult);
        TextView passedLabel = findViewById(R.id.text_roundresult_passed);

        progressStep.setMax(RoundService.testResults.size());
        progressStep.setProgress(countPassedTests());
        passedLabel.setText(countPassedTests()+"/"+RoundService.testResults.size()+"\nPassed");

        insertTests();

    }

    private int countPassedTests() {

        int completedTests = 0;

        for(TestResult testResult : RoundService.testResults)
            if (testResult.getPassed().equals("Passed"))
                completedTests++;

        return completedTests;
    }

    private void insertTests() {

        LinearLayout linearPassed = findViewById(R.id.linear_roundresult_passed);
        LinearLayout linearSkipped = findViewById(R.id.linear_roundresult_skipped);
        LinearLayout linearFailed = findViewById(R.id.linear_roundresult_failed);

        linearPassed.removeAllViews();
        linearSkipped.removeAllViews();
        linearFailed.removeAllViews();

        for(TestResult testResult : RoundService.testResults) {

            TextView testName = new TextView(this);
            testName.setText(RoundService.getTestById(testResult.getTestId()).getName());
            testName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            switch (testResult.getPassed()) {
                case "Passed":
                    testName.setTextColor(getResources().getColor(R.color.colorTestPassed));
                    linearPassed.addView(testName);
                    break;
                case "Skipped":
                    testName.setTextColor(getResources().getColor(R.color.colorTestSkipped));
                    linearSkipped.addView(testName);
                    break;
                case "Failed":
                    testName.setTextColor(getResources().getColor(R.color.colorTestFailed));
                    linearFailed.addView(testName);
                    break;
            }
        }

    }

}
