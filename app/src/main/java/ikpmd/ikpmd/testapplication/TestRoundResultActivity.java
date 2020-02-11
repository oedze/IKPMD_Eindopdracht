package ikpmd.ikpmd.testapplication;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.models.Step;
import ikpmd.ikpmd.testapplication.models.StepResult;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.models.TestResult;
import ikpmd.ikpmd.testapplication.services.ExcelService;
import ikpmd.ikpmd.testapplication.services.FirebaseService;
import ikpmd.ikpmd.testapplication.services.ProjectService;
import ikpmd.ikpmd.testapplication.services.RoundService;

public class TestRoundResultActivity extends AppCompatActivity {

    private boolean testsReceived = false;
    private boolean testResultsReceived = false;
    private static String TAG = "TestRoundResultActivity";

    private static int stepResultLoadCounter;
    private static int stepResultLoadAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_round_result);

        loadTests(ProjectService.activeProjecId);
        loadTestResults(ProjectService.activeProjecId, RoundService.activeRoundId);

        Button buttonExport = findViewById(R.id.button_roundresult_export);
        buttonExport.setEnabled(false);

        buttonExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                export();
            }
        });

        final Intent intent_back = new Intent(this, ProjectActivity.class);
        intent_back.putExtra("projectId", ProjectService.activeProjecId);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(intent_back);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

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

                RoundService.testResults = new ArrayList();
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

    private void loadStepResults(final String projectId, String roundId, final OnCompleteListener onCompleteListener) {

        stepResultLoadAmount = 0;
        stepResultLoadCounter = 0;

        for (final TestResult testResult : RoundService.testResults) {

            stepResultLoadAmount++;

            FirebaseService.getCollection("projects/" + projectId + "/rounds/" + roundId + "/testresults/" + testResult.getId() + "/stepresults", new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    testResult.setStepResults(new ArrayList());
                    for (final DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        StepResult stepResult = document.toObject(StepResult.class);
                        stepResult.setId(document.getId());
                        testResult.getStepResults().add(stepResult);
                        insertTestStepNames(projectId, testResult);
                        insertTestNames(projectId, testResult);

                    }
                    stepResultLoadCounter++;

                    if (stepResultLoadCounter >= stepResultLoadAmount)
                        onCompleteListener.onComplete(null);


                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });

        }

    }

    private void insertTestNames(String projectId, final TestResult testResult){
        FirebaseService.getDocument("projects/" + projectId + "/tests", testResult.getTestId(), new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                testResult.setTest(documentSnapshot.toObject(Test.class));
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Something went wrong getting test names");
            }
        });
    }

    private void insertTestStepNames(String projectId, final TestResult testResult){
        FirebaseService.getCollection("projects/" + projectId + "/tests/" + testResult.getTestId() + "/steps", new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot doc: queryDocumentSnapshots.getDocuments()){
                    testResult.getStepNames().add(doc.toObject(Step.class));
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
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

        loadStepResults(ProjectService.activeProjecId, RoundService.activeRoundId, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                Button buttonExport = findViewById(R.id.button_roundresult_export);
                buttonExport.setEnabled(true);
            }
        });

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

    private void export() {

        ExcelService.export(this, RoundService.activeRoundId+".csv", RoundService.testResults, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("text/csv");

                Intent shareIntent =Intent.createChooser(intent, null);
                startActivity(shareIntent);
            }
        });

    }

}
