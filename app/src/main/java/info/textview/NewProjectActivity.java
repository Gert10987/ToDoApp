package info.textview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import info.textview.DataBase.DataBaseToDo;

/**
 * Created by Paweł on 2016-05-31.
 */
public class NewProjectActivity extends Activity {

    public static final String NAME_OF_PROJECT_EXIST = "info.textview.NameOfProjectExist";

    public static final String NAME_OF_PROJECT_CREATE = "info.textview.NameOfProjectCreate";

    public static final String ANDROID_METADA = "android_metadata";

    GridView gridView;
    Button button;
    EditText editText;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        initVariables();
        getNamesOfTables();

        setUpAdapter();
        setUpGridView();
    }



    private ArrayAdapter setUpAdapter() {

        deleteBasicMetaDataTableFromArrayList();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, arrayList);

        return arrayAdapter;
    }

    private void getNamesOfTables() {

        DataBaseToDo dataBaseToDo = new DataBaseToDo(this);
        arrayList = dataBaseToDo.getNameOfTables();


    }

    private void setUpGridView() {

        gridView.setAdapter(setUpAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String nameOfProjectFromGridView = (String) parent.getItemAtPosition(position);
                movesToAnotherActivity(nameOfProjectFromGridView, NAME_OF_PROJECT_EXIST);
            }
        });

    }

    private void initVariables() {

        gridView = (GridView) findViewById(R.id.gridViewNewProject);
        button = (Button) findViewById(R.id.buttonNewProject);
        editText = (EditText) findViewById(R.id.editTextNewProject);
        arrayList = new ArrayList<>();

    }


    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonNewProject:

                try{
                    createNewProject();

                }catch (Exception e){
                    e.printStackTrace();
                }

                break;

        }

    }

    private void createNewProject() {

        movesToAnotherActivity(getNameFromEditText(), NAME_OF_PROJECT_CREATE);
    }

    private String getNameFromEditText() {

        String nameOfNewProject = editText.getText().toString();

        for(int i = 0; i < arrayList.size(); i++){

            if(arrayList.get(i).matches(nameOfNewProject)){

                Toast.makeText(getApplicationContext(), R.string.thsiNameIsBusy, Toast.LENGTH_SHORT).show();
                break;
            }

        }

        return nameOfNewProject;
    }

    private void movesToAnotherActivity(String name, String Tag) {

        Intent intent = new Intent(NewProjectActivity.this, MainActivity.class);
        intent.putExtra(Tag, name);
        startActivity(intent);
    }

    private void deleteBasicMetaDataTableFromArrayList(){

        for(int i = 0; i < arrayList.size(); i++){

            Log.d("XXX", arrayList.get(i));

            if(arrayList.get(i).matches(ANDROID_METADA)){

                arrayList.remove(i);
            }

        }

    }



}
