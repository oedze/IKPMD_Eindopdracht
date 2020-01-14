package ikpmd.ikpmd.testapplication.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.Project;

public class ProjectService extends FirebaseService {

    private static String TAG = "PROJECT_SERVICE";

    public static void getProjects(final OnSuccessListener<List<Project>> sl){


        getCollection("projects", new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d(TAG, "Loaded projects");

                List<Project> projectList = new ArrayList<>();
                for(QueryDocumentSnapshot snapshot: queryDocumentSnapshots) {

                    Project project = snapshot.toObject(Project.class);
                    project.setId(snapshot.getId());
                    projectList.add(project);
                }
                sl.onSuccess(projectList);
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e){
                Log.d(TAG,"Could not load projects");
                sl.onSuccess(new ArrayList<Project>());
            }
        });
    }
}
