package com.lorry.petclient.fragment.index;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {

    private ArrayList<android.app.Fragment> fragmentList;

    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ScrollView scrollView = view.findViewById(R.id.fragment_shop_scrollView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener((view1, i, i1, i2, i3) -> {
                if (view1.getScrollY() <= 0) {
                    clearPosts();
                    getPosts();
                } else if (view1.getScrollY() + view1.getHeight() - view1.getPaddingTop() - view1.getPaddingBottom() == ((ScrollView) view1).getChildAt(0).getHeight()) {
                    System.out.println("bottom");
                }
            });
        }
        getPosts();
        return view;
    }

    private void clearPosts() {
        LinearLayout view_left = getView().findViewById(R.id.fragment_shop_show);
        view_left.removeAllViews();
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
                        fragmentTransaction.add(R.id.fragment_shop_show, item);
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