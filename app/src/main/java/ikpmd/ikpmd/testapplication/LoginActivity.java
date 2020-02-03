package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import ikpmd.ikpmd.testapplication.services.FirebaseService;
import ikpmd.ikpmd.testapplication.utils.Validator;

public class LoginActivity extends AppCompatActivity {

    public static String TAG = "LOGIN";

    private Intent dashboardIntent;

    EditText emailField;
    EditText passwordField;
    Button logInButton;
    TextView gotoRegister;



    @Override
    protected void onResume() {
        super.onResume();
        final Intent intent = new Intent(this, ProjectListActivity.class);

        if(FirebaseService.getUser() != null){
            startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        dashboardIntent = new Intent(this, DashhboardActivity.class);

        logInButton = findViewById(R.id.button_login_login);
        emailField = findViewById(R.id.edittext_login_email);
        passwordField = findViewById(R.id.edittext_login_password);
        gotoRegister = findViewById(R.id.button_login_register);

        final Intent intent = new Intent(this, ProjectListActivity.class);

        onResume();

        final Intent regIntent = new Intent(this, RegisterActivity.class);
        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(regIntent);
            }
        });


        logInButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(TAG, "Onclick for loginButtonCalled" );

                trimInputs();

                if(isValid()){

                    FirebaseService.login(emailField.getText().toString(), passwordField.getText().toString(),
                            new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    startActivity(dashboardIntent);
                                }
                            }, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println(emailField.toString());
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),"Invalid credentials", Toast.LENGTH_SHORT).show();
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
