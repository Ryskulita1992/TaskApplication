package kg.geektech.taskapprestored;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import kg.geektech.taskapprestored.App;
import kg.geektech.taskapprestored.R;
import kg.geektech.taskapprestored.models.Task;

public class FormActivity extends AppCompatActivity  {

    private EditText editTitle;
    private EditText editDesc;
    private Task task;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if (getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Новая Задача");
        }
        editTitle = findViewById(R.id.edit_title);
        editDesc = findViewById(R.id.edit_description);
        task = (Task) getIntent().getSerializableExtra("task");
        if (task !=null){
            editTitle.setText(task.getTitle());
            editDesc.setText(task.getDesc());
        }

    }
    public void save (View view) {
        String title = editTitle.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        if (title.isEmpty()){
            editTitle.setError("");
            return;
        }
        else if (task !=null){
            task.setTitle(title);
            task.setDesc(desc);
            App.getInstance().getDatabase().taskDao().update(task);
            Log.e("ololo", "editing  task and updating");
        }else {
            task = new Task();
            task.setTitle(title);
            task.setDesc(desc);
            App.getInstance().getDatabase().taskDao().insert(task);
            Log.e("ololo", "adding new task and updating");

        }

//        String title2 = editTitle.getText().toString().trim();
//        String desc2 = editDesc.getText().toString().trim();
//        Map<String, Object> map = new HashMap<>();   //PV…
//        map.put("title", title2);
//        map.put("desc", desc2);

        FirebaseFirestore.getInstance().collection("tasks")
                .add(task)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FormActivity.this, "Successful", Toast.LENGTH_SHORT);
                        } else {
                            Toast.makeText(FormActivity.this, "Failed", Toast.LENGTH_SHORT);
                        }
                    }
                });
        finish();
    }
}