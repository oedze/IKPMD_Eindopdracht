package ikpmd.ikpmd.testapplication.services;

import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.models.Round;
import ikpmd.ikpmd.testapplication.models.StepResult;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.models.TestResult;

public class RoundService extends FirebaseService {

    public static Project project;
    public static List<Test> tests;
    public static int currentTestIndex = 0;
    public static List<TestResult> testResults;
    public static Round round;
    public static String activeRoundId;

    private static int stepResultSaveCounter;
    private static int stepResultAmount;

    public static Test getCurrentTest() {
        return tests.get(currentTestIndex);
    }

    public static Test getTestById(String id) {
        for (Test test : tests) {
            if (test.getId().equals(id)) return test;
        }

         return null;
    }

    public static void saveRoundResult(final OnCompleteListener onCompleteListener) {

        Round round = new Round();
        round.setTester(FirebaseService.getUser().getDisplayName());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        round.setDate(formatter.format(new Date()));
        RoundService.round = round;

        stepResultSaveCounter = 0;
        stepResultAmount = 0;

        FirebaseService.addDocument("projects/" + project.getId() + "/rounds", round, new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                String roundId = documentReference.getId();

                for (TestResult testResult : testResults) {
                    saveTestResult(roundId, testResult, onCompleteListener);
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private static void saveTestResult(final String roundId, final TestResult testResult, final OnCompleteListener onCompleteListener) {

        FirebaseService.addDocument("projects/" + project.getId() + "/rounds/"+roundId+"/testresults", testResult, new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                String testResultId = documentReference.getId();

                if (testResult.getStepResults().size() > 0 ) {

                    for (StepResult stepResult : testResult.getStepResults()) {
                        stepResultAmount++;
                        saveStepResult(roundId, testResultId, stepResult, onCompleteListener);
                    }

                } else {
                    onCompleteListener.onComplete(null);
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    private static void saveStepResult(String roundId, String testResultId, StepResult stepResult, final OnCompleteListener onCompleteListener) {


        FirebaseService.addDocument("projects/" + project.getId() + "/rounds/"+roundId+"/testresults/"+testResultId+"/stepresults", stepResult, new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                stepResultSaveCounter++;
                if (stepResultSaveCounter >= stepResultAmount) {
                    onCompleteListener.onComplete(null);
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

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
