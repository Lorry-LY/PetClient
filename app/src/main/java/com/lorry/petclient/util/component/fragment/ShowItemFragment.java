package com.lorry.petclient.util.component.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lorry.petclient.R;


public class ShowItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int i;

    public ShowItemFragment() {
        // Required empty public constructor
    }


    public static ShowItemFragment newInstance(Bundle args) {
        ShowItemFragment fragment = new ShowItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ShowItemFragment newInstance(int i) {
        ShowItemFragment fragment = new ShowItemFragment();
        Bundle args = new Bundle();
        args.putInt("key", i);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int key = getArguments().getInt("key");
            this.i = key;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_item, container, false);
        TextView support_number = view.findViewById(R.id.fragment_show_item_support_number);
        support_number.setText(String.format("%d", this.i));
        return view;
    }
}