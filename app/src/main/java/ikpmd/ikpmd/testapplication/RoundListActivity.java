package ikpmd.ikpmd.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.Round;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.services.ProjectService;
import ikpmd.ikpmd.testapplication.services.RoundService;

import static ikpmd.ikpmd.testapplication.R.layout.activity_round_list;
import static ikpmd.ikpmd.testapplication.R.layout.activity_round_list_item;
import static ikpmd.ikpmd.testapplication.R.layout.activity_test_list;

public class RoundListActivity extends AppCompatActivity{

    LinearLayout roundListView;
    ArrayAdapter<Round> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_list);

        final List<Round> list = new ArrayList<>();

        roundListView = (LinearLayout)findViewById(R.id.round_list_list);
        //adapter = new ArrayAdapter<Round>(getBaseContext(), activity_round_list_item, list);
        //roundListView.setAdapter(adapter);




        final Activity _this = this;

        RoundService.getRoundsOfProject(ProjectService.activeProjecId, new OnSuccessListener<List<Round>>() {
            @Override
            public void onSuccess(List<Round> rounds) {
                list.addAll(rounds);
                //adapter.notifyDataSetChanged();

                for(final Round round : rounds){
                    TextView ts = new TextView(_this);
                    ts.setText(round.getDate() + " -> " + round.getTester());
                    ts.setPadding(10, 10, 10, 10);
                    ts.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RoundService.activeRoundId = round.getId();
                            Intent intent = new Intent(_this, TestRoundResultActivity.class);
                            startActivity(intent);
                        }
                    });
                    roundListView.addView(ts);
                }

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
}
