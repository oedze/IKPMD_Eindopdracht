package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Document;
import com.google.firestore.v1.FirestoreGrpc;

import ikpmd.ikpmd.testapplication.services.FirebaseService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        register("nigerfagoot@gmail.com","wachtwoord","Niger Fagoot");
//        login("nigerfagoot@gmail.com", "wachtwoord");
//        getCollection("test");
//        FirebaseService.logout();
        getCollection("projects");

    }

    private void register(String email, String password, String username) {

        FirebaseService.register(email, password, username,
                new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        System.out.println("REGISTER SUCCESS!");
                        System.out.println(FirebaseService.getUser().getDisplayName());
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("REGISTER FAIL!");
                    }
                });
    }

    private void login(String email, String password) {

        FirebaseService.login(email, password,
                new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        System.out.println("LOGIN SUCCESS!");
                        System.out.println(FirebaseService.getUser().getDisplayName());
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("LOGIN FAIL!");
                        e.printStackTrace();
                    }
                });

    }

    private void getCollection(String collection) {
        FirebaseService.getCollection(collection,
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot collection) {
                        System.out.println("COLLECTION SUCCESS!");
                        System.out.println(collection.size());
                        for (DocumentSnapshot document : collection.getDocuments()) {
                            System.out.println(document.getId());
                        }
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("COLLECTION FAIL!");
                        e.printStackTrace();
                    }
                });
    }

    private void getDocument(String collection, String document) {
        FirebaseService.getDocument(collection, document,
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        System.out.println("DOCUMENT SUCCESS!");
                        System.out.println(document.getId()+": name: "+document.get("name"));
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("DOCUMENT FAIL!");
                        e.printStackTrace();
                    }
                });
    }
}
