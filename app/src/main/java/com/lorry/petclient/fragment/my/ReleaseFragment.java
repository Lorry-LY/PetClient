package com.lorry.petclient.fragment.my;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.lorry.petclient.R;
import com.lorry.petclient.util.component.fragment.ShowItemFragment;
import com.lorry.petclient.util.http.HttpMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReleaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReleaseFragment extends Fragment {

    List<android.app.Fragment> fragmentList;

    public ReleaseFragment() {
        // Required empty public constructor
    }

    public static ReleaseFragment newInstance(Bundle args) {
        ReleaseFragment fragment = new ReleaseFragment();
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
        View view = inflater.inflate(R.layout.fragment_release, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getPosts();
    }

    private List<JSONObject> getPosts() {
        List<JSONObject> jsonObjects = new ArrayList<>();
        HttpMapper.getPostInfo(null, (res) -> {
            try {
                if (res.getInt("code") == 200) {
                    JSONArray data = res.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        jsonObjects.add((JSONObject) data.get(i));
                    }
                    fragmentList = new ArrayList<>();
                    for (JSONObject post : jsonObjects) {
                        fragmentList.add(ShowItemFragment.newInstance(post));
                    }
                    boolean is_left = true;
                    for (android.app.Fragment item : fragmentList) {
                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(is_left ? R.id.fragment_release_show_left : R.id.fragment_release_show_right, item);
                        fragmentTransaction.commit();
                        is_left = !is_left;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return jsonObjects;
    }
}