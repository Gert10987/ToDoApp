package info.textview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import info.textview.DataBase.DataBaseToDo;



public class SecondActivity extends AppCompatActivity {

    private static final int IS_DONE = 2;

    private static final int IS_DOING = 1;

    private static final int TO_DO = 0;

    ListView listWithData;
    ArrayAdapter<String> arrayAdapter;
    ListViewHolder listViewHolder;
    ArrayList<String> arrayListToGetData;
    Chronometer chronometer;
    TextView textView;
    Button stopCountingButton;
    long positionInListView;

    DataBaseToDo dataBaseToDo = new DataBaseToDo(SecondActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initVariables();
        setListWithDataCursor();
        listViewListener();
        buttonViewListner();
        getSumColumnTimeInteger();



    }

    private void listViewListener() {

        listWithData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                positionInListView = parent.getItemIdAtPosition(position);
                setUpAlertDialogWhenStartTask();



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

    private void initVariables() {

        listWithData = (ListView) findViewById(R.id.listView);
        arrayListToGetData = new ArrayList<String>();
        arrayListToGetData = getIntent().getStringArrayListExtra("lista");
        chronometer = (Chronometer) findViewById(R.id.chronometerr);
        textView = (TextView) findViewById(R.id.zzz);
        stopCountingButton = (Button) findViewById(R.id.stopButton);


    }

    private void setListWithData() {

        arrayAdapter = new ArrayAdapter<String>(SecondActivity.this,
                android.R.layout.simple_list_item_1, arrayListToGetData);
        listWithData.setAdapter(arrayAdapter);
    }

    private void setListWithData2() {
        ItemToDoApp object = new ItemToDoApp("pierwszy", "10:10", R.drawable.lets_do_it);
        ItemToDoApp object2 = new ItemToDoApp("drufi to i tamto xsratarasadasdsadsadawdawdcxzcxzcasawcwadwasdxcxzvdscsaca", "00:10", R.drawable.lets_do_it);
        ItemToDoApp object3 = new ItemToDoApp("dhasda saduas csacsacsacsacascsacsacsacsadsahdsagdcgsacdgsacgdcsgacdgsacgdcsagdcgsa", "10", R.drawable.ok_man);
        ArrayList<ItemToDoApp> itemToDoAppsList = new ArrayList<>();
        itemToDoAppsList.add(0, object);
        itemToDoAppsList.add(1, object2);
        itemToDoAppsList.add(2, object3);

        listViewHolder = new ListViewHolder(SecondActivity.this, R.layout.lis_view_record, itemToDoAppsList);

        listWithData.setAdapter(listViewHolder);
    }

    private void setListWithDataCursor() {

        DataBaseToDo dataBaseToDo = new DataBaseToDo(this);
        Cursor c = dataBaseToDo.display("ToDo");
        CursorAdapterToDo cursorAdapterToDo = new CursorAdapterToDo(this, c, 0);
        listWithData.setAdapter(cursorAdapterToDo);

    }

    private static int getSecondsFromDurationString(String value) {

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

        dataBaseToDo = new DataBaseToDo(SecondActivity.this);

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

        dataBaseToDo = new DataBaseToDo(SecondActivity.this);

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

                boolean isStaring = false;
                isStartingTask(isStaring);
                Toast.makeText(getApplicationContext(), R.string.taskNotRunning, Toast.LENGTH_SHORT).show();
            }


        });

        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                boolean isStaring = true;
                isStartingTask(isStaring);

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

        Intent intent = new Intent(SecondActivity.this, SecondActivity.class);
        startActivity(intent);

    }

    private void deleteRowFromDataBase(long id) {

        dataBaseToDo = new DataBaseToDo(this);
        dataBaseToDo.deleteRecord(id);
    }


    private void getSumColumnTimeInteger()
    {
        dataBaseToDo = new DataBaseToDo(this);
        int sumColumnTimeInteger = dataBaseToDo.sumColumn();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);
        String time = df.format(new Date(sumColumnTimeInteger*1000L));


        textView.setText(String.valueOf(time));

    }

    private boolean isStartingTask(boolean isStarting){


        return isStarting;
    }


}
