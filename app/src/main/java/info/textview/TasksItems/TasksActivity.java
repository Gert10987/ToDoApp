package info.textview.TasksItems;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.SystemClock;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


import info.textview.DataBase.CursorAdapterToDo;
import info.textview.DataBase.DataBaseToDo;
import info.textview.R;
import info.textview.StartActivity.StartActivity;
import info.textview.addTask.AddTaskActivity;


public class TasksActivity extends AppCompatActivity {

    public static final String NAME_OF_PROJECT_EXIST = "info.textview.NameOfProjectExist";

    public static final String NAME_OF_PROJECT_CREATE = "info.textview.NameOfProjectCreate";

    private static final int IS_DONE = 2;

    private static final int IS_DOING = 1;

    private static final int TO_DO = 0;

    private ListView listWithData;
    private ArrayList<String> arrayListToGetData;
    private Chronometer chronometer;
    private TextView textView;
    private Button stopCountingButton;
    private long positionInListView;
    private String nameOfProject;

    private DataBaseToDo dataBaseToDo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);

        initDataBase();
        initNameOfProject();
        initVariables();
        setListWithDataCursor();
        listViewListener();
        buttonViewListner();
        getSumColumnTimeInteger();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.addTask:{

                Intent intent = new Intent(TasksActivity.this, AddTaskActivity.class);
                intent.putExtra(NAME_OF_PROJECT_EXIST, nameOfProject);
                startActivity(intent);

                break;

            }


        }

        return true;
    }

    private void initDataBase() {

        dataBaseToDo = new DataBaseToDo(this);

    }


    private void initNameOfProject() {

        nameOfProject = getIntent().getStringExtra(NAME_OF_PROJECT_EXIST);

        if (nameOfProject == null) {

            nameOfProject = getIntent().getStringExtra(NAME_OF_PROJECT_CREATE);

            Log.v("TAG", nameOfProject);

            dataBaseToDo.newTable(nameOfProject);

        } else {

            dataBaseToDo.setNameOfTable(nameOfProject);

        }

    }

    private void initVariables() {

        listWithData = (ListView) findViewById(R.id.listView);
        chronometer = (Chronometer) findViewById(R.id.chronometerr);
        textView = (TextView) findViewById(R.id.zzz);
        stopCountingButton = (Button) findViewById(R.id.stopButton);


    }


    private void setListWithDataCursor() {

        Cursor c = dataBaseToDo.display();
        CursorAdapterToDo cursorAdapterToDo = new CursorAdapterToDo(this, c, 0);
        listWithData.setAdapter(cursorAdapterToDo);

    }

    private void listViewListener() {

        listWithData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               positionInListView = parent.getItemIdAtPosition(position);
               setUpAlertDialogWhenStartTask();
               view.setBackgroundColor(Color.YELLOW);


            }
        });

        listWithData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                setUpAlertDialogWhenDelete(parent.getItemIdAtPosition(position));

                return true;
            }
        });


    }


    private void buttonViewListner() {

        stopCountingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chronometerStopCounting();
                setUpAlertDialogWhenStopButtonClicked(positionInListView);
                listWithData.setEnabled(true);

            }
        });
    }

    private void getSumColumnTimeInteger() {

        int sumColumnTimeInteger = dataBaseToDo.sumColumn();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);
        String time = df.format(new Date(sumColumnTimeInteger * 1000L));


        textView.setText(String.valueOf(time));

    }

    private void chronometerStopCounting() {

        chronometer.stop();

        // textView.setText(getChronometerValue());

    }

    private String getChronometerValue() {

        String chronometrValue = chronometer.getText().toString();

        return chronometrValue;
    }

    private void chronometrStartCounting() {

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

    }


    public int getSecondsFromDurationString(String value) {

        String[] parts = value.split(":");

        // Wrong format, no value for you.
        if (parts.length < 2 || parts.length > 3)
            return 0;

        int seconds = 0, minutes = 0, hours = 0;

        if (parts.length == 2) {
            seconds = Integer.parseInt(parts[1]);
            minutes = Integer.parseInt(parts[0]);
        } else if (parts.length == 3) {
            seconds = Integer.parseInt(parts[2]);
            minutes = Integer.parseInt(parts[1]);
            hours = Integer.parseInt(parts[1]);
        }

        return seconds + (minutes * 60) + (hours * 3600);
    }

    private void setUpAlertDialogWhenStopButtonClicked(final long positionInListView) {


        dataBaseToDo.setIndexPosition(positionInListView);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                addTimeToSqlite();
                addTimeIntegerToSqLite();
                addIsDoneValueToSqLite(IS_DOING);
                resetActivity();
            }


        });

        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                addTimeToSqlite();
                addTimeIntegerToSqLite();
                addIsDoneValueToSqLite(IS_DONE);
                Log.v("DATABASE TO DO", String.valueOf(positionInListView));
                resetActivity();

            }
        });

        alertDialogBuilder.setMessage(R.string.alertMessage);

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    private void setUpAlertDialogWhenDelete(final long positionInListView) {


        dataBaseToDo.setIndexPosition(positionInListView);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Toast.makeText(getApplicationContext(), R.string.nothingDeleted, Toast.LENGTH_SHORT).show();
            }


        });

        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                deleteRowFromDataBase(positionInListView);
                resetActivity();

            }
        });

        alertDialogBuilder.setMessage(R.string.alertMessageWhenDeleteRow);

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    private void setUpAlertDialogWhenStartTask() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                isStartingTask(false);
                Toast.makeText(getApplicationContext(), R.string.taskNotRunning, Toast.LENGTH_SHORT).show();
            }


        });

        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                isStartingTask(true);

                chronometrStartCounting();

                Log.v("POSTION_TAG", String.valueOf(positionInListView));
                listWithData.setEnabled(false);

            }
        });

        alertDialogBuilder.setMessage(R.string.alertMessageWhenStartTask);

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    private void addTimeIntegerToSqLite() {

        dataBaseToDo.addTimeInteger(getSecondsFromDurationString(getChronometerValue()));
    }

    private void addIsDoneValueToSqLite(int isDone) {

        dataBaseToDo.addIsDoneValue(isDone);
    }

    private void addTimeToSqlite() {

        dataBaseToDo.addTime(getChronometerValue());

    }

    private void resetActivity() {

        Intent intent = new Intent(TasksActivity.this, TasksActivity.class);
        intent.putExtra(StartActivity.NAME_OF_PROJECT_EXIST, nameOfProject);
        startActivity(intent);

    }

    private void deleteRowFromDataBase(long id) {


        dataBaseToDo.deleteRecord(id);
    }


    private boolean isStartingTask(boolean isStarting) {

        return isStarting;
    }



}
