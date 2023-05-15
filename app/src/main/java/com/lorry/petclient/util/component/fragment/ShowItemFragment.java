package com.lorry.petclient.util.component.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.lorry.petclient.R;
import com.lorry.petclient.activity.PostImageActivity;
import com.lorry.petclient.activity.PostVideoActivity;
import com.lorry.petclient.util.data.NumberUtil;
import com.lorry.petclient.util.data.StringUtil;
import com.lorry.petclient.util.http.HttpMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Random;


public class ShowItemFragment extends Fragment {

    private float ColumnImageScalarHeight = (float) (1920F / 1080);
    private float RowImageScalarHeight = (float) (1080F / 1920);
    private float SizeImageScalarHeight = 1.0F;

    private Bundle data;

    public ShowItemFragment() {
        // Required empty public constructor
    }


    public static ShowItemFragment newInstance(Bundle args) {
        ShowItemFragment fragment = new ShowItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ShowItemFragment newInstance(JSONObject data) {
        ShowItemFragment fragment = new ShowItemFragment();
        try {
            Bundle args = new Bundle();
            args.putString("id", data.getString("id"));
            args.putString("author_id", data.getString("author_id"));
            args.putString("kind", data.getString("kind"));
            args.putString("title", data.getString("title"));
            args.putString("author_name", data.getString("author_name"));
            args.putString("header_image", data.getString("header_image"));
            fragment.setArguments(args);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.data = getArguments();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_item, container, false);

        return view;
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        ImageView title_image = view.findViewById(R.id.fragment_show_item_title_image);
        TextView title_text = view.findViewById(R.id.fragment_show_item_title_text);
        ImageFilterView author_image = view.findViewById(R.id.fragment_show_item_author_image);
        TextView author_name = view.findViewById(R.id.fragment_show_item_author_name);
        ImageFilterView video_icon = view.findViewById(R.id.imageFilterView_video_icon);
        video_icon.setVisibility(Objects.equals(data.getString("kind"), "1") ? View.VISIBLE : View.GONE);

        HttpMapper.getPostImage(data.getString("id"), data.getString("header_image"), (res) -> {
            try {
                if (res.getInt("code") == 200) {
                    JSONObject data = res.getJSONObject("data");
                    String base64 = data.getString("base64");
                    getActivity().runOnUiThread(() -> {
                        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        title_image.setImageBitmap(decodedByte);
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        HttpMapper.getUserAvatar(data.getString("author_id"), (res) -> {
            try {
                if (res.getInt("code") == 200) {
                    JSONObject data = res.getJSONObject("data");
                    String base64 = data.getString("base64");
                    getActivity().runOnUiThread(() -> {
                        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        author_image.setImageBitmap(decodedByte);
                        System.gc();
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        title_text.setText(data.getString("title"));

        String author_name1 = data.getString("author_name");
        author_name.setText(StringUtil.format(author_name1, 5));

        TextView support_number = view.findViewById(R.id.fragment_show_item_support_number);
        long l = new Random().nextInt(10000000);
        if (l < 0) l = l * -1;
        support_number.setText(String.format("%s", NumberUtil.format(l)));

        getView().setOnClickListener(view2 -> {
            Intent intent = new Intent(this.getActivity(),
                    Objects.equals(data.getString("kind"), "1") ? PostVideoActivity.class : PostImageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", data.getString("id"));
            intent.putExtra("data", bundle);
            startActivity(intent);
        });

    }
}