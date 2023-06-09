package com.lorry.petclient.fragment.my;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lorry.petclient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SupportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupportFragment extends androidx.fragment.app.Fragment {

    List<Fragment> fragmentList;

    public SupportFragment() {
        // Required empty public constructor
    }

    public static SupportFragment newInstance(Bundle args) {
        SupportFragment fragment = new SupportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        fragmentList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        Boolean is_left = true;
        for (Fragment item : fragmentList) {
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(is_left ? R.id.fragment_support_show_left : R.id.fragment_support_show_right, item);
            fragmentTransaction.commit();
            is_left = !is_left;
        }

        return view;
    }
}