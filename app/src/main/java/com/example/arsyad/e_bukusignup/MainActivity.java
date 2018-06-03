package com.example.arsyad.e_bukusignup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv_NameResult;
    String nameResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        Bundle extras = getIntent().getExtras();
        if (extras!=null)
            nameResult = extras.getString("name_result");
        tv_NameResult.setText(nameResult);
    }

    private void initComponents() {
        tv_NameResult = (TextView) findViewById(R.id.tv_NameResult);
    }
}
