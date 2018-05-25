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
public class KategoriSatuFragment extends Fragment {


    private FrameLayout content;
    private BottomNavigationView navigation;

    public KategoriSatuFragment() {
        // Required empty public constructor
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = WadahFragment.newInstance();
                    break;
                case R.id.navigation_dashboard:
                    fragment = VideoSatuFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.commit();
            return true;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kategori_satu, container, false);
        initView(view);

        navigation.setOnNavigationItemSelectedListener(this.mOnNavigationItemSelectedListener);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, WadahFragment.newInstance());
        fragmentTransaction.commit();
        return view;
    }

    private void initView(View view) {
        content = (FrameLayout) view.findViewById(R.id.content);
        navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
    }
}
