package kg.geektech.taskapprestored.ui.gallery;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import kg.geektech.taskapprestored.R;


public class GalleryFragment extends Fragment {
    public StorageAdapter storageAdapter;
    public ArrayList<File> galleryList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        return  root;
    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id._gallery_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        storageAdapter = new StorageAdapter(galleryList);
        recyclerView.setAdapter(storageAdapter);
        getPermissions();

    }
    private void getPermissions (){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            getFiles();
            Log.e("ololo", "Checking if there is a permission, if there is we get the files");
        }else {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
            Log.e("ololo", "requesting permission");
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 110) {
            getPermissions();
        }
    }
    private void getFiles() {
        File folder = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera");
        Log.e("ololo",  "creating File class and getting path to storage of mobile");
        //if (!folder.exists()) folder.mkdir();
        for (File file : folder.listFiles()) {
            galleryList.add(file);
            storageAdapter.notifyDataSetChanged();
            Log.e("ololo", "here will show all files " + file.getAbsolutePath());
        }
    }
}