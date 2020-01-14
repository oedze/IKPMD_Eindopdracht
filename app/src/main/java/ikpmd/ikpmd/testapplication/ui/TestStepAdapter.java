package ikpmd.ikpmd.testapplication.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ikpmd.ikpmd.testapplication.R;
import ikpmd.ikpmd.testapplication.models.Step;

public class TestStepAdapter extends RecyclerView.Adapter<TestStepAdapter.ViewHolder>{
    private List<Step> testStepList;
    private LayoutInflater inflater;
    private TestStepAdapter.StepChangedListener stepChangedListener;
    private TestStepAdapter.TestStepCloseButtonPressedListener closeButtonListener;


    public TestStepAdapter(Context context, List<Step> testDataList) {
        this.testStepList = testDataList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TestStepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_addstep, parent, false);
        return new TestStepAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestStepAdapter.ViewHolder holder, int position) {
        Step data = testStepList.get(position);
        holder.stepName.setText(data.getDetails());
        holder.stepExpectedResult.setText(data.getExpectedResult());
    }

    @Override
    public int getItemCount() {
        return testStepList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements TextWatcher, View.OnClickListener{
        EditText stepName;
        EditText stepExpectedResult;
        Button removeButton;

        ViewHolder(View itemView){
            super(itemView);
            stepName = itemView.findViewById(R.id.edittext_addstep_step);
            stepExpectedResult = itemView.findViewById(R.id.edittext_addstep_expected_result);
            removeButton = itemView.findViewById(R.id.button_addstep_remove);

            stepName.addTextChangedListener(this);
            stepExpectedResult.addTextChangedListener(this);
            removeButton.setOnClickListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(stepChangedListener != null){
                stepChangedListener.onTestStepListChange(new Step(stepName.getText().toString(),stepExpectedResult.getText().toString()), getAdapterPosition());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void onClick(View v) {
            if(closeButtonListener != null){
                closeButtonListener.onTestStepRemoveTest(getAdapterPosition());
            }

        }

    }

    public void setStepChangedListener(TestStepAdapter.StepChangedListener stepChangedListener) {
        this.stepChangedListener = stepChangedListener;
    }

    public void setCloseButtonListener(TestStepAdapter.TestStepCloseButtonPressedListener closeButtonListener) {
        this.closeButtonListener = closeButtonListener;
    }

    public interface StepChangedListener{
        void onTestStepListChange(Step newStep, int position);
    }

    public interface TestStepCloseButtonPressedListener{
        void onTestStepRemoveTest(int position);
    }
}
