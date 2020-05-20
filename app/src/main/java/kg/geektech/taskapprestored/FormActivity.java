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

public class FormActivity extends AppCompatActivity {


    private EditText editTitle;
    private EditText editDesc;
    private Task task;
    Button buttonChange;
    Button save;
    private int id;

    public void setId(int id) {
        this.id = id;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Новая Задача");
        }
        editTitle = findViewById(R.id.edit_title);
        editDesc = findViewById(R.id.edit_description);
        save = findViewById(R.id.save);
        buttonChange = findViewById(R.id.change);
        if (getIntent().getSerializableExtra("ss") != null) {
            task = (Task) getIntent().getSerializableExtra("ss");
            editTitle.setText(task.getTitle());
            editDesc.setText(task.getDesc());
            save.setVisibility(View.GONE);
            buttonChange.setVisibility(View.VISIBLE);

            buttonChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // saveIsShown();
                    if (getIntent().getSerializableExtra("sss") != null) {
                        Intent intent = getIntent();
                        Integer posit = intent.getIntExtra("sss", 1);
                        App.getInstance().getDatabase().taskDao().update(posit, editTitle.getText().toString(), editDesc.getText().toString());
                        Log.d("ololo", "editing " + posit.toString());
                        finish();
                    }
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    save.setVisibility(View.INVISIBLE);
                    String title = editTitle.getText().toString().trim();
                    String desc = editDesc.getText().toString().trim();
                    Task task = new Task(title, desc);
                    App.getInstance().getDatabase().taskDao().insert(task);
                    finish();
                    Log.d("ololo", "saving  " );

                }
            });




            class Pos implements Serializable {
                int position;

                public int getPosition() {
                    return position;
                }
            }

        }
    }
//    public void saveIsShown (){
//        SharedPreferences preferences = this.getSharedPreferences("storageFile", Context.MODE_PRIVATE);
//        preferences.edit().putBoolean("isShownButChange", true).apply();
//    }
//    private  boolean isShownButton (){
//        SharedPreferences preferences =getSharedPreferences("storageFile", Context.MODE_PRIVATE);
//        return preferences.getBoolean("isShownButChange",true);
 //   };
}