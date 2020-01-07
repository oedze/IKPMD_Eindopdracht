package ikpmd.ikpmd.testapplication.ui;

import android.content.Context;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ikpmd.ikpmd.testapplication.R;

public class TestPreConditionAdapter extends RecyclerView.Adapter<TestPreConditionAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflator;
    private ButtonPressedEventListener buttonPressedListener;
    private TextChangedListener textChangedListener;
    public TestPreConditionAdapter(Context context, List<String> data) {
        this.mInflator = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public TestPreConditionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String test = mData.get(position);
        holder.myTextView.setText(test);
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements TextWatcher, View.OnClickListener {
        EditText myTextView;
        Button closeButton;
        ViewHolder(View itemView){
            super(itemView);
            myTextView = itemView.findViewById(R.id.pre_text);
            closeButton = itemView.findViewById(R.id.button_remove_pre);
            itemView.setOnClickListener(this);

            myTextView.addTextChangedListener(this);
            closeButton.setOnClickListener(this);

        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(textChangedListener != null){
                try{
                    textChangedListener.onTextChanged(s.toString(), getAdapterPosition());
                } catch (Exception e) {

                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void onClick(View v) {
            if(buttonPressedListener != null){
                buttonPressedListener.onButtonPressed(getAdapterPosition());
            }
        }
    }

    public String getItem(int id){
        return mData.get(id);
    }


    public void setTextChangedListener(TextChangedListener tc){
        this.textChangedListener = tc;
    }

    public void setButtonPressedListener(ButtonPressedEventListener bpel){
        this.buttonPressedListener = bpel;
    }



    public interface TextChangedListener{
        void onTextChanged(String newText, int position);
    }

    public interface ButtonPressedEventListener{
        void onButtonPressed(int position);
    }
}
