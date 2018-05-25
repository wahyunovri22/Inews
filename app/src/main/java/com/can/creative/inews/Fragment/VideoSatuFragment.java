package com.can.creative.inews.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.can.creative.inews.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoSatuFragment extends Fragment {

    private SmartTabLayout viewpagertab;
    private ViewPager viewpager;
    Handler handler = new Handler();
    private SmartTabLayout viewpagertab2;
    private ViewPager viewpager2;

    public static VideoSatuFragment newInstance() {
        return new VideoSatuFragment();
    }

    public VideoSatuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_satu, container, false);
        initView(view);
//
        this.handler = new Handler();
        this.handler.postDelayed(this.runnable, 1000);

        setupViewPager(viewpager2);
        viewpagertab2.setViewPager(viewpager2);

        return view;
    }

    private void initView(View view) {
        viewpagertab2 = view.findViewById(R.id.viewpagertab2);
        viewpager2 = view.findViewById(R.id.viewpager2);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new VideoDuaFragment(), "Semua");
        adapter.addFragment(new KetegoriVideoFragment("10"), "Kantor Wilayah");
        adapter.addFragment(new KetegoriVideoFragment("11"), "Area Banjarmasin");
        adapter.addFragment(new KetegoriVideoFragment("12"), "Area Barabai");
        adapter.addFragment(new KetegoriVideoFragment("13"), "Area Kotabaru");
        adapter.addFragment(new KetegoriVideoFragment("14"), "Area Palangka Raya");
        adapter.addFragment(new KetegoriVideoFragment("15"), "Area Kuala Kapuas");
        adapter.addFragment(new KetegoriVideoFragment("16"), "APD Kalselteng");
        adapter.addFragment(new KetegoriVideoFragment("17"), "UPPK Kalsel");
        adapter.addFragment(new KetegoriVideoFragment("18"), "UPPK Kalteng");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
//            return null;
        }
    }

    public final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setupViewPager(viewpager2);
            viewpagertab2.setViewPager(viewpager2);
        }
    };

}
