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
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WadahFragment extends Fragment {


    private SmartTabLayout viewpagertab;
    private ViewPager viewpager;
    Handler handler = new Handler();


    public static WadahFragment newInstance() {
        return new WadahFragment();
    }

    public WadahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wadah, container, false);
        initView(view);
        this.handler = new Handler();
        this.handler.postDelayed(this.runnable, 1000);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
//        viewPager.setAdapter(adapter);
        setupViewPager(viewPager);

        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

        return view;
    }

    private void initView(View view) {
        viewpagertab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new NewsSatuFragment(), "Semua");
        adapter.addFragment(new NewsDuaFragment("10"), "Kantor Wilayah");
        adapter.addFragment(new NewsDuaFragment("11"), "Area Banjarmasin");
        adapter.addFragment(new NewsDuaFragment("12"), "Area Barabai");
        adapter.addFragment(new NewsDuaFragment("13"),"Area Kotabaru");
        adapter.addFragment(new NewsDuaFragment("14"), "Area Palangka Raya");
        adapter.addFragment(new NewsDuaFragment("15"), "Area Kuala Kapuas");
        adapter.addFragment(new NewsDuaFragment("16"),"APD Kalselteng");
        adapter.addFragment(new NewsDuaFragment("17"),"UPPK Kalsel");
        adapter.addFragment(new NewsDuaFragment("18"), "UPPK Kalteng");
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
            ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
//        viewPager.setAdapter(adapter);
            setupViewPager(viewPager);

            SmartTabLayout viewPagerTab = (SmartTabLayout) getActivity().findViewById(R.id.viewpagertab);
            viewPagerTab.setViewPager(viewPager);
//            getView().handler.postDelayed(runnable, 1000);
        }
    };
}
