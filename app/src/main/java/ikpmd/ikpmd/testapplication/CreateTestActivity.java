package ikpmd.ikpmd.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.Step;
import ikpmd.ikpmd.testapplication.models.TestData;
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

