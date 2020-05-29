package cn.zzerx.itimepicker.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import cn.zzerx.itimepicker.CasualiTimePicker;
import cn.zzerx.itimepicker.R;
import cn.zzerx.itimepicker.iTimePicker;

public class MainActivity extends AppCompatActivity {
    int show_type = 0;
    ViewGroup picker_parent;
    CasualiTimePicker picker;
    int[] MODE_LIST = {CasualiTimePicker.HOUR_AND_YEAR,
            CasualiTimePicker.YEAR_AND_HOUR,
            CasualiTimePicker.ONLY_YEAR,
            CasualiTimePicker.ONLY_HOUR};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(this);
    }

    private void init(final Context context) {
        picker = findViewById(R.id.casual_iTimePicker);
        Button setTime = findViewById(R.id.set_time);
        final EditText format_date = findViewById(R.id.format_date);
        final Button show_itimepicker = findViewById(R.id.show_itimepicker);
        final Button picker_mode = findViewById(R.id.picker_mode);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String format = String.valueOf(format_date.getText());
                Calendar calendar = Calendar.getInstance();
                try {
                    Date date = iTimePicker.DEFAULT_FORMAT.parse(format);
                    calendar.setTime(date);
                    picker.getPickerParent().setTimePicker(calendar);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "时间格式有误！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        picker_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i =  0; i < MODE_LIST.length; i++){
                    if(picker.PICKER_MODE == MODE_LIST[i]){
                        if(i == MODE_LIST.length -1){ i = 0;}else{i = i+1;}
                        picker.setTimeSelectMode(i);
                        picker_mode.setText("切换显示模式：" + picker.PICKER_MODE);
                        return;
                    }
                }

            }
        });

        picker.setTimeSelectMode(CasualiTimePicker.YEAR_AND_HOUR); // 设置显示模式 年/月/日 可点击切换
        iTimePicker ipicker = picker.getPickerParent();            // 获取iTimePicker
        ipicker.setPickersNumberSize(10);

        picker_parent = (ViewGroup) picker.getParent();
        show_itimepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show_type == 0) {
                    picker_parent.removeViewAt(3);
                    picker_parent.addView(new iTimePicker(context), 3);
                    ((TextView) findViewById(R.id.type_text)).setText("iTimePicker ");
                    show_itimepicker.setText("切换CasualiTimePicker");
                    show_type = 1;
                } else {
                    picker = new CasualiTimePicker(context);
                    picker_parent.removeViewAt(3);
                    picker_parent.addView(picker, 3);
                    ((TextView) findViewById(R.id.type_text)).setText("CasualiTimePicker ");
                    show_itimepicker.setText("切换iTimePicker");
                    show_type = 0;
                }

            }
        });


    }
}
