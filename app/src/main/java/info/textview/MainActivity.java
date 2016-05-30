package info.textview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button buttonStart, buttonNext;
    TextView textView;
    EditText editText;
    ArrayList<String> dataForListView;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariable();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = editText.getText().toString();
                textView.setText(value);
                dataForListView.add(counter, value);
                counter++;
                editText.setText("");

            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("lista", dataForListView);
                startActivity(intent);

            }
        });
    }

    public void initVariable() {

        buttonStart = (Button) findViewById(R.id.buttonAdd);
        textView = (TextView) findViewById(R.id.textView1);
        editText = (EditText) findViewById(R.id.editText);
        buttonNext = (Button) findViewById(R.id.buttonNext);

        counter = 0;
        dataForListView = new ArrayList<String>();

    }
}