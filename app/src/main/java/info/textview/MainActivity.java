package info.textview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import info.textview.DataBase.DataBaseToDo;

public class MainActivity extends Activity {

    Button buttonStart, buttonNext;
    TextView textView;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariable();


    }

    public void initVariable() {

        buttonStart = (Button) findViewById(R.id.buttonAdd);
        textView = (TextView) findViewById(R.id.textView1);
        editText = (EditText) findViewById(R.id.editText);
        buttonNext = (Button) findViewById(R.id.buttonNext);


    }

    public void addTitleToSqLite(){

        DataBaseToDo dataBaseToDo = new DataBaseToDo(this);
        dataBaseToDo.addTitle(getTitleFromEditText());


    }

    public String getTitleFromEditText(){

        String value = editText.getText().toString();

        return value;
    }

    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonAdd:
                try{

                    addTitleToSqLite();
                    textView.setText(getTitleFromEditText());
                    editText.setText("");

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case R.id.buttonNext:
                try{

                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    startActivity(intent);

                }catch (Exception e){
                    e.printStackTrace();
                }



        }

    }
}