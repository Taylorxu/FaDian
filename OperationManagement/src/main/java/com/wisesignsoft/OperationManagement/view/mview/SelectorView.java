package com.wisesignsoft.OperationManagement.view.mview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.databinding.SelectorViewBinding;

/**
 * Created by ycs on 2016/11/17.
 */

public class SelectorView extends RelativeLayout {

    private ImageView imageView;
    private TextView textView;
    private boolean isSelect;

    public SelectorView(Context context) {
        super(context);
        init(context);
    }

    public SelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    SelectorViewBinding viewBinding;
    private void init(Context context) {
//        View view = LayoutInflater.from(context).inflate(R.layout.selector_view, this, true);
//        imageView = (ImageView) view.findViewById(R.id.iv_selector_view);
//        textView = (TextView) view.findViewById(R.id.tv_selector_view);
        viewBinding= DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.selector_view,this,true);
    }

    public void setInit(int reId, String text) {
        if (reId != -1) {
//            imageView.setImageResource(reId);
            viewBinding.ivSelectorView.setImageResource(reId);
        }
        if (!TextUtils.isEmpty(text)) {
//            textView.setText(text);
            viewBinding.tvSelectorView.setText(text);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
        if (isSelect) {
//            if (!textView.isSelected())
            viewBinding.tvSelectorView.setSelected(true);
//            if (!imageView.isSelected())
            viewBinding.ivSelectorView.setSelected(true);
        } else {
//            if (textView.isSelected())
            viewBinding.tvSelectorView.setSelected(false);
//            if (imageView.isSelected())
            viewBinding.ivSelectorView.setSelected(false);
        }
    }

    public boolean isSelect() {
        return isSelect;
    }
}
