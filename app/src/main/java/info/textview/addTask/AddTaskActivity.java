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
    private String nameOfProject;
    private DataBaseToDo dataBaseToDo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDataBase();
        initVariable();


    }

    private void initDataBase() {

        dataBaseToDo = new DataBaseToDo(this);
        dataBaseToDo.setNameOfTable(getNameOfProject());

    }

    private String getNameOfProject() {

        nameOfProject = getIntent().getStringExtra(NAME_OF_PROJECT_EXIST);

        return nameOfProject;

    }

    private void initVariable() {

        textView = (TextView) findViewById(R.id.textView1);
        editText = (EditText) findViewById(R.id.editText);


    }

    private void addTitleToSqLite() {


        dataBaseToDo.addTitle(getTitleFromEditText());


    }

    private String getTitleFromEditText() {

        String value = editText.getText().toString();

        return value;
    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonAdd:

                try {

                    addTitleToSqLite();
                    textView.setText(getTitleFromEditText());
                    editText.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.buttonNext:

                try {

                    Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
                    intent.putExtra(NAME_OF_PROJECT_EXIST, nameOfProject);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

        }

    }


}