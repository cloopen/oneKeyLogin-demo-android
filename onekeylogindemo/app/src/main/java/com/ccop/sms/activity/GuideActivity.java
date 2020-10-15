package com.ccop.sms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ccop.sms.R;
import com.ccop.sms.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG =GuideActivity.class.getSimpleName();
    private Context context;
    private ViewPager mViewPager;
    //容器
    private List<View> mList = new ArrayList<>();
    private View view1, view2,  viewLast;
    //小圆点
    private ImageView point1, point2,  pointLast;
    //跳过
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        StatusBarUtil.setStatusBarTranslucent(this, true);
        context=this;
        Log.e(TAG,"Create GuideActivity");
        initView();
        initListener();
    }

    private void initView() {
        point1 = (ImageView) findViewById(R.id.point1);
        point2 = (ImageView) findViewById(R.id.point2);
        pointLast = (ImageView) findViewById(R.id.pointLast);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        //设置默认图片
        setPointImg(true, false,  false);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        view1 = View.inflate(this, R.layout.pager_item_one, null);
        view2 = View.inflate(this, R.layout.pager_item_two, null);

        viewLast = View.inflate(this, R.layout.pager_item_last, null);
        viewLast.findViewById(R.id.btn_start).setOnClickListener(this);

        mList.add(view1);
        mList.add(view2);
        mList.add(viewLast);

        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());
    }

    private void initListener() {
        //监听ViewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //pager切换
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setPointImg(true, false,  false);
                        btn_back.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImg(false, true,  false);
                        btn_back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImg(false, false,  true);
                        btn_back.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //设置小圆点的选中效果
    private void setPointImg(boolean isCheck1, boolean isCheck2, boolean isCheckLast) {
        if (isCheck1) {
            point1.setBackgroundResource(R.drawable.point_on);
        } else {
            point1.setBackgroundResource(R.drawable.point_off);
        }

        if (isCheck2) {
            point2.setBackgroundResource(R.drawable.point_on);
        } else {
            point2.setBackgroundResource(R.drawable.point_off);
        }

        if (isCheckLast) {
            pointLast.setBackgroundResource(R.drawable.point_on);
        } else {
            pointLast.setBackgroundResource(R.drawable.point_off);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
            case R.id.btn_back:
                Log.e(TAG,"btn_back");
                startActivity(new Intent(context, MainActivity.class));
                finish();
                break;
        }
    }


    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }
    }
}
