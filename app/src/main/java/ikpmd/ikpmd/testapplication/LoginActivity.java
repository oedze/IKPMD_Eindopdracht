package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import ikpmd.ikpmd.testapplication.services.FirebaseService;
import ikpmd.ikpmd.testapplication.utils.Validator;

public class LoginActivity extends AppCompatActivity {

    public static String TAG = "LOGIN";

    EditText emailField;
    EditText passwordField;
    Button logInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        logInButton = findViewById(R.id.button_login_login);
        emailField = findViewById(R.id.edittext_login_email);
        passwordField = findViewById(R.id.edittext_login_password);


        final Intent intent = new Intent(getApplicationContext(), DashhboardActivity.class);

        if(FirebaseService.getUser() != null){
            startActivity(intent);
        }


        logInButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(TAG, "Onclick for loginButtonCalled" );

                trimInputs();

                if(isValid()){
                    Toast.makeText(getApplicationContext(), "Goede invoer", Toast.LENGTH_SHORT).show();
                    //TODO add to firestore login functionality
                    FirebaseService.login(emailField.getText().toString(), passwordField.getText().toString(), new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            startActivity(intent);
                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Foute invoer", Toast.LENGTH_SHORT).show();
                }

                //TODO add login functionality;
            }
        });


    }


    public void trimInputs(){
        Log.d(TAG, "Trim Inputs called");
        this.emailField.setText(this.emailField.getText().toString().trim());
        this.passwordField.setText(this.passwordField.getText().toString().trim());
    }


    public boolean isValid(){
        Log.d(TAG, "IsValid Called");
        if(!Validator.isEmail(emailField.getText())){
            Log.d(TAG, "Invalid email");
            return false;
        }

        if(!Validator.isPassword(passwordField.getText())){
            Log.d(TAG, "Invalid password");
            return false;
        }

        return true;
    }
}
