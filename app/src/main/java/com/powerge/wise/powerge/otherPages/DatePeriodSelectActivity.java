package com.powerge.wise.powerge.otherPages;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityDatePeriodSelectBinding;
import com.wisesignsoft.OperationManagement.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePeriodSelectActivity extends AppCompatActivity {
    public static int REQUEST_CODE_F_DATE_PER = 8008;
    public static String INTENT_STARTDATE = "startDate";
    public static String INTENT_ENDDATE = "endDate";

    public static void start(Activity context) {
        Intent starter = new Intent(context, DatePeriodSelectActivity.class);
        context.startActivityForResult(starter, REQUEST_CODE_F_DATE_PER);
    }

    ActivityDatePeriodSelectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_period_select);
        initView();
    }

    private void initView() {
        binding.textStartD.setChecked(true);
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        binding.textStartD.setText(format.format(calendar.getTime()));
        binding.datePickerView.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);

        binding.datePickerView.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (binding.textStartD.isChecked()) {
                    calendar.set(year, monthOfYear, dayOfMonth);
                    binding.textStartD.setText(format.format(calendar.getTime()));
                } else if (binding.textEndD.isChecked()) {
                    calendar.set(year, monthOfYear, dayOfMonth);
                    binding.textEndD.setText(format.format(calendar.getTime()));
                }
            }
        });

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_start_d:
                binding.textStartD.setChecked(true);
                break;
            case R.id.text_end_d:
                binding.textEndD.setChecked(true);
                break;
            case R.id.btn_done:
                checkDate();
                break;
            case R.id.btn_back:
                finish();
                break;
        }

    }

    /**
     * 检查日期
     */
    private void checkDate() {
        if (binding.textStartD.getText().toString().isEmpty() || binding.textEndD.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "请选择时间", Toast.LENGTH_SHORT).show();
            if (binding.textStartD.isChecked()) {
                binding.textEndD.setChecked(true);
            } else {
                binding.textStartD.setChecked(true);
            }

            return;
        }
        int start_d = Integer.parseInt(binding.textStartD.getText().toString().trim().replace("-", "").trim());
        int end_d = Integer.parseInt(binding.textEndD.getText().toString().trim().replace("-", "").trim());


        if (start_d > end_d) {
            Toast.makeText(getBaseContext(), "起始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
            return;
        } else if (Math.abs(end_d - start_d) > 7) {
            Toast.makeText(getBaseContext(), "日期区间不能超出7天", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra(INTENT_STARTDATE, binding.textStartD.getText());
            intent.putExtra(INTENT_ENDDATE, binding.textEndD.getText());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
