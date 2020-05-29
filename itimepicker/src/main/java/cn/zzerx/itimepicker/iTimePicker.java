package cn.zzerx.itimepicker;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class iTimePicker extends LinearLayout {

    // 数字改变接口
    private static OnValueChanges onValueChanges;


    public interface OnValueChanges {
        void onValueChanges(NumberPicker picker, int oldVal, int newVal, int change_picker);
    }

    public void setOnValueChangesListener(OnValueChanges onValueChanges) {
        iTimePicker.onValueChanges = onValueChanges;
    }


    public static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
    public static final SimpleDateFormat DEFAULT_FORMAT_YEAR = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    public static final SimpleDateFormat DEFAULT_FORMAT_HOUR = new SimpleDateFormat("HH:mm", Locale.getDefault());


    /**
     * pickerParent -> NumberPiker中直接的父控件
     * yearToDay，hourToMinute 对应年月日模式和时分模式的NumberPicker和其对应的TextView视图 @link{R.layout.time_picker}
     */


    private static Calendar MaxDate;


    private static CNumPicket yearPicker, monthPicker, dayPicker, hourPicker, minutePicker;
    private static TextView yearPickerText, monthPickerText, dayPickerText, hourPickerText, minutePickerText;


    public static CNumPicket getYearPicker() {
        return yearPicker;
    }

    public static CNumPicket getMonthPicker() {
        return monthPicker;
    }

    public static CNumPicket getDayPicker() {
        return dayPicker;
    }

    public static CNumPicket getHourPicker() {
        return hourPicker;
    }

    public static CNumPicket getMinutePicker() {
        return minutePicker;
    }

    public static TextView getYearPickerText() {
        return yearPickerText;
    }

    public static TextView getMonthPickerText() {
        return monthPickerText;
    }

    public static TextView getDayPickerText() {
        return dayPickerText;
    }

    public static TextView getHourPickerText() {
        return hourPickerText;
    }

    public static TextView getMinutePickerText() {
        return minutePickerText;
    }


    public iTimePicker(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(
                this.getResources().getIdentifier("itimepicker", "layout", context.getPackageName()),
                this, true);
        init(context);
    }

    public iTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(
                this.getResources().getIdentifier("itimepicker", "layout", context.getPackageName()),
                this, true);
        init(context);
    }

    public void init(Context context) {

        yearPicker = findViewById(this.getResources().getIdentifier("year", "id", context.getPackageName()));
        monthPicker = findViewById(this.getResources().getIdentifier("month", "id", context.getPackageName()));
        dayPicker = findViewById(this.getResources().getIdentifier("day", "id", context.getPackageName()));
        hourPicker = findViewById(this.getResources().getIdentifier("hour", "id", context.getPackageName()));
        minutePicker = findViewById(this.getResources().getIdentifier("minute", "id", context.getPackageName()));

        yearPickerText = findViewById(this.getResources().getIdentifier("year_text", "id", context.getPackageName()));
        monthPickerText = findViewById(this.getResources().getIdentifier("month_text", "id", context.getPackageName()));
        dayPickerText = findViewById(this.getResources().getIdentifier("day_text", "id", context.getPackageName()));
        hourPickerText = findViewById(this.getResources().getIdentifier("hour_text", "id", context.getPackageName()));
        minutePickerText = findViewById(this.getResources().getIdentifier("minute_text", "id", context.getPackageName()));

        CNumPicket.OnVisibilityChangedListen bindViewVisibilityChange = new CNumPicket.OnVisibilityChangedListen() {
            @Override
            public void onVisibilityChanged(CNumPicket picker, int visibility) {
                bindViewVisibility(picker, visibility);
            }
        };
        yearPicker.setOnVisibilityChangedListen(bindViewVisibilityChange);
        monthPicker.setOnVisibilityChangedListen(bindViewVisibilityChange);
        dayPicker.setOnVisibilityChangedListen(bindViewVisibilityChange);
        hourPicker.setOnVisibilityChangedListen(bindViewVisibilityChange);
        minutePicker.setOnVisibilityChangedListen(bindViewVisibilityChange);

        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return repairValue(value);
            }
        };


        final NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int CHANGE_PICKER = -1;
                if (picker == yearPicker) {
                    CHANGE_PICKER = 0;
                } else if (picker == monthPicker) {
                    CHANGE_PICKER = 1;
                } else if (picker == dayPicker) {
                    CHANGE_PICKER = 2;
                } else if (picker == hourPicker) {
                    CHANGE_PICKER = 3;
                } else if (picker == minutePicker) {
                    CHANGE_PICKER = 4;
                }


                int THIS_MAX = -1;
                int THIS_MIN = -1;
                int FIX_TYPE = -1;
                switch (CHANGE_PICKER) {
                    case 0:
                        THIS_MAX = yearPicker.getMaxValue();
                        THIS_MIN = yearPicker.getMinValue();
                        FIX_TYPE = -1111;
                        break;
                    case 1:
                        THIS_MAX = monthPicker.getMaxValue();
                        THIS_MIN = monthPicker.getMinValue();
                        FIX_TYPE = Calendar.YEAR;

                        break;
                    case 2:
                        THIS_MAX = dayPicker.getMaxValue();// getLastDayOfMother(yearPicker.getMaxValue(), motherPicker.getMaxValue());
                        THIS_MIN = dayPicker.getMinValue();
                        FIX_TYPE = Calendar.MONTH;

                        break;
                    case 3:
                        THIS_MAX = hourPicker.getMaxValue();
                        THIS_MIN = hourPicker.getMinValue();
                        FIX_TYPE = Calendar.DAY_OF_MONTH;
                        break;
                    case 4:
                        THIS_MAX = minutePicker.getMaxValue();
                        THIS_MIN = minutePicker.getMinValue();
                        FIX_TYPE = Calendar.HOUR;
                        break;
                }

                Calendar cal = getTimePicker();

                /*
                
                 29  ↑   30(旧)      新<旧 & 旧 = 月份最后一天
                 >30  ↑  >01(新)  ->  2020/5/30 -> 2020/5/1(getPiker)
                 01  ↑   02          (getPiker) 月份 + 1 -> setPiker;
                
                
                 30  ↓   29          |新>旧 & 新 = 月份最后一天 ╳
                 >01  ↓  >30(新)  -> {
                 02  ↓   01(旧)      |
                 
                 */


                if (FIX_TYPE == Calendar.YEAR || FIX_TYPE == Calendar.MONTH) {
                    if (oldVal > newVal && oldVal != newVal + 1 && oldVal == THIS_MAX) {
                        cal.add(FIX_TYPE, 1);
                        setTimePicker(cal);
                        return;

                    } else if (newVal > oldVal && newVal != THIS_MIN + 1 && oldVal == THIS_MIN) {
                        cal.add(FIX_TYPE, -1);
                        setTimePicker(cal);
                        return;
                    }/*else if (picker == dayPicker && newVal < oldVal && newVal == THIS_MIN + 1) { //这时候该更新最后一天
     
                       dayPicker.setMaxValue(getLastDayOfMother(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH) -1 )); //这里的 -1是需要获取上一个月的最大天数
                       return;
                   }else if(picker == dayPicker && newVal > oldVal && oldVal == THIS_MIN + 1){
                  
                       dayPicker.setMaxValue(getLastDayOfMother(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)));
                      return;
                   }*/
                } else {
                    setTimePicker(cal);
                }

                if (onValueChanges != null) {
                    onValueChanges.onValueChanges(picker, oldVal, newVal, CHANGE_PICKER);
                }
            }
        };


        // 获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        int lastDay = getLastDayOfMonth(cale.get(Calendar.YEAR), cale.get(Calendar.MONTH));

        yearPicker.setOnValueChangedListener(onValueChangeListener);
        yearPicker.setFormatter(formatter);
        yearPicker.setMaxValue(cale.get(Calendar.YEAR));
        yearPicker.setMinValue(1938);
        yearPicker.setValue(cale.get(Calendar.YEAR));
        yearPicker.setWrapSelectorWheel(false);

        monthPicker.setOnValueChangedListener(onValueChangeListener);
        monthPicker.setFormatter(formatter);
        monthPicker.setMaxValue(12);
        monthPicker.setMinValue(1);
        monthPicker.setValue(cale.get(Calendar.MONTH)); //Calendar.MONTH 格里高利历和罗马儒略历中一年中的第一个月是 JANUARY 为 0


        dayPicker.setOnValueChangedListener(onValueChangeListener);
        dayPicker.setFormatter(formatter);
        dayPicker.setMaxValue(lastDay);
        dayPicker.setMinValue(1);
        dayPicker.setValue(cale.get(Calendar.DATE));


        hourPicker.setFormatter(formatter);
        hourPicker.setOnValueChangedListener(onValueChangeListener);
        hourPicker.setMaxValue(23);
        hourPicker.setMinValue(0);
        hourPicker.setValue(cale.get(Calendar.HOUR));

        minutePicker.setFormatter(formatter);
        minutePicker.setOnValueChangedListener(onValueChangeListener);
        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);
        minutePicker.setValue(cale.get(Calendar.MINUTE));

        //设置为对当前值不可编辑
        monthPicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        dayPicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        hourPicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        minutePicker.setDescendantFocusability(iTimePicker.FOCUS_BLOCK_DESCENDANTS);

        /* //这里设置为不循环显示，默认值为true
         hourPicker.setWrapSelectorWheel(false);
         minutePicker.setWrapSelectorWheel(false);
         */


    }


    public static String repairValue(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = 0 + tmpStr;
        }
        return tmpStr;
    }


    private static int getLastDayOfMonth(int year, int month) {
        // 获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.YEAR, year);
        cale.set(Calendar.MONTH, month);
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return cale.get(Calendar.DAY_OF_MONTH);
    }

    public Calendar getTimePicker() {
        int year, month, day, hour, minute;
        year = yearPicker.getValue();
        month = monthPicker.getValue() - 1; //这里的month是getValue所得 所以需要-1
        day = dayPicker.getValue();
        hour = hourPicker.getValue();
        minute = minutePicker.getValue();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, minute);
        return cal;

    }


    /**
     * (1)任何一个滑动改变触发setTimePicker都会改变所有值，但是setValue不会触发其他的OnValueChange事件
     */

    public void setTimePicker(Calendar cal) {
        yearPicker.setValue(cal.get(Calendar.YEAR));
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);
        dayPicker.setValue(cal.get(Calendar.DAY_OF_MONTH));
        hourPicker.setValue(cal.get(Calendar.HOUR_OF_DAY));
        minutePicker.setValue(cal.get(Calendar.MINUTE));

        //   if(cal.get(Calendar.DAY_OF_MONTH) == 1 || cal.get(Calendar.DAY_OF_MONTH) >= getLastDayOfMother(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)) ){
        //      dayPicker.setMaxValue(getLastDayOfMother(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)-1));

        //   }else{
        dayPicker.setMaxValue(getLastDayOfMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)));

        //  }

    }

    public void setMaxValve(Context context, Calendar cal) {
        MaxDate = cal;
        yearPicker.setMaxValue(cal.get(Calendar.YEAR));


    }

    public Calendar getCalenderForPicker() {
        int hour, minute;
        hour = hourPicker.getValue();
        minute = minutePicker.getValue();
        int day, month, year;
        day = dayPicker.getValue();
        month = monthPicker.getValue();
        year = yearPicker.getValue();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, minute);
        return cal;
    }

    public Date getDateForPicker() {
        return getCalenderForPicker().getTime();
    }

    private void bindViewVisibility(CNumPicket picker, int visibility) {
        if (picker == yearPicker) {
            yearPickerText.setVisibility(visibility);
        } else if (picker == monthPicker) {
            monthPickerText.setVisibility(visibility);
        } else if (picker == dayPicker) {
            dayPickerText.setVisibility(visibility);
        } else if (picker == hourPicker) {
            hourPickerText.setVisibility(visibility);
        } else if (picker == minutePicker) {
            minutePickerText.setVisibility(visibility);
        }
    }

    public void setPickersNumberColor(int color) {
        CNumPicket[] pickers = getPickersList();
        for (CNumPicket picker : pickers) {
            picker.setTextColor(color);
        }
    }

    public void setPickersNumberSize(float size) {
        CNumPicket[] pickers = getPickersList();
        for (CNumPicket picker : pickers) {
            picker.setTextSize(size);
        }
    }


    public CNumPicket[] getPickersList() {
        return new CNumPicket[]{yearPicker, monthPicker, dayPicker, hourPicker, minutePicker};
    }

    public TextView[] getPickersTextList() {
        return new TextView[]{yearPickerText, monthPickerText, dayPickerText, hourPickerText, minutePickerText};
    }


}
