package ikpmd.ikpmd.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Intent intent = new Intent(this, CreateTestActivity.class);
        final Intent intent2 = new Intent(this, LoginActivity.class);
        final Intent intent3 = new Intent(this, RegisterActivity.class);
        final Intent intent4 = new Intent(this, StartTestRoundActivity.class);
        final Intent intent5 = new Intent(this, TestStepActivity.class);



        Button button = (Button) findViewById(R.id.button);
        Button gotoLogin = findViewById(R.id.button_main_gotologin);
        Button gotoRegister =  findViewById(R.id.button_main_gotoregister);
        Button gotoRound =  findViewById(R.id.button_main_gotoround);
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

        gotoRound.setOnClickListener(new View.OnClickListener(){

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
