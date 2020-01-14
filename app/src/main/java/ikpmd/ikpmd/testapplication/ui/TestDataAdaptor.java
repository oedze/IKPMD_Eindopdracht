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
import ikpmd.ikpmd.testapplication.models.TestData;

public class TestDataAdaptor extends RecyclerView.Adapter<TestDataAdaptor.ViewHolder>{

    private List<TestData> testDataList;
    private LayoutInflater inflater;
    private DataChangedListener dataChangedListener;
    private TestDataCloseButtonPressedListener closeButtonListener;


    public TestDataAdaptor(Context context, List<TestData> testDataList) {
        this.testDataList = testDataList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_adddata, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestData data = testDataList.get(position);
        holder.keyText.setText(data.getKey());
        holder.valueText.setText(data.getValue());
    }

    @Override
    public int getItemCount() {
        return testDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements TextWatcher, View.OnClickListener{
        EditText keyText;
        EditText valueText;
        Button removeButton;

        ViewHolder(View itemView){
            super(itemView);
            keyText = itemView.findViewById(R.id.edittext_testdata_key);
            valueText = itemView.findViewById(R.id.edittext_testdata_value);
            removeButton = itemView.findViewById(R.id.button_testdata_remove);

            keyText.addTextChangedListener(this);
            valueText.addTextChangedListener(this);
            removeButton.setOnClickListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(dataChangedListener != null){
                dataChangedListener.onTestDataListChange(new TestData(keyText.getText().toString(),valueText.getText().toString()), getAdapterPosition());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void onClick(View v) {
            if(closeButtonListener != null){
                closeButtonListener.onTestDataRemoveTest(getAdapterPosition());
            }

        }

    }

    public void setDataChangedListener(DataChangedListener dataChangedListener) {
        this.dataChangedListener = dataChangedListener;
    }

    public void setCloseButtonListener(TestDataCloseButtonPressedListener closeButtonListener) {
        this.closeButtonListener = closeButtonListener;
    }

    public interface DataChangedListener{
        void onTestDataListChange(TestData  newData, int position);
    }

    public interface TestDataCloseButtonPressedListener{
        void onTestDataRemoveTest(int position);
    }
}
