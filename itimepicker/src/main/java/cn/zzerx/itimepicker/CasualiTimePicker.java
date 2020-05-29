package cn.zzerx.itimepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;


public class CasualiTimePicker extends LinearLayout {

    private static ImageButton showDateSelect;
    private static iTimePicker pickerParent;
    private static TextView small_time;
    public int PICKER_MODE;

    public static final int HOUR_AND_YEAR = 0;
    public static final int YEAR_AND_HOUR = 1;
    public static final int ONLY_YEAR = 2;
    public static final int ONLY_HOUR = 3;

    public interface OnModeChange {
        void onModeChange(int oldMode, int newMode);
    }

    public CasualiTimePicker(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(
                this.getResources().getIdentifier("casua_itimepicker", "layout", context.getPackageName()),
                this, true);
        init(context);
    }

    public CasualiTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(
                this.getResources().getIdentifier("casua_itimepicker", "layout", context.getPackageName()),
                this,
                true);
        init(context);
    }


    // 数字改变接口
    private static iTimePicker.OnValueChanges onValueChanges;

    public void setOnValueChangesListener(final iTimePicker.OnValueChanges onValueChanges) {
        CasualiTimePicker.onValueChanges = onValueChanges;
        pickerParent.setOnValueChangesListener(new iTimePicker.OnValueChanges() {
            @Override
            public void onValueChanges(NumberPicker picker, int oldVal, int newVal, int change_picker) {
                updateSmallTime(PICKER_MODE, pickerParent.getTimePicker());
                onValueChanges.onValueChanges(picker, oldVal, newVal, change_picker);

            }

        });
    }

    private static OnModeChange OnModeChange;

    public void setOnModeChangesListener(OnModeChange onModeChanges) {
        OnModeChange = onModeChanges;
    }

    private void init(Context context) {
        pickerParent = findViewById(this.getResources().getIdentifier("timer_picker", "id", context.getPackageName()));
        showDateSelect = findViewById(this.getResources().getIdentifier("show", "id", context.getPackageName()));
        small_time = findViewById(this.getResources().getIdentifier("small_time", "id", context.getPackageName()));
        showDateSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PICKER_MODE == YEAR_AND_HOUR || PICKER_MODE == ONLY_YEAR) {
                    setTimeSelectMode(HOUR_AND_YEAR);
                } else {
                    setTimeSelectMode(YEAR_AND_HOUR);
                }
            }
        });

        CNumPicket[] pickers = getPickerParent().getPickersList();
        for (CNumPicket picker : pickers) {
            iTimePickerUtil.setNumberPickerDividerColor(picker, "#FFFFFFFF");
        }
    }

    private static void setVis(int mode) {
        int year_state;
        int hour_state;

        if (mode == 1) {
            hour_state = GONE;
            year_state = VISIBLE;
        } else {
            hour_state = VISIBLE;
            year_state = GONE;
        }
        pickerParent.getYearPicker().setVisibility(year_state);
        pickerParent.getMonthPicker().setVisibility(year_state);
        pickerParent.getDayPicker().setVisibility(year_state);
        pickerParent.getHourPicker().setVisibility(hour_state);
        pickerParent.getMinutePicker().setVisibility(hour_state);
    }

    public void setTimeSelectMode(int mode) {
        if (OnModeChange != null) {
            OnModeChange.onModeChange(PICKER_MODE, mode);
        }
        PICKER_MODE = mode;
        updateSmallTime(PICKER_MODE, pickerParent.getTimePicker());
        switch (mode) {
            case HOUR_AND_YEAR:
                setVis(0);
                showDateSelect.setClickable(true);
                break;
            case YEAR_AND_HOUR:
                setVis(1);
                showDateSelect.setClickable(true);
                break;
            case ONLY_HOUR:
                setVis(0);
                showDateSelect.setClickable(false);
                break;
            case ONLY_YEAR:
                setVis(1);
                showDateSelect.setClickable(false);
                break;
        }
    }

    private static int counterState(int state) {
        if (state == View.GONE || state == View.INVISIBLE) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }


    public iTimePicker getPickerParent() {
        return pickerParent;
    }

    public void updateSmallTime(int mode, Calendar calendar) {
        if (mode == CasualiTimePicker.HOUR_AND_YEAR || mode == CasualiTimePicker.ONLY_HOUR) {
            small_time.setText(iTimePicker.DEFAULT_FORMAT_YEAR.format(calendar.getTime()));
        } else if (mode == CasualiTimePicker.YEAR_AND_HOUR || mode == CasualiTimePicker.ONLY_YEAR) {
            small_time.setText(iTimePicker.DEFAULT_FORMAT_HOUR.format(calendar.getTime()));
        }
    }

}
