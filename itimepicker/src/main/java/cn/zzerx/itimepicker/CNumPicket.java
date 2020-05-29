package cn.zzerx.itimepicker;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

public class CNumPicket extends NumberPicker {


    int TextColor;
    float TextSize;
    private OnVisibilityChangedListen onVisibilityChangedListen;

    public static interface OnVisibilityChangedListen {
        void onVisibilityChanged(CNumPicket picker, int visibility);
    }

    public void setOnVisibilityChangedListen(OnVisibilityChangedListen listen) {
        onVisibilityChangedListen = listen;
    }

    public CNumPicket(Context context) {
        super(context);
    }

    public CNumPicket(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CNumPicket(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setTextColor(int textColor) {
        TextColor = textColor;
    }

    public int getTextColor() {
        return TextColor;
    }

    public void setTextSize(float textSize) {
        TextSize = textSize;
    }

    public float getTextSize() {
        return TextSize;
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    public void updateView(View view) {
        if (view instanceof EditText) {
            //这里修改字体的属性
            ((EditText) view).setTextColor(Color.WHITE);
            ((EditText) view).setTextSize(20);
        }


    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (onVisibilityChangedListen != null) {
            onVisibilityChangedListen.onVisibilityChanged(this, visibility);
        }
    }


}
