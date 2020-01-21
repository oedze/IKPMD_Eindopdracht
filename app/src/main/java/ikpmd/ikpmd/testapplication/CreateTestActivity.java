package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.Step;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.models.TestData;
import ikpmd.ikpmd.testapplication.services.ProjectService;
import ikpmd.ikpmd.testapplication.ui.TestDataAdaptor;
import ikpmd.ikpmd.testapplication.ui.TestPreConditionAdapter;
import ikpmd.ikpmd.testapplication.ui.TestStepAdapter;

public class CreateTestActivity extends AppCompatActivity implements TestPreConditionAdapter.TextChangedListener,
        TestPreConditionAdapter.ButtonPressedEventListener,
        TestDataAdaptor.DataChangedListener,
        TestDataAdaptor.TestDataCloseButtonPressedListener,
        TestStepAdapter.StepChangedListener,
        TestStepAdapter.TestStepCloseButtonPressedListener{

    RecyclerView recyclerView;
    RecyclerView testDataRecyclerView;
    RecyclerView testStepRecyclerView;

    TestPreConditionAdapter adaptor;
    TestDataAdaptor testDataAdapter;
    TestStepAdapter testStepAdapter;

    List<String> preConditionList = new ArrayList();
    List<TestData> testDataList = new ArrayList();
    List<Step> testStepList = new ArrayList<>();

    EditText testName;
    EditText testMadeBy;
    EditText testNumber;
    EditText testDescription;
    EditText testVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        preConditionList.add("Gebruiker is ingelogd");
        preConditionList.add("Gebruiker is op admin scherm");

        testDataList.add(new TestData("username", "oetze"));
        testDataList.add(new TestData("password", "12345678"));

        testStepList.add(new Step("Klik op inloggen", "syteem geeft aan je bent inglogd"));

        recyclerView = findViewById(R.id.rv_create_test_pre_conditions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        testDataRecyclerView = findViewById(R.id.rv_create_test_data);
        testDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        testStepRecyclerView = findViewById(R.id.rv_create_test_step);
        testStepRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adaptor = new TestPreConditionAdapter(this, preConditionList);
        testDataAdapter = new TestDataAdaptor(this, testDataList);
        testStepAdapter = new TestStepAdapter(this, testStepList);

        adaptor.setTextChangedListener(this);
        adaptor.setButtonPressedListener(this);

        testDataAdapter.setCloseButtonListener(this);
        testDataAdapter.setDataChangedListener(this);

        testStepAdapter.setStepChangedListener(this);
        testStepAdapter.setCloseButtonListener(this);

        recyclerView.setAdapter(adaptor);
        testDataRecyclerView.setAdapter(testDataAdapter);
        testStepRecyclerView.setAdapter(testStepAdapter);


        Button addDataButton =  findViewById(R.id.button_create_test_add_data);
        Button addStepButton = findViewById(R.id.button_create_test_add_step);
        Button addPreButton = (Button)  findViewById(R.id.button_add_pre);
        Button createButton = findViewById(R.id.button_create_test_create);

        testName = findViewById(R.id.text_input_test_name);
        testMadeBy = findViewById(R.id.text_input_made_by);
        testNumber = findViewById(R.id.text_input_test_number);
        testDescription = findViewById(R.id.text_input_test_description);
        testVersion = findViewById(R.id.text_input_test_version);

        addPreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                preConditionList.add("");
                adaptor.notifyDataSetChanged();
                v.computeScroll();
            }
        });


        addDataButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                testDataList.add(new TestData("", ""));
                testDataAdapter.notifyDataSetChanged();
                v.computeScroll();
            }
        });

        addStepButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                testStepList.add(new Step("", ""));
                testStepAdapter.notifyDataSetChanged();
                v.computeScroll();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int errorCount =0;
                if(testName.getText().length() == 0){
                   showError("Niks ingevuld voor testnaam");
                   errorCount++;
                }
                if(testMadeBy.getText().length() == 0){
                    showError("Niks ingevuld voor 'gemaakt door'");
                    errorCount++;
                }
                if(testNumber.getText().length() == 0){
                    showError("Niks ingevuld voor 'test nummer'");
                    errorCount++;
                }
                if(testDescription.getText().length() == 0){
                    showError("Niks ingevuld voor 'test omschrijving'");
                    errorCount++;
                }
                if(testVersion.getText().length() == 0){
                    showError("Niks ingevuld voor 'test version'");
                    errorCount++;
                }
                if(testStepList.size() == 0){
                    showError("Geen teststappen ingevoerd");
                    errorCount++;
                }
                for(Step step: testStepList){
                    if(step.details.length() == 0 || step.expectedResult.length() == 0){
                        showError("Incomplete stappen");
                        errorCount++;
                        break;
                    }
                }

                for(TestData data: testDataList){
                    if(data.getKey().length() == 0 || data.getValue().length() == 0){
                        showError("Incomplete Test Data");
                        errorCount++;
                        break;
                    }
                }

                if(errorCount == 0){
                    Test test = new Test();
                    test.setAuthor(testMadeBy.getText().toString());
                    test.setName(testName.getText().toString());
                    test.setDescription(testDescription.getText().toString());
                    test.setVersion(testVersion.getText().toString());
                    test.setPrerequisites(preConditionList);
                    test.setData(testDataList);
                    test.setSteps(testStepList);

                    Intent intent = getIntent();
                    final String projectId = intent.getStringExtra("projectId");

                    ProjectService.addTestToProject(projectId, test, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            showError("Gelukt met toeveogen!");
                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showError("Er ging iets mis met toevoegen");
                        }
                    });
                }



            }
        });





    }

    public void showError(String text){
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0,0);
        toast.show();
    }

    @Override
    public void onTextChanged(String newText, int position) {
          preConditionList.set(position, newText);
    }

    @Override
    public void onButtonPressed(int position) {
        preConditionList.remove(position);
        adaptor.notifyDataSetChanged();
    }

    @Override
    public void onTestDataListChange(TestData newData, int position) {
        testDataList.set(position, newData);
    }

    @Override
    public void onTestDataRemoveTest(int position) {
        testDataList.remove(position);
        testDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTestStepListChange(Step newStep, int position) {
        testStepList.set(position, newStep);

    }

    @Override
    public void onTestStepRemoveTest(int position) {
        testStepList.remove(position);
        testStepAdapter.notifyDataSetChanged();
    }
}

