package kg.geektech.taskapprestored;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

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
       finish();
    }


}