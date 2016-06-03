package info.textview.addTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import info.textview.DataBase.DataBaseToDo;
import info.textview.R;
import info.textview.TasksItems.TasksActivity;


public class AddTaskActivity extends Activity {

    public static final String NAME_OF_PROJECT_EXIST = "info.textview.NameOfProjectExist";

    public static final String NAME_OF_PROJECT_CREATE = "info.textview.NameOfProjectCreate";


    private TextView textView;
    private EditText editText;
    private String name;

    private DataBaseToDo dataBaseToDo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initVariable();



    }

    private void initVariable() {

        textView = (TextView) findViewById(R.id.textView1);
        editText = (EditText) findViewById(R.id.editText);
        dataBaseToDo = new DataBaseToDo(this);

    }

    private void addTitleToSqLite(){


        dataBaseToDo.addTitle(getTitleFromEditText());


    }

    private String getTitleFromEditText(){

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

                    Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
                    intent.putExtra(NAME_OF_PROJECT_EXIST, name);
                    startActivity(intent);

                }catch (Exception e){
                    e.printStackTrace();
                }

        }

    }


}