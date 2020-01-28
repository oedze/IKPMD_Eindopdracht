package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.models.Round;
import ikpmd.ikpmd.testapplication.models.Step;
import ikpmd.ikpmd.testapplication.models.StepResult;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.models.TestData;
import ikpmd.ikpmd.testapplication.models.TestResult;
import ikpmd.ikpmd.testapplication.services.FirebaseService;
import ikpmd.ikpmd.testapplication.services.RoundService;

public class TestStepActivity extends AppCompatActivity {

    List<Step> steps = new ArrayList();
    List<TestData> testDataList = new ArrayList();
    int currentStepIndex = -1;

    boolean stepsReceived = false;
    boolean testDataReceived = false;

    private int stepResultSaveCounter;
    private int stepResultAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_step);

        getSteps(RoundService.project.getId(), RoundService.getCurrentTest().getId());
        getTestData(RoundService.project.getId(), RoundService.getCurrentTest().getId());

//        getSteps("uasuLd4k2DgbLOdh3QKw","test1");
//        getTestData("uasuLd4k2DgbLOdh3QKw","test1");

        final RadioButton radioStepPassed = findViewById(R.id.radio_step_passed);
        final RadioButton radioStepFailed = findViewById(R.id.radio_step_failed);
        final TextView textStepActualResultLabel = findViewById(R.id.text_step_actualresult_label);
        final EditText editTextStepActualResult = findViewById(R.id.edittext_step_actualresult);
        final Button buttonStepNext = findViewById(R.id.button_step_next);

        radioStepPassed.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            textStepActualResultLabel.setVisibility(View.INVISIBLE);
                            editTextStepActualResult.setVisibility(View.INVISIBLE);
                            buttonStepNext.setEnabled(true);
                        }
                    }
                }
        );

        radioStepFailed.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            textStepActualResultLabel.setVisibility(View.VISIBLE);
                            editTextStepActualResult.setVisibility(View.VISIBLE);
                            buttonStepNext.setEnabled(true);
                        }
                    }
                }
        );

        buttonStepNext.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextStep();
                    }
                }
        );
    }

    private void getSteps(String projectId, String testId) {

        FirebaseService.getCollection("projects/" + projectId + "/tests/" +testId + "/steps",
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Step step = document.toObject(Step.class);
                            step.setId(document.getId());
                            steps.add(step);
                        }

                        // Sort steps
                        Collections.sort(steps, new Comparator<Step>() {
                            @Override
                            public int compare(Step o1, Step o2) {
                                return o1.getNumber() - o2.getNumber();
                            }
                        });

                        // Load first step
                        stepsReceived = true;
                        if (testDataReceived) nextStep();

                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    private void getTestData(String projectId, String testId) {

        FirebaseService.getCollection("projects/" + projectId + "/tests/" +testId + "/data",
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            TestData testData = document.toObject(TestData.class);
                            testDataList.add(testData);
                        }

                        // Load first step
                        testDataReceived = true;
                        if (stepsReceived) nextStep();

                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    private void insertStepValues(Step step) {

        ProgressBar progressStep = findViewById(R.id.progress_step);
        TextView textStepNumber = findViewById(R.id.text_step_number);
        TextView textStepDetails = findViewById(R.id.text_step_detail);
        TextView textStepExpectedResult = findViewById(R.id.text_step_expectedresult);
        RadioGroup radioGroupStepPassed = findViewById(R.id.radiogroup_step_passed);
        TextView textStepActualResultLabel = findViewById(R.id.text_step_actualresult_label);
        EditText editTextStepActualResult = findViewById(R.id.edittext_step_actualresult);
        Button buttonStepNext = findViewById(R.id.button_step_next);

        progressStep.setMax(steps.size());
        progressStep.setProgress(currentStepIndex);
        textStepNumber.setText("Stap #"+step.getNumber());
        textStepDetails.setText(insertTestData(step));
        textStepExpectedResult.setText(step.getExpectedResult());
        radioGroupStepPassed.clearCheck();;
        textStepActualResultLabel.setVisibility(View.INVISIBLE);
        editTextStepActualResult.setVisibility(View.INVISIBLE);
        editTextStepActualResult.setText("");
        buttonStepNext.setEnabled(false);

    }

    private String insertTestData(Step step) {

        String out = step.getDetails();

        for (TestData testData : testDataList) {
            out = out.replace("#"+testData.getKey(), testData.getValue());
        }

        return out;

    }

    private void nextStep() {

        if (currentStepIndex == -1) {

            // First step
            TestResult testResult = new TestResult();
            testResult.setStepResults(new ArrayList());
            testResult.setTestId(RoundService.getCurrentTest().getId());

            RoundService.testResults.add(testResult);

        } else {

            addStepResult();

            RadioButton radioStepFailed= findViewById(R.id.radio_step_failed);
            if (radioStepFailed.isChecked()) {
                completeTest("Failed");
                return;
            }


        }

        currentStepIndex++;

        if (currentStepIndex >= steps.size()) {
            completeTest("Passed");
            return;
        }

        insertStepValues(steps.get(currentStepIndex));

    }

    private void addStepResult() {

        RadioButton radioStepPassed = findViewById(R.id.radio_step_passed);
        EditText editTextStepActualResult = findViewById(R.id.edittext_step_actualresult);

        StepResult stepResult = new StepResult();
        stepResult.setPassed( radioStepPassed.isChecked() ? "Passed" : "Failed" );
        stepResult.setActualResult( radioStepPassed.isChecked() ? steps.get(currentStepIndex).getExpectedResult() : editTextStepActualResult.getText().toString() );
        stepResult.setStepId( steps.get(currentStepIndex).getId() );

        RoundService.testResults.get(RoundService.currentTestIndex).getStepResults().add(stepResult);

    }

    private void completeTest(String passed) {

        // Test passed?
        RoundService.testResults.get(RoundService.currentTestIndex).setPassed(passed);

        RoundService.currentTestIndex++;

        if (RoundService.currentTestIndex < RoundService.tests.size()) {

            // Next test
            final Intent intent_gotoTestStart = new Intent(this, TestStartActivity.class);
            startActivity(intent_gotoTestStart);

        } else {

            System.out.println("Save round epic!");
            saveRoundResult();

        }


    }

    private void saveRoundResult() {

        // add round
            // on success
                // for testresult in testresult
                    // save testresult

        Round round = new Round();
        round.setTester(FirebaseService.getUser().getDisplayName());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        round.setDate(formatter.format(new Date()));

        stepResultSaveCounter = 0;
        stepResultSaveCounter = 0;

        FirebaseService.addDocument("projects/" + RoundService.project.getId() + "/rounds", round, new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                String roundId = documentReference.getId();

                for (TestResult testResult : RoundService.testResults) {
                    saveTestResult(roundId, testResult);
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void saveTestResult(final String roundId, final TestResult testResult) {

        // add test
            // on success
                // for step in step

        FirebaseService.addDocument("projects/" + RoundService.project.getId() + "/rounds/"+roundId+"/testresults", testResult, new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                String testResultId = documentReference.getId();

                for (StepResult stepResult : testResult.getStepResults()) {
                    stepResultAmount++;
                    saveStepResult(roundId, testResultId, stepResult);
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    private void saveStepResult(String roundId, String testResultId, StepResult stepResult) {

        // add step
            // on success
                // stepResultSaveCounter++
                // if stepResultSaveCounter >= stepResultAmount
                    // saveComplete

        FirebaseService.addDocument("projects/" + RoundService.project.getId() + "/rounds/"+roundId+"/testresults/"+testResultId+"/stepresults", stepResult, new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                stepResultSaveCounter++;
                if (stepResultSaveCounter >= stepResultAmount) {
                    saveComplete();
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void saveComplete() {

        // Round result
        final Intent intent_gotoRoundResult = new Intent(this, TestRoundResultActivity.class);
        startActivity(intent_gotoRoundResult);

    }

}
