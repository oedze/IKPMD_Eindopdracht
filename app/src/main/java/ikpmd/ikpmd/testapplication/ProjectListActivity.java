package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.services.FirebaseService;
import ikpmd.ikpmd.testapplication.services.ProjectService;
import ikpmd.ikpmd.testapplication.ui.home.HomeViewModel;

import static ikpmd.ikpmd.testapplication.R.layout.activity_project_list_view;

public class ProjectListActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_project_list);
//    }


    List<Project> projects = new ArrayList<>();
    ArrayAdapter adapter;
    Button logoutButton;

    @Override
    public void onResume() {
        super.onResume();

        ProjectService.getProjects(new OnSuccessListener<List<Project>>() {
            @Override
            public void onSuccess(List<Project> pro) {
                projects.clear();
                projects.addAll(pro);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        Project pj = new Project();
        projects.add(pj);

        Button createProjectButton = findViewById(R.id.button_create_project);
        logoutButton = findViewById(R.id.button_project_list_logout);


        final Intent gotoLoginIntent = new Intent(this, LoginActivity.class);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseService.logout();
                startActivity(gotoLoginIntent);
            }
        });


        final Intent intent = new Intent(this, CreateProjectActivity.class);


        createProjectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


        adapter = new ArrayAdapter<Project>(this, activity_project_list_view, projects);

        ListView listView = findViewById(R.id.project_list);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "You clicked on pos: " + position, Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(getApplicationContext(), ProjectActivity.class);
                intent.putExtra("projectId", projects.get(position).getId());
                startActivity(intent);
            }
        });

        ProjectService.getProjects(new OnSuccessListener<List<Project>>() {
            @Override
            public void onSuccess(List<Project> pro) {
                projects.clear();
                projects.addAll(pro);
                adapter.notifyDataSetChanged();
            }
        });


    }
}
