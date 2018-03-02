package com.powerge.wise.powerge.otherPages;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityJingSaiBinding;

public class JingSaiActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, JingSaiActivity.class);
        context.startActivity(starter);
    }

    ActivityJingSaiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jing_sai);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[7]);
    }
}
