package ikpmd.ikpmd.testapplication.services;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.models.Step;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.models.TestData;

public class ProjectService extends FirebaseService {

    private static String TAG = "PROJECT_SERVICE";
    public static Project activeProject;
    public static String activeProjecId;


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


    private static void getTests(String projectId, final OnSuccessListener<List<Test>> sl){
        Log.d(TAG, "Attempting to log testst");
        db.collection("users").document(getUser().getEmail()).collection("projects").document(projectId).collection("tests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            List<Test> testList = new ArrayList<>();
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d(TAG, "COMPLETED getting testts");
                if(task.isSuccessful()){
                    Log.d(TAG, "Getting tests success, listSize: " + task.getResult().size());
                    for(QueryDocumentSnapshot snapshot: task.getResult()){
                        Test test = snapshot.toObject(Test.class);
                        test.setId(snapshot.getId());
                        testList.add(test);
                    }
                }else{
                    Log.d(TAG, "Could not load tests");
                }

                sl.onSuccess(testList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d(TAG, "SUCCES");
            }
        });
    }


    public  static void getProject(final String projectId, final OnSuccessListener<Project> sl, final OnFailureListener fl){
        final Project[] project = new Project[1];
        getDocument("projects", projectId, new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                project[0] = documentSnapshot.toObject(Project.class);
                project[0].setId(documentSnapshot.getId());
                getTests(project[0].getId(), new OnSuccessListener<List<Test>>() {
                    @Override
                    public void onSuccess(List<Test> tests) {

                        project[0].setTests(tests);
                        activeProjecId = projectId;
                        activeProject = project[0];
                        sl.onSuccess(project[0]);
                    }
                });
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                fl.onFailure(new Exception("Something went wrong"));
            }
        });
    }



    public static void addTestToProject(String projectId, Test test, final OnSuccessListener s1, final OnFailureListener fl){
        WriteBatch batch = db.batch();
        DocumentReference docRef = db.collection("users").document(getUser().getEmail()).collection("projects").document(projectId).collection("tests").document();
        batch.set(docRef, test);

        CollectionReference colRef = docRef.collection("steps");
        for(Step step: test.getSteps()){
            DocumentReference doc = colRef.document();
            batch.set(doc, step);
        }

        CollectionReference colRef2 = docRef.collection("data");
        for(TestData data: test.getData()){
            DocumentReference doc = colRef2.document();
            batch.set(doc, data);
        }

        batch.commit().addOnSuccessListener(s1).addOnFailureListener(fl);

    }



    public static void createProject(Project project, final OnSuccessListener sl, final OnFailureListener fl){
        db.collection("users").document(getUser().getEmail()).collection("projects").add(project)
                .addOnSuccessListener(sl)
                .addOnFailureListener(fl);
    }
}
