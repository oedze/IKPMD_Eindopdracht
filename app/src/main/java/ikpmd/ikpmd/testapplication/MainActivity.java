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



        Button button = (Button) findViewById(R.id.button);
        Button gotoLogin = findViewById(R.id.button_main_gotologin);
        Button gotoRegister =  findViewById(R.id.button_main_gotoregister);

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


    }
}
