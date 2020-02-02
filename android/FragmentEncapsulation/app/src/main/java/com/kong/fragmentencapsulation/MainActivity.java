package com.kong.fragmentencapsulation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button m_buttonCreateDialog;
    private RadioGroup m_DialogGroup;
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toast=Toast.makeText(MainActivity.this,"",Toast.LENGTH_SHORT);
        m_buttonCreateDialog=findViewById(R.id.buttonCreateDialog);
        m_buttonCreateDialog.setOnClickListener(new MyOnClickListener());
        m_DialogGroup=findViewById(R.id.dialogGroup);
        m_DialogGroup.setOnCheckedChangeListener(new MyOnCheckChangeListener());
    }

    //Button点击事件
    private class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

        }
    }

    //RadioGroup事件改变监听事件
    private class MyOnCheckChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            String radioButtonText=((RadioButton)group.findViewById(checkedId)).getText().toString().trim();
            toast.setText(radioButtonText);
            toast.show();
        }
    }


}
