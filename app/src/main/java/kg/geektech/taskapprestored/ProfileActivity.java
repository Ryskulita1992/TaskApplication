package kg.geektech.taskapprestored;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.autofill.AutofillService;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import kg.geektech.taskapprestored.models.User;

public class ProfileActivity extends AppCompatActivity {
    private EditText editProfile;
    private ImageView profile_image;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editProfile=findViewById(R.id.edit_profile);
        profile_image=findViewById(R.id.image_profile);
        getData2();
    }

    public void save(View view) {
        String uid=FirebaseAuth.getInstance().getUid();
        String name=editProfile.getText().toString().trim();
        User user=new User(name, null, 50);
        Map< String, Object> map=new HashMap<>();
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
                //.add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(getParent(), "Successful", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getParent(), "Failed", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        });



    }
    public void getData(){
        String uid=FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                     if (task.isSuccessful()){
                         //User user=task.getResult().toObject(User.class); will get the user
                         String name=task.getResult().getString("name");// will get the specific part
                         editProfile.setText(name);
                     }
                    }
                });




    }
    public void getData2(){
        String uid=FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document("Ryskul")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()){
                            User user=documentSnapshot.toObject(User.class);
                            editProfile.setText(user.getName());
                        }
                    }
                });



    }

    public void choosePhoto(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                profile_image.setImageBitmap(bitmap);
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
