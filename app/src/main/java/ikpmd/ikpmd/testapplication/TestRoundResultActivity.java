package ikpmd.ikpmd.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.models.Round;
import ikpmd.ikpmd.testapplication.models.StepResult;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.models.TestResult;
import ikpmd.ikpmd.testapplication.services.RoundService;

public class TestRoundResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_round_result);

        insertTestResultValues();
    }

    private void insertTestResultValues() {

        ProgressBar progressStep = findViewById(R.id.progress_roundresult);
        TextView passedLabel = findViewById(R.id.text_roundresult_passed);

        progressStep.setMax(RoundService.testResults.size());
        progressStep.setProgress(countPassedTests());
        passedLabel.setText(countPassedTests()+"/"+RoundService.testResults.size()+"\nPassed");

    }

    private int countPassedTests() {

        int completedTests = 0;

        for(TestResult testResult : RoundService.testResults)
            if (testResult.getPassed().equals("Passed"))
                completedTests++;

        return completedTests;
    }

    private void insertTests() {

        for(TestResult testResult : RoundService.testResults) {

            switch (testResult.getPassed()) {
                case "Passed":
                    // TODO ADD TO PASSED LIST
                    break;
                case "Failed":
                    // TODO ADD TO FAILED LIST
                    break;
                case "Skipped":
                    // TODO ADD TO SKIPPED LIST
                    break;
            }
        }

    }

}
