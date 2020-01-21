package ikpmd.ikpmd.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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



        for (TestResult testResult : RoundService.testResults) {
            System.out.println("TestResult: "+testResult.getId());
            for (StepResult stepResult : testResult.stepResults) {
                System.out.println("StepResult: "+stepResult.getId());
            }
        }

    }

}
