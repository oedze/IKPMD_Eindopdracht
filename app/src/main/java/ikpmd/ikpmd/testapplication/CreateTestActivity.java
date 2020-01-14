package ikpmd.ikpmd.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ikpmd.ikpmd.testapplication.ui.TestPreConditionAdapter;

public class CreateTestActivity extends AppCompatActivity implements TestPreConditionAdapter.TextChangedListener, TestPreConditionAdapter.ButtonPressedEventListener {

    RecyclerView recyclerView;
    TestPreConditionAdapter adaptor;
    List<String> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        list.add("Gebruiker is ingelogd");
        list.add("Gebruiker is op admin scherm");

        recyclerView = findViewById(R.id.rvPreConditions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        adaptor = new TestPreConditionAdapter(this, list);


        adaptor.setTextChangedListener(this);
        adaptor.setButtonPressedListener(this);


        recyclerView.setAdapter(adaptor);

        //LayoutInflater.from(this).inflate(R.layout.activity_create_test, null, false);

        Button addPreButton = (Button)  findViewById(R.id.button_add_pre);
        addPreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                list.add("");
                adaptor.notifyDataSetChanged();
                v.computeScroll();
            }
        });

    }



    @Override
    public void onTextChanged(String newText, int position) {
          list.set(position, newText);
    }

    @Override
    public void onButtonPressed(int position) {
        list.remove(position);
        adaptor.notifyDataSetChanged();
    }
}

