package kg.geektech.taskapprestored.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import kg.geektech.taskapprestored.ui.OnItemClickListener;
import kg.geektech.taskapprestored.R;

public class StorageAdapter extends RecyclerView.Adapter <StorageAdapter.ViewHolder> {
    public ArrayList<File> galleryList;
    private OnItemClickListener onItemClickListener;
    public StorageAdapter(ArrayList<File> galleryList) {
        this.galleryList = galleryList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gallery, parent, false);
        return new StorageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(galleryList.get(position));
    }

    @Override
    public int getItemCount() {

        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.gallery_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());

                }
            });
        }
        public void bind(File file) {
            image.setText(file.getName());

        }
    }
}











