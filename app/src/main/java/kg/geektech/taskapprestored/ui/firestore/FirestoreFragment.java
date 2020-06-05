package kg.geektech.taskapprestored.ui.firestore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import kg.geektech.taskapprestored.R;
import kg.geektech.taskapprestored.models.Task;
import kg.geektech.taskapprestored.ui.OnItemClickListener;
import kg.geektech.taskapprestored.ui.home.TaskAdapter;


public class FirestoreFragment extends Fragment {
    TaskAdapter fireStoreAdapter;
    ArrayList<Task>list=new ArrayList<>();

    public FirestoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_firestore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView= view.findViewById(R.id.recycler_view_firestore);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fireStoreAdapter = new TaskAdapter(list);
        recyclerView.setAdapter(fireStoreAdapter);
        getData();
        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback simpleCallback =new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position= viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    list.remove(position);
                    fireStoreAdapter.notifyItemRemoved(position);
                    Toast.makeText(getActivity(), "Item will be deleted forever", Toast.LENGTH_SHORT).show();
                    break;
                case ItemTouchHelper.RIGHT:
                    list.remove(position);
                    fireStoreAdapter.notifyItemRemoved(position);
                    Toast.makeText(getActivity(), "Item will be deleted", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    private void getData() {
        FirebaseFirestore.getInstance().collection("tasks")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snap) {
                        list.addAll(snap.toObjects(Task.class));
                        fireStoreAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ololo", "getTasks " + e.getMessage());

                    }
                });
    }
}




