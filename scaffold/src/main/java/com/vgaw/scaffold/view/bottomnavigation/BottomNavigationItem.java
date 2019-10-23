package com.vgaw.scaffold.view.bottomnavigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vgaw.scaffold.R;
import com.vgaw.scaffold.util.phone.DensityUtil;

public class BottomNavigationItem extends RelativeLayout {
    private Drawable mBottomNavIcon;
    private Drawable mBottomNavChecked;
    private String mBottomNavName;

    private ImageView mBottomNavigationItemIv;
    private TextView mBottomNavigationItemTv;
    private ImageView mBubble;

    private Boolean mChecked;
    private OnItemCheckedListener mListener;

    public BottomNavigationItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BottomNavigationItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setOnItemCheckedListener(OnItemCheckedListener listener) {
        mListener = listener;
    }

    public void showBubble(boolean show) {
        mBubble.setVisibility(show ? VISIBLE : GONE);
    }

    public void check(boolean check) {
        onCheckChanged(check);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BottomNavigationItem);
        mBottomNavIcon = array.getDrawable(R.styleable.BottomNavigationItem_bottomNavIcon);
        mBottomNavChecked = array.getDrawable(R.styleable.BottomNavigationItem_bottomNavIconChecked);
        mBottomNavName = array.getString(R.styleable.BottomNavigationItem_bottomNavName);
        int size = DensityUtil.dp2px(getContext(), 24);
        mBottomNavIcon.setBounds(0, 0, size, size);
        mBottomNavChecked.setBounds(0, 0, size, size);

        array.recycle();

        View view = View.inflate(getContext(), R.layout.bottom_navigation_item, this);
        mBottomNavigationItemIv = view.findViewById(R.id.bottom_navigation_item_iv);
        mBottomNavigationItemTv = view.findViewById(R.id.bottom_navigation_item_tv);
        mBubble = view.findViewById(R.id.bottom_navigation_item_bubble);

        mBottomNavigationItemTv.setText(mBottomNavName);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkedNow = !mChecked;

                if (checkedNow) {
                    onCheckChanged(checkedNow);
                }
            }
        });

        check(false);
    }

    private void onCheckChanged(boolean checkedNow) {
        if (mChecked == null || checkedNow != mChecked) {
            if (checkedNow) {
                mBottomNavigationItemIv.setImageDrawable(mBottomNavChecked);
                mBottomNavigationItemTv.setTextColor(getResources().getColor(R.color.colorPrimary));

                callListener();
            } else {
                mBottomNavigationItemIv.setImageDrawable(mBottomNavIcon);
                mBottomNavigationItemTv.setTextColor(getResources().getColor(R.color.black4));
            }

            mChecked = checkedNow;
        }
    }

    private void callListener() {
        if (mListener != null) {
            mListener.onItemChecked(-1);
        }
    }
}
