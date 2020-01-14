package ikpmd.ikpmd.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ikpmd.ikpmd.testapplication.models.TestData;
import ikpmd.ikpmd.testapplication.ui.TestDataAdaptor;
import ikpmd.ikpmd.testapplication.ui.TestPreConditionAdapter;

public class CreateTestActivity extends AppCompatActivity implements TestPreConditionAdapter.TextChangedListener, TestPreConditionAdapter.ButtonPressedEventListener, TestDataAdaptor.DataChangedListener, TestDataAdaptor.TestDataCloseButtonPressedListener {

    RecyclerView recyclerView;
    RecyclerView testDataRecyclerView;
    TestPreConditionAdapter adaptor;
    TestDataAdaptor testDataAdaper;
    List<String> preConditionList = new ArrayList();
    List<TestData> testDataList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        preConditionList.add("Gebruiker is ingelogd");
        preConditionList.add("Gebruiker is op admin scherm");

        testDataList.add(new TestData("username", "oetze"));
        testDataList.add(new TestData("password", "12345678"));

        recyclerView = findViewById(R.id.rv_create_test_pre_conditions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        testDataRecyclerView = findViewById(R.id.rv_create_test_data);
        testDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        adaptor = new TestPreConditionAdapter(this, preConditionList);
        testDataAdaper = new TestDataAdaptor(this, testDataList);

        adaptor.setTextChangedListener(this);
        adaptor.setButtonPressedListener(this);

        testDataAdaper.setCloseButtonListener(this);
        testDataAdaper.setDataChangedListener(this);

        recyclerView.setAdapter(adaptor);
        testDataRecyclerView.setAdapter(testDataAdaper);





        Button addDataButton =  findViewById(R.id.button_create_test_add_data);


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
                testDataAdaper.notifyDataSetChanged();
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
        testDataAdaper.notifyDataSetChanged();
    }
}

