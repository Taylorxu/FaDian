package com.powerge.wise.powerge.operationProjo.net.view.mview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerge.wise.powerge.R;


/**
 * Created by ycs on 2016/11/24.
 */

public class SeachView extends RelativeLayout implements View.OnClickListener, TextView.OnEditorActionListener {

    private RelativeLayout rl_search;
    private LinearLayout ll_search;
    private EditText et_search;
    private TextView tv_cancel;
    private RelativeLayout ll_search_view_all;
    private ISearchView iSearchView;
    private TextView tv_test;

    public SeachView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.seach_view, this, true);
        ll_search_view_all = view.findViewById(R.id.ll_search_view_all);
        rl_search = view.findViewById(R.id.rl_search_view);
        ll_search = view.findViewById(R.id.ll_search_view);
        et_search = view.findViewById(R.id.et_search_view);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_test = view.findViewById(R.id.tv_test);

        rl_search.setVisibility(VISIBLE);
        ll_search.setVisibility(GONE);
        rl_search.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        et_search.setOnEditorActionListener(this);
        setStatic();

    }

    /**
     * 设置hint
     * @param string
     */
    public void setHint(String string){
        tv_test.setText(string);
    }

    private void setStatic() {
        ll_search_view_all.setBackgroundResource(R.color.c2);
        rl_search.setVisibility(VISIBLE);
        ll_search.setVisibility(GONE);
    }

    private void setDynamic() {
        ll_search_view_all.setBackgroundResource(R.color.c2);
        rl_search.startAnimation(getAnimationIn());
        rl_search.setVisibility(GONE);
        ll_search.setVisibility(VISIBLE);
        et_search.setText("");
        et_search.setFocusable(true);
        et_search.requestFocus();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_cancel) {
            setStatic();
            iSearchView.setOnCancelListener();

        } else if (i == R.id.rl_search_view) {
            setDynamic();

        }
    }

    public void setISearchViewListener(ISearchView iSearchViewListener){
        iSearchView = iSearchViewListener;
    }
    public interface ISearchView{
        void setOnSearchListener(String key);
        void setOnCancelListener();
    }

    private Animation getAnimationIn() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.search_in);
        return animation;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_SEARCH)) {
            String str = et_search.getText().toString().trim();
            if (!TextUtils.isEmpty(str)) {
                iSearchView.setOnSearchListener(str);
                return true;
            }
        }
        return false;
    }
}
