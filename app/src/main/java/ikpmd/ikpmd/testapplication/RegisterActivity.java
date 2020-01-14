package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.logging.Logger;

import ikpmd.ikpmd.testapplication.services.FirebaseService;
import ikpmd.ikpmd.testapplication.utils.Validator;

public class RegisterActivity extends AppCompatActivity {


    static String TAG = "REGISTER";


    EditText email;
    EditText name;
    EditText password;
    EditText confirmPassword;
    Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        email = findViewById(R.id.edittext_register_email);
        name = findViewById(R.id.edittext_register_name);
        password = findViewById(R.id.edittext_register_password);
        confirmPassword = findViewById(R.id.edittext_register_confirmpassword);
        registerButton = findViewById(R.id.button_register_register);


        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                trimInputs();
                if(isValid()){
                    //Toast.makeText(getApplicationContext(), "Goede invoer", Toast.LENGTH_SHORT).show();
                    //TODO add firestore service to create account;\
                    FirebaseService.register(
                            email.getText().toString(),
                            password.getText().toString(),
                            name.getText().toString(), new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    Toast.makeText(getApplicationContext(), "Succesvolle registratie", Toast.LENGTH_SHORT).show();
                                }
                            }, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) { email.setText("");
                                    password.setText("");
                                    confirmPassword.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "Dit email address is al in gebruik", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP, 0, 0);
                                    toast.show();
                                }
                            });
                }else{
                    Toast.makeText(getApplicationContext(),"Foute invoer",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void trimInputs(){
        email.setText(email.getText().toString().trim());
        name.setText(name.getText().toString().trim());
        password.setText(password.getText().toString().trim());
        confirmPassword.setText(confirmPassword.getText().toString().trim());
    }


    public boolean isValid(){
        if(!Validator.isEmail(email.getText())){
            Log.d(TAG, "Invalid Email");
            return false;
        }

        if(!Validator.isName(name.getText())){
            Log.d(TAG, "Invalid name");
            return false;
        }

        if(!Validator.isPassword(password.getText())){
            Log.d(TAG, "Invalid password");
            return false;
        }

        if(!password.getText().toString().equals(confirmPassword.getText().toString())){
            Log.d(TAG, "Passwords do not mach");
            return false;
        }

        return true;

    }
}
