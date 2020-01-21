//package ikpmd.ikpmd.testapplication;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.ActivityChooserView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ikpmd.ikpmd.testapplication.R;
//import ikpmd.ikpmd.testapplication.models.Project;
//import ikpmd.ikpmd.testapplication.models.Test;
//import ikpmd.ikpmd.testapplication.services.ProjectService;
//
//import static ikpmd.ikpmd.testapplication.R.layout.activity_project_list_view;
//import static ikpmd.ikpmd.testapplication.R.layout.activity_test_list;
//
//public class ProjectActivity extends AppCompatActivity{
//    Project project;
//
//
//    TextView projectDescription;
//    TextView projectName;
//    ListView testListView;
//    List<Test> testList = new ArrayList<Test>();
//
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_project);
//
//
//        projectName = (TextView)findViewById(R.id.text_project_name);
//        projectDescription = (TextView)findViewById(R.id.text_project_description);
//        testListView = (ListView)findViewById(R.id.list_project_tests);
//
//        final ArrayAdapter adapter = new ArrayAdapter<Test>(getBaseContext(), activity_test_list, testList);
//        testListView.setAdapter(adapter);
//
//
//        Intent intent = getIntent();
//        String projectId = intent.getStringExtra("projectId");
//
//        ProjectService.getProject(projectId, new OnSuccessListener<Project>() {
//            @Override
//            public void onSuccess(Project project) {
//                projectName.setText(project.getName());
//                projectDescription.setText(project.getDescription());
//
//                testList.clear();
//                testList.addAll(project.getTests());
//                adapter.notifyDataSetChanged();
//            }
//        }, new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//
//    }
//}

package ikpmd.ikpmd.testapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ProjectActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

    }

}