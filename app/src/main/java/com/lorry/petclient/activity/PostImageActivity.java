package com.lorry.petclient.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lorry.petclient.R;
import com.lorry.petclient.util.data.DateTimeUtil;
import com.lorry.petclient.util.http.HttpMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostImageActivity extends AppCompatActivity {

    private List<ImageView> imageViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image);


        ViewPager viewPager = findViewById(R.id.activity_post_image_viewpager);
        RadioGroup radioGroup = findViewById(R.id.activity_post_image_radioGroup);

        imageViewList = new ArrayList<>();

        String ID = getIntent().getExtras().getBundle("data").getString("ID");
        List<String> imageList = new ArrayList<>();

        ImageButton btn_back = findViewById(R.id.activity_post_image_btn_back);
        btn_back.setOnClickListener(view -> finish());

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViewList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView1 = imageViewList.get(position);
                container.addView(imageView1);
                return imageView1;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(imageViewList.get(position));
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                radioGroup.check(radioGroup.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        HttpMapper.getPostInfo(ID, (res) -> {
            try {
                if (res.getInt("code") == 200) {
                    JSONObject data = res.getJSONObject("data");
                    String images = data.getString("images");
                    imageList.addAll(Arrays.asList(images.split(";")));
                    runOnUiThread(() -> {
                        for (String str : imageList) {
                            RadioButton radioButton = new RadioButton(this);
                            radioButton.setClickable(false);
                            radioButton.setButtonDrawable(null);
//                            radioButton.setText(" ");
                            radioButton.setWidth(30);
                            radioButton.setHeight(30);
                            radioButton.setBackgroundResource(R.drawable.post_image_radio_image);
                            radioGroup.addView(radioButton);
                        }
                        if (radioGroup.getChildCount() > 0) {
                            radioGroup.check(radioGroup.getChildAt(0).getId());
                        }
                    });
                    for (String img : imageList) {
                        HttpMapper.getPostImage(ID, img, (res1) -> {
                            try {
                                if (res1.getInt("code") == 200) {
                                    JSONObject data1 = res1.getJSONObject("data");
                                    String base64 = data1.getString("base64");
                                    runOnUiThread(() -> {
                                        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                        ImageView imageView = new ImageView(this);
                                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                        imageView.setAdjustViewBounds(true);
                                        imageView.setImageBitmap(decodedByte);
                                        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                        System.gc();
                                        imageViewList.add(imageView);
                                        viewPager.getAdapter().notifyDataSetChanged();
                                        viewPager.setOffscreenPageLimit(imageList.size() / 2);
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    String author_id = data.getString("author_id");
                    HttpMapper.getUserInfo(author_id, (res1) -> {
                        try {
                            if (res1.getInt("code") == 200) {
                                JSONObject data1 = res1.getJSONObject("data");
                                String name = data1.getString("name");
                                runOnUiThread(() -> {
                                    TextView text_name = findViewById(R.id.activity_post_image_text_author);
                                    text_name.setText(name);
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                    HttpMapper.getUserAvatar(author_id, (res1) -> {
                        try {
                            if (res1.getInt("code") == 200) {
                                JSONObject data1 = res1.getJSONObject("data");
                                String base64 = data1.getString("base64");
                                runOnUiThread(() -> {
                                    ImageFilterView image = findViewById(R.id.activity_post_image_image_author);
                                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    image.setImageBitmap(decodedByte);
                                    ImageFilterView temp_view = findViewById(R.id.activity_post_image_image_author_comments);
                                    temp_view.setImageBitmap(decodedByte);
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });

                    String title = data.getString("title");
                    String text = data.getString("text");
                    String tags = data.getString("tags");
                    String place = data.getString("place");
                    String create_time = data.getString("create_time");
                    String create_date = data.getString("create_date");
                    String remove_time = data.getString("remove_time");
                    String remove_date = data.getString("remove_date");

                    List<String> tagList = Arrays.asList(tags.split(";"));

                    String date_time = DateTimeUtil.format(create_date, create_time);

                    String date_place = date_time + " " + place;

                    runOnUiThread(() -> {
                        TextView text_title = findViewById(R.id.activity_post_image_text_title);
                        text_title.setText(title);
                        TextView text_text = findViewById(R.id.activity_post_image_text_text);
                        text_text.setText(text);
                        String tagText = "";
                        for (String tag : tagList) {
                            tagText += "#" + tag + "#";
                        }
                        TextView text_tags = findViewById(R.id.activity_post_image_text_tags);
                        text_tags.setText(tagText);
                        TextView text_time = findViewById(R.id.activity_post_image_text_time);
                        text_time.setText(date_place);
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


    }
}