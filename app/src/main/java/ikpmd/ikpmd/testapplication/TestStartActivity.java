package ikpmd.ikpmd.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.services.RoundService;

public class TestStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_start);

        insertTestValues(RoundService.getCurrentTest());

        final Intent intent_gotoStep = new Intent(this, TestStepActivity.class);

        Button button_start =  findViewById(R.id.button_teststart_start);
        Button button_skip =  findViewById(R.id.button_teststart_skip);

        button_start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(intent_gotoStep);
            }
        });

        button_skip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                skip();
            }
        });
    }

    private void insertTestValues(Test test) {

        TextView textName = findViewById(R.id.text_teststart_name);
        TextView textVersion = findViewById(R.id.text_teststart_version);
        TextView textAuthor = findViewById(R.id.text_teststart_author);
        TextView textDescription = findViewById(R.id.text_teststart_description);
        TextView textPrerequisites = findViewById(R.id.text_teststart_prerequisites);

        textName.setText(test.getName());
        textVersion.setText("Versie "+test.getVersion());
        textAuthor.setText("Geschreven door "+test.getAuthor());
        textDescription.setText(test.getDescription());

        String prerequisitesString = "";
        for (String prerequisite : test.getPrerequisites()) {
            prerequisitesString += " - " + prerequisite + "\n";
        }

        textPrerequisites.setText("Startvoorwaardes:\n"+prerequisitesString);

    }

    private void skip() {

    }

}
