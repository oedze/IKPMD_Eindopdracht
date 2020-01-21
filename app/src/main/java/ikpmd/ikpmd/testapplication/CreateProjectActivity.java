package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.services.ProjectService;

public class CreateProjectActivity extends AppCompatActivity {

    public static String TAG = "project_create";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        final EditText name = findViewById(R.id.edittext_create_project_name);
        final EditText description = findViewById(R.id.edittext_create_project_description);
        final Button createTest = (Button) findViewById(R.id.button_create_project_create);
        Log.d(TAG, "Attempting to set On click Listener");
        createTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked");
                if(name.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "Ongeldige projectnaam", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(description.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "Ongeldige omschrijving", Toast.LENGTH_SHORT).show();
                    return;
                }

                Project project = new Project();
                project.setName(name.getText().toString());
                project.setDescription(description.getText().toString());
                ProjectService.createProject(project, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(getApplicationContext(), "Successvol aangemaakt", Toast.LENGTH_SHORT);
                        finish();
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Er ging iets mis", Toast.LENGTH_SHORT);
                    }
                });
            }
        });

    }
}
