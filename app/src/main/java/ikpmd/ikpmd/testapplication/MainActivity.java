package ikpmd.ikpmd.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.StepResult;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.models.TestResult;
import ikpmd.ikpmd.testapplication.services.ExcelService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Intent intent = new Intent(this, CreateTestActivity.class);
        final Intent intent2 = new Intent(this, LoginActivity.class);
        final Intent intent3 = new Intent(this, RegisterActivity.class);
        final Intent intent4 = new Intent(this, StartTestRoundActivity.class);
        final Intent intent5 = new Intent(this, TestStepActivity.class);


        Button button = (Button) findViewById(R.id.button);
        Button gotoLogin = findViewById(R.id.button_main_gotologin);
        Button gotoRegister = findViewById(R.id.button_main_gotoregister);
        Button gotoRound = findViewById(R.id.button_main_gotoround);
        Button gotoTestStep = findViewById(R.id.button_main_gotostep);
        Button export = findViewById(R.id.button_main_export_data);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });

        gotoRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });

        gotoRound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent4);
            }
        });

        gotoTestStep.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent5);
            }
        });

        final List<TestResult> list = new ArrayList<>();

        List<StepResult> srList = new ArrayList<>();
        srList.add(new StepResult("0", "failed", "failed", "0"));
        srList.add(new StepResult("0", "", "passed", "0"));
        srList.add(new StepResult("0", "failed", "passed", "0"));
        srList.add(new StepResult("0", "failed", "passed", "0"));
        srList.add(new StepResult("0", "failed", "passed", "0"));
        TestResult ts = new TestResult("text", "author", "tommorow");
        ts.setStepResults(srList);
        list.add(ts);
        list.add(ts);
        list.add(ts);
        list.add(ts);
        list.add(ts);
        list.add(ts);
        list.add(ts);
        list.add(ts);

        export.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View v) {
                ExcelService.export(getApplicationContext(), "testresults.csv", list, new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri f) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_STREAM, f);
                        intent.setType("text/csv");

                        Intent shareIntent =Intent.createChooser(intent, null);
                        startActivity(shareIntent);
                    }
                });
            }
        });




    }


    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{permission}, requestCode);

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, permission + " is already granted.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
