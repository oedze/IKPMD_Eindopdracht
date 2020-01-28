package ikpmd.ikpmd.testapplication.services;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import ikpmd.ikpmd.testapplication.models.StepResult;
import ikpmd.ikpmd.testapplication.models.TestResult;

public class ExcelService {


    public static void export(Context context, String filename, List<TestResult> list,  OnSuccessListener<Uri> sl){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File sd = context.getExternalFilesDir("");
        String csvFile = filename;
        File directory = new File(sd.getAbsolutePath());

        if(!directory.isDirectory()){
            directory.mkdirs();
        }



        try{
            File file = new File(directory, filename);
            FileWriter writer = new FileWriter(file);

            writer.append("SEP=,\n");
            writer.append("result_id");
            writer.append(',');
            writer.append("testname");
            writer.append(',');
            writer.append("stepname");
            writer.append(',');
            writer.append("stepresult");
            writer.append(',');
            writer.append('\n');

            for(TestResult rs : list){
                writer.append(rs.getId() + ',');
                writer.append("<testname>, , \n");
                for(StepResult sr: rs.getStepResults()){
                    writer.append(" , ,");
                    writer.append("<stepname>," +  sr.getActualResult() +"\n");
                }
            }

            writer.flush();
            writer.close();

            Uri uri = Uri.fromFile(file);
            sl.onSuccess(uri);

            } catch (IOException e) {
                e.printStackTrace();
        }
    }





}
