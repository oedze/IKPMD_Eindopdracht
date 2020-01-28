package ikpmd.ikpmd.testapplication.services;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.models.Round;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.models.TestResult;

public class RoundService extends FirebaseService {

    public static Project project;
    public static List<Test> tests;
    public static int currentTestIndex = 0;
    public static List<TestResult> testResults;
    public static String activeRoundId;

    public static Test getCurrentTest() {
        return tests.get(currentTestIndex);
    }


    public static void getRoundsOfProject(String projectId, final OnSuccessListener<List<Round>> sl, final OnFailureListener fl){
        final List<Round> list = new ArrayList<>();

        getCollection("projects/"+projectId+"/rounds", new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(DocumentSnapshot ds: queryDocumentSnapshots.getDocuments()){
                    Round r = ds.toObject(Round.class);
                    r.setId(ds.getId());
                    list.add(r);
                }
                sl.onSuccess(list);
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                fl.onFailure(new Exception("Could not load rounds"));
            }
        });
    }
}
