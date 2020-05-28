package kg.geektech.taskapprestored.ui.onboard;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import kg.geektech.taskapprestored.MainActivity;
import kg.geektech.taskapprestored.R;
public class BoardFragment extends Fragment {

    public BoardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);
        return v;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textTitle = view.findViewById(R.id.view_pager_text_title);
        TextView textTitleBottom = view.findViewById(R.id.text_title_bottom);
        ImageView imageView = view.findViewById(R.id.image_view);
        Button buttonGetStarted = view.findViewById(R.id.getStarted);
        final LottieAnimationView animationView = view.findViewById(R.id.animation);

        int pos = getArguments().getInt("pos");
        switch (pos) {
            case 0:
                animationView.setAnimation(R.raw.greeting);
                //imageView.setImageResource(R.drawable.netflix);
                textTitle.setText("Trying to join Netflix?");
                textTitleBottom.setText("You can not sign up for Netflix in the app. We know its hassle. After you`re a member, you can start watching in the app");
                buttonGetStarted.setVisibility(View.INVISIBLE);
                break;
            case 1:
                animationView.setAnimation(R.raw.balance);
                imageView.setImageResource(R.drawable.devices);
                textTitle.setText("Watch on any device");
                textTitleBottom.setText("Stream on your phone, tablet, laptop, and TV without paying more.");
                buttonGetStarted.setVisibility(View.INVISIBLE);
                break;
            case 2:
                animationView.setAnimation(R.raw.stayhome);
                imageView.setImageResource(R.drawable.downloadandgo);
                textTitle.setText("Download and go");
                textTitleBottom.setText("Save your data, watch offline on a plane, train, or submarine...");
                break;
        }
        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIsShown();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
                Log.d("ololo", "opening the Main Activity");

            }
        });

    }
    private void saveIsShown() {
        SharedPreferences preferences = getActivity().getSharedPreferences("storageFile", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("isShown", true).apply();

    }


}