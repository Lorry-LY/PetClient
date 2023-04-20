package com.lorry.petclient.fragment.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lorry.petclient.R;
import com.lorry.petclient.util.component.fragment.ShowItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndexFragment extends androidx.fragment.app.Fragment {

    List<Fragment> fragmentList;

    public IndexFragment() {
        // Required empty public constructor
    }

    public static IndexFragment newInstance(Bundle args) {
        IndexFragment fragment = new IndexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        fragmentList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            fragmentList.add(ShowItemFragment.newInstance(i));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);

        Boolean is_left = true;
        for (Fragment item : fragmentList) {
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(is_left ? R.id.fragment_index_show_left : R.id.fragment_index_show_right, item);
            fragmentTransaction.commit();
            is_left = !is_left;
        }
        Log.d("my", String.format("%d", fragmentList.size()));
        return view;
    }
}