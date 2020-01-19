package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.models.Round;
import ikpmd.ikpmd.testapplication.models.Step;
import ikpmd.ikpmd.testapplication.models.StepResult;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.models.TestResult;
import ikpmd.ikpmd.testapplication.services.FirebaseService;

public class TestStepActivity extends AppCompatActivity {

    Project project;
    Test test;
    Round round;
    List<Step> steps = new ArrayList();
    List<StepResult> stepResults = new ArrayList();
    int currentStepIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_step);

        //TODO get project and test from bundle
//        getSteps(project.getId(), test.getId());

        getSteps("uasuLd4k2DgbLOdh3QKw","test1");

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
                        nextStep();

                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    private void nextStep() {

        if (currentStepIndex >= 0) addStepResult();

        currentStepIndex++;

        if (currentStepIndex >= steps.size()) {
            complete();
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

        stepResults.add(stepResult);

    }

    private void insertStepValues(Step step) {

        ProgressBar progressStep = findViewById(R.id.progress_step);
        TextView textStepNumber = findViewById(R.id.text_step_number);
        TextView textStepDetails = findViewById(R.id.text_step_detail);
        TextView textStepExpectedResult = findViewById(R.id.text_step_expectedresult);
        RadioButton radioStepPassed = findViewById(R.id.radio_step_passed);
        RadioButton radioStepFailed = findViewById(R.id.radio_step_failed);
        TextView textStepActualResultLabel = findViewById(R.id.text_step_actualresult_label);
        EditText editTextStepActualResult = findViewById(R.id.edittext_step_actualresult);
        Button buttonStepNext = findViewById(R.id.button_step_next);

        progressStep.setMax(steps.size());
        progressStep.setProgress(currentStepIndex);
        textStepNumber.setText("Step #"+step.getNumber());
        textStepDetails.setText(step.getDetails());
        textStepExpectedResult.setText(step.getExpectedResult());
        radioStepPassed.setChecked(false);
        radioStepFailed.setChecked(false);
        textStepActualResultLabel.setVisibility(View.INVISIBLE);
        editTextStepActualResult.setVisibility(View.INVISIBLE);
        editTextStepActualResult.setText("");
        buttonStepNext.setEnabled(false);

    }

    private void complete() {

        if (true) return;

        TestResult testResult = new TestResult();
        // TODO set date and tester

        // Add all stepResults to testResult
        for (StepResult stepResult : stepResults) {

            FirebaseService.addDocument("projects/" + project.getId() + "/rounds/" + round.getId() + "/testresults/" + testResult.getId() + "/steps", stepResult,
                    new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {

                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }

    }


}
