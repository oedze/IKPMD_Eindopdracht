package ikpmd.ikpmd.testapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import ikpmd.ikpmd.testapplication.CreateProjectActivity;
import ikpmd.ikpmd.testapplication.ProjectActivity;
import ikpmd.ikpmd.testapplication.R;
import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.services.ProjectService;

import static ikpmd.ikpmd.testapplication.R.layout.activity_project_list_view;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    List<Project> projects = new ArrayList<>();
    ArrayAdapter adapter;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        Project pj = new Project();
        projects.add(pj);

        Button createProjectButton = root.findViewById(R.id.button_create_project);
        final Intent intent = new Intent(root.getContext(), CreateProjectActivity.class);

        final Fragment frag = this;

        createProjectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


        adapter = new ArrayAdapter<Project>(root.getContext(), activity_project_list_view, projects);

        ListView listView = root.findViewById(R.id.project_list);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "You clicked on pos: " + position, Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(getContext(), ProjectActivity.class);
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

        return root;
    }
}