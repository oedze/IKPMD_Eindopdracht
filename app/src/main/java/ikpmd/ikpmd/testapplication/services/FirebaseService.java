package ikpmd.ikpmd.testapplication.services;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class FirebaseService {

    protected static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static void login(String email, String password, OnSuccessListener successListener, OnFailureListener failureListener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    public static void logout() {
        mAuth.signOut();
    }

    public static void register(String email, String password, final String displayName, final OnSuccessListener successListener, final OnFailureListener failureListener) {

         mAuth.createUserWithEmailAndPassword(email, password)
                 .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                     @Override
                     public void onSuccess(@NonNull AuthResult authResult) {
                         // Update profile to set display name
                        updateProfile(displayName, successListener, failureListener);
                     }
                 })
                 .addOnFailureListener(failureListener);

    }

    public static FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    public static void updateProfile(String displayName, OnSuccessListener successListener, OnFailureListener failureListener) {

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build();

        FirebaseService.getUser().updateProfile(profileUpdates)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    public static void addDocument(String collection, Object document, OnSuccessListener successListener, OnFailureListener failureListener) {

        String path = "/users/"+getUser().getEmail()+"/"+collection;

        db.collection(path).add(document)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    public static void getCollection(String collection, OnSuccessListener<QuerySnapshot> successListener, OnFailureListener failureListener) {

        String path = "/users/"+getUser().getEmail()+"/"+collection;

        db.collection(path).get()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    public static void getDocument(String collection, String document, OnSuccessListener<DocumentSnapshot> successListener, OnFailureListener failureListener) {

        String path = "/users/"+getUser().getEmail()+"/"+collection;

        db.collection(path).document(document).get()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

}
