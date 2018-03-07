package com.powerge.wise.powerge.otherPages.JingSai;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityJingSaiBinding;

import com.powerge.wise.basestone.heart.network.Notification;
import com.powerge.wise.powerge.databinding.ItemJingSaiPopBinding;
import com.powerge.wise.powerge.databinding.JingSaiPopListBinding;

import rx.Subscription;
import rx.functions.Action1;

public class JingSaiActivity extends AppCompatActivity {
    public Subscription notification;

    public static void start(Context context) {
        Intent starter = new Intent(context, JingSaiActivity.class);
        context.startActivity(starter);
    }

    ActivityJingSaiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notification = Notification.register(action1);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jing_sai);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[7]);
        binding.jingSaiMainPage.setAdapter(new JingSaiFragmentAdapter(getSupportFragmentManager()));
        binding.jingSaiTabL.setupWithViewPager(binding.jingSaiMainPage);

    }


    private String[] datas = {"指标1", "指标2", "指标3", "指标4", "指标5"};

    private void showPop() {
        JingSaiPopListBinding popBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.jing_sai_pop_list, null, false);
        popBinding.jingSaiPopList.setAdapter(new ArrayAdapter<String>(this, R.layout.item_jing_sai_pop, R.id.item_text, datas));

        PopupWindow window = new PopupWindow(popBinding.getRoot(), LinearLayout.LayoutParams.MATCH_PARENT, 900);
        // TODO: 2016/5/17 设置动画
//        window.setAnimationStyle(R.style.popup_window_anim);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        window.setFocusable(true);
        window.setOutsideTouchable(false);
        window.update();
        window.showAtLocation(popBinding.getRoot(), Gravity.BOTTOM, 0, 0);
    }


    Action1<Notification> action1 = new Action1<Notification>() {
        @Override
        public void call(Notification notification) {
            if (notification.getCode() == 001) {
                showPop();
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        notification.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notification.unsubscribe();
    }
}
