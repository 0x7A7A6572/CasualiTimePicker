package cn.zzerx.itimepicker;

import android.annotation.SuppressLint;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

public class iTimePickerUtil {


    /**
     * 设置numberpicker分割线颜色
     * color 为空默认为蓝色
     *
     * @param numberPicker
     * @param color        颜色  “#000000”
     */
    @SuppressLint("NewApi")
    public static void setNumberPickerDividerColor(NumberPicker numberPicker, String color) {
        if (color == null || color.equals("")) {
            color = "#0000FF";
        }
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(picker, new ColorDrawable(Color.parseColor(color)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 设置时间选择器DatePicker的分割线颜色
     * color 为空默认为蓝色
     *
     * @param datePicker
     * @param color      颜色 “#000000”
     */
    @SuppressLint("NewApi")
    public static void setDatePickerDividerColor(DatePicker datePicker, String color) {
        if (color == null || color.equals("")) {
            color = "#0000FF";
        }
        // Divider changing:

        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);

        // 获取 NumberPicker 
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(Color.parseColor(color)));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

        }
    }


}
