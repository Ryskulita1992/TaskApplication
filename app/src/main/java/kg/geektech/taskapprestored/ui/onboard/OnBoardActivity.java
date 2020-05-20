package kg.geektech.taskapprestored.ui.onboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import kg.geektech.taskapprestored.R;

public class OnBoardActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        viewPager=findViewById(R.id.view_pager);
        viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        tablayout=findViewById(R.id.tabLayout);
        tablayout.setupWithViewPager(viewPager, true);

    }
    public void skipButton(View view) {
        viewPager.setCurrentItem(2);
        Log.e("ololo", "skip should open the last page of board");
    }

    private void init() {
        ArrayList<Fragment> fragments=new ArrayList<>();
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public
        Fragment getItem(int position) {
            Bundle bundle=new Bundle();
            bundle.putInt("pos", position);
            BoardFragment fragment= new BoardFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }



}