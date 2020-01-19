package ikpmd.ikpmd.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Intent intent = new Intent(this, CreateTestActivity.class);
        final Intent intent2 = new Intent(this, LoginActivity.class);
        final Intent intent3 = new Intent(this, RegisterActivity.class);
        final Intent intent4 = new Intent(this, TestStartActivity.class);
        final Intent intent5 = new Intent(this, TestStepActivity.class);



        Button button = (Button) findViewById(R.id.button);
        Button gotoLogin = findViewById(R.id.button_main_gotologin);
        Button gotoRegister =  findViewById(R.id.button_main_gotoregister);
        Button gotoTestStart =  findViewById(R.id.button_main_gototest);
        Button gotoTestStep =  findViewById(R.id.button_main_gotostep);


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(intent);
            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });

        gotoRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });

        gotoTestStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(intent4);
            }
        });

        gotoTestStep.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(intent5);
            }
        });


    }
}
