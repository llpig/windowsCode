package com.kong.viewtext;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
(1)创建一个继承了AppCompatActivity的类(Activity的本质就是类)
(2)在活动清单中声明该活动，不然会找不到。
(3)重写onCreate方法
(4)创建对应的XML文件（如果需要的话）
(5)设置该界面的UI(setContentView)
 */

public class RadioButtonTestActivity extends AppCompatActivity {
    private RadioGroup m_RadioGroup;
    private TextView m_TextView;
    private CheckBox check1,check2,check3,check4,check5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiobutton_text);
        m_RadioGroup=findViewById(R.id.sexRadioGroup);
        m_RadioGroup.setOnCheckedChangeListener(new MyOnCheckedChange());

        CheckBoxCheckedChangeListener changeListener=new CheckBoxCheckedChangeListener();
        check1=findViewById(R.id.checkbox1);
        check1.setOnCheckedChangeListener(changeListener);
        check2=findViewById(R.id.checkbox2);
        check2.setOnCheckedChangeListener(changeListener);
        check3=findViewById(R.id.checkbox3);
        check3.setOnCheckedChangeListener(changeListener);
        check4=findViewById(R.id.checkbox4);
        check4.setOnCheckedChangeListener(changeListener);
        check5=findViewById(R.id.checkbox5);
        check5.setOnCheckedChangeListener(changeListener);

    }
    private class CheckBoxCheckedChangeListener implements CompoundButton.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {
                Toast.makeText(RadioButtonTestActivity.this,buttonView.getText(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    //重写RadioGroup的onChangedListener
    private class MyOnCheckedChange implements RadioGroup.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            String str=((RadioButton)(group.findViewById(checkedId))).getText().toString();
            Toast.makeText(RadioButtonTestActivity.this,str,Toast.LENGTH_SHORT).show();
//            String content=m_TextView.getText().toString()+"\n你的性别："+((RadioButton)(group.findViewById(checkedId))).getText();
//            m_TextView.setText(content);
        }
    }
}
