package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import ikpmd.ikpmd.testapplication.R;
import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.services.ProjectService;
import ikpmd.ikpmd.testapplication.services.RoundService;

import static ikpmd.ikpmd.testapplication.R.layout.activity_project_list_view;
import static ikpmd.ikpmd.testapplication.R.layout.activity_test_list;

public class ProjectActivity extends AppCompatActivity{
    Project activityProject;


    TextView projectDescription;
    TextView projectName;
    ListView testListView;
    List<Test> testList = new ArrayList<Test>();
    Button createTestButton;
    Button startTestButton;

    ArrayAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        final String projectId = intent.getStringExtra("projectId");

        ProjectService.getProject(projectId, new OnSuccessListener<Project>() {
            @Override
            public void onSuccess(Project project) {
                activityProject = project;
                projectName.setText(project.getName());
                projectDescription.setText(project.getDescription());
                createTestButton.setEnabled(true);
                testList.clear();
                testList.addAll(project.getTests());
                adapter.notifyDataSetChanged();
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);


        projectName = (TextView)findViewById(R.id.text_project_name);
        projectDescription = (TextView)findViewById(R.id.text_project_description);
        testListView = (ListView)findViewById(R.id.list_project_tests);
        createTestButton = (Button)findViewById(R.id.button_project_test_create);
        createTestButton.setEnabled(false);
        startTestButton = findViewById(R.id.button_project_start_testing);

         adapter = new ArrayAdapter<Test>(getBaseContext(), activity_test_list, testList);
        testListView.setAdapter(adapter);

        onResume();




        Intent intent = getIntent();
        final String projectId = intent.getStringExtra("projectId");

        final Intent gotoCreateTest = new Intent(this, CreateTestActivity.class);
        final Intent intent_gotoTestStart = new Intent(this, TestStartActivity.class);

        createTestButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoCreateTest.putExtra("projectId", projectId);
                startActivity(gotoCreateTest);

            }
        });

        startTestButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                RoundService.project = activityProject;
                RoundService.currentTestIndex = 0;
                RoundService.tests = RoundService.project.getTests();
                startActivity(intent_gotoTestStart);
            }
        });


    }
}
