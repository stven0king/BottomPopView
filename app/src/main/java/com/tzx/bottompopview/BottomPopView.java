package com.tzx.bottompopview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Administrator
 * Date: 2016/2/19.
 */
public class BottomPopView extends Fragment implements View.OnClickListener {
    public final String TAG;
    private FragmentManager manager;
    private LayoutInflater inflater;
    private ViewGroup parentView;
    private View popView;
    private boolean isPop;
    private final int CLICK_INTERVAL_MIN = 3000;
    private long clickItemTime;
    private View currentView;
    private LinearLayout linearLayout;
    public BottomPopView() {
        TAG = this.getClass().getSimpleName();
    }
    public BottomPopView(FragmentManager manager) {
        TAG = this.getClass().getSimpleName();
        this.manager = manager;
        isPop = false;
        popView();
    }

    public void popView() {
        if (!isPop) {
            isPop = true;
            FragmentTransaction transaction = this.manager.beginTransaction();
            transaction.add(this,TAG);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        }
    }

    public void hideView() {
        if (isPop) {
            if (getFragmentManager() != null && getFragmentManager().getBackStackEntryCount() != 0) {
                getFragmentManager().popBackStack();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.remove(this);
                transaction.commitAllowingStateLoss();
                isPop = false;
            }
        }
    }

    public View createPopView() {
        FrameLayout parent = new FrameLayout(getActivity());
        parent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        currentView = new View(getActivity());
        currentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        currentView.setBackgroundColor(Color.argb(90, 0, 0, 0));
        currentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                hideView();
            }
        });

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        linearLayout = (LinearLayout) inflater.inflate(R.layout.bottom_pop_view, null);
        linearLayout.setLayoutParams(params);

        parent.addView(currentView);
        parent.addView(linearLayout);

        linearLayout.setOnClickListener(null);
        linearLayout.findViewById(R.id.weixinCircle).setOnClickListener(this);
        linearLayout.findViewById(R.id.weixin).setOnClickListener(this);
        linearLayout.findViewById(R.id.sina).setOnClickListener(this);
        linearLayout.findViewById(R.id.qq).setOnClickListener(this);
        linearLayout.findViewById(R.id.qqSpace).setOnClickListener(this);
        linearLayout.findViewById(R.id.instagram).setOnClickListener(this);
        linearLayout.findViewById(R.id.more).setOnClickListener(this);
        linearLayout.findViewById(R.id.report).setOnClickListener(this);
        linearLayout.findViewById(R.id.save_photo).setOnClickListener(this);
        return parent;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        parentView = (ViewGroup) getActivity().getWindow().getDecorView();
        popView = createPopView();
        parentView.addView(popView);
        currentView.startAnimation(createAlphaInAnimation());
        linearLayout.startAnimation(createTranslationInAnimation());

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        currentView.startAnimation(createAlphaOutAnimation());
        linearLayout.startAnimation(createTranslationOutAnimation());

        popView.postDelayed(new Runnable() {
            @Override
            public void run() {
                parentView.removeView(popView);
                parentView = null;
                popView = null;
                inflater = null;
                currentView = null;
                linearLayout = null;
            }
        }, 300);
    }

    @Override
    public void onClick(View v) {
        long time = System.currentTimeMillis();
        if (time - clickItemTime < CLICK_INTERVAL_MIN) {
            return;
        }
        clickItemTime = time;
        switch (v.getId()) {
            case R.id.weixinCircle:
                Toast.makeText(getContext(),"weixinCircle",Toast.LENGTH_SHORT).show();
                break;
            case R.id.weixin:
                Toast.makeText(getContext(),"weixin",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sina:
                Toast.makeText(getContext(),"sina",Toast.LENGTH_SHORT).show();
                break;
            case R.id.qq:
                Toast.makeText(getContext(),"qq",Toast.LENGTH_SHORT).show();
                break;
            case R.id.qqSpace:
                Toast.makeText(getContext(),"qqSpace",Toast.LENGTH_SHORT).show();
                break;
            case R.id.instagram:
                Toast.makeText(getContext(),"instagram",Toast.LENGTH_SHORT).show();
                break;
            case R.id.more:
                Toast.makeText(getContext(),"more",Toast.LENGTH_SHORT).show();
                break;
            case R.id.report:
                Toast.makeText(getContext(),"report",Toast.LENGTH_SHORT).show();
                break;
            case R.id.save_photo:
                Toast.makeText(getContext(),"save_photo",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private Animation createAlphaInAnimation() {
        AlphaAnimation an = new AlphaAnimation(0, 1);
        an.setDuration(300);
        return an;
    }

    private Animation createAlphaOutAnimation() {
        AlphaAnimation an = new AlphaAnimation(1, 0);
        an.setDuration(300);
        an.setFillAfter(true);
        return an;
    }

    private Animation createTranslationInAnimation() {
        int type = TranslateAnimation.RELATIVE_TO_SELF;
        TranslateAnimation an = new TranslateAnimation(type, 0, type, 0, type, 1, type, 0);
        an.setDuration(300);
        return an;
    }

    private Animation createTranslationOutAnimation() {
        int type = TranslateAnimation.RELATIVE_TO_SELF;
        TranslateAnimation an = new TranslateAnimation(type, 0, type, 0, type, 0, type, 1);
        an.setDuration(300);
        an.setFillAfter(true);
        return an;
    }
}
