package com.lorry.petclient.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.lorry.petclient.R;
import com.lorry.petclient.util.data.NumberUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatActivity extends AppCompatActivity {

    private LocalDateTime old_time_text;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ImageFilterButton btn_open_extra = findViewById(R.id.btn_open_extra);

        View layout_extra = findViewById(R.id.layout_extra);
        layout_extra.setVisibility(View.GONE);

        btn_open_extra.setOnClickListener(view -> {
            layout_extra.setVisibility(View.VISIBLE);
            layout_extra.setFocusable(true);
            layout_extra.setFocusableInTouchMode(true);
            layout_extra.requestFocus();
        });
        layout_extra.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                layout_extra.setVisibility(View.GONE);
            } else {
                layout_extra.setVisibility(View.VISIBLE);
            }
        });

        ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> {
            finish();
        });

        ImageFilterButton btn_send = findViewById(R.id.btn_send);

        old_time_text = LocalDateTime.of(2023, 5, 15, 14, 58);

        btn_send.setOnClickListener(view -> {
            EditText edit_message = findViewById(R.id.edit_message);
            String text = edit_message.getText().toString();
            if (text.equals("")) return;

            edit_message.setText("");

            LinearLayout layout_message = findViewById(R.id.layout_message);
            LinearLayout layout = new LinearLayout(this);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int dp20 = NumberUtil.dip2px(this, 20);
            int dp5 = NumberUtil.dip2px(this, 5);
            params.setMargins(dp20, dp5, dp20, dp5);
            layout.setGravity(Gravity.RIGHT);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(params);
            LocalDateTime new_time_text = LocalDateTime.now();
            Duration duration = Duration.between(new_time_text, old_time_text);
            if (duration.getSeconds() / 60 >= 5) {

                TextView time = new TextView(this);
                params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                time.setGravity(Gravity.CENTER);
                params.setMargins(0, dp20, 0, 0);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
                String timeStr = formatter.format(new_time_text);
                time.setText(timeStr);
                time.setLayoutParams(params);
                layout_message.addView(time);
                old_time_text = new_time_text;
            }

            TextView send_text = new TextView(this);
            params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int dp10 = NumberUtil.dip2px(this, 10);
            params.setMargins(dp10, 0, dp10, 0);
            send_text.setPadding(dp10, dp10, dp10, dp10);
            send_text.setBackgroundResource(R.drawable.radius_message);
            send_text.setText(text);
            send_text.setMaxWidth(NumberUtil.dip2px(this, 260));
            send_text.setLayoutParams(params);
            layout.addView(send_text);

            ImageFilterView author = new ImageFilterView(this);
            int dp45 = NumberUtil.dip2px(this, 45);
            params = new ViewGroup.MarginLayoutParams(dp45, dp45);
            author.setBackground(null);
            author.setImageResource(R.drawable.avatar4);
            author.setScaleType(ImageView.ScaleType.FIT_CENTER);
            author.setRound(NumberUtil.dip2px(this, 360));

            author.setLayoutParams(params);
            layout.addView(author);
            layout_message.addView(layout);

        });

    }
}