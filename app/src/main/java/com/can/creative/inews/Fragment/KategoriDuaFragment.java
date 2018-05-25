package com.can.creative.inews.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.can.creative.inews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KategoriDuaFragment extends Fragment {


    private FrameLayout content2;
    private BottomNavigationView navigation;

    public KategoriDuaFragment() {
        // Required empty public constructor
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home2:
//                    fragment = NewsDuaFragment.newInstance();
                    break;
                case R.id.navigation_dashboard2:
//                    fragment = VideoDuaFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction = KategoriDuaFragment.this.getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content2, fragment);
            transaction.commit();
            return true;
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kategori_dua, container, false);
        initView(view);

//        navigation.setOnNavigationItemSelectedListener(this.mOnNavigationItemSelectedListener);
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.content2, NewsDuaFragment.newInstance());
//        fragmentTransaction.commit();
        return view;
    }

    private void initView(View view) {
        content2 = (FrameLayout) view.findViewById(R.id.content2);
        navigation = (BottomNavigationView) view.findViewById(R.id.navigation2);
    }
}
