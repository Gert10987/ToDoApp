package info.textview;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.textview.DataBase.DataBaseToDo;


public class SecondActivity extends AppCompatActivity {

    ListView listWithData;
    ArrayAdapter<String> arrayAdapter;
    ListViewHolder listViewHolder;
    ArrayList<String> arrayListToGetData;
    Chronometer chronometer;
    TextView textView;
    Button stopCountingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initVariables();
        setListWithData2();
        listViewListener();
        buttonViewListner();


    }

    private void listViewListener() {

        listWithData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(listWithData.isEnabled()){

                    chronometrStartCounting();


                    listWithData.setEnabled(false);
                }else{

                    Toast.makeText(getApplicationContext(), R.string.ListViewIsEnabled, Toast.LENGTH_SHORT).show();

                }



            }
        });


    }


    private void buttonViewListner(){

        stopCountingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chronometerStopCounting();
                listWithData.setEnabled(true);
            }
        });
    }

    private void chronometerStopCounting() {

        chronometer.stop();
        String chronometrValue = chronometer.getText().toString();
        textView.setText(chronometrValue);

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

    private void setListWithData2(){
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

    private void setListWithDataCursor(){

        DataBaseToDo dataBaseToDo = new DataBaseToDo(this);
        dataBaseToDo.wyswietl("Table");

    }

    public static int getSecondsFromDurationString(String value){

        String [] parts = value.split(":");

        // Wrong format, no value for you.
        if(parts.length < 2 || parts.length > 3)
            return 0;

        int seconds = 0, minutes = 0, hours = 0;

        if(parts.length == 2){
            seconds = Integer.parseInt(parts[1]);
            minutes = Integer.parseInt(parts[0]);
        }
        else if(parts.length == 3){
            seconds = Integer.parseInt(parts[2]);
            minutes = Integer.parseInt(parts[1]);
            hours = Integer.parseInt(parts[1]);
        }

        return seconds + (minutes*60) + (hours*3600);
    }


}
