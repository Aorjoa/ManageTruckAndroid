package aorjoa.com.managetruckabdroid;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button login;
    DBHelper dbSqlite;
    List<String> records;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbSqlite = new DBHelper(this);
        dbSqlite.getWritableDatabase();
    }

    public void addListenerOnLogin(){

    }

    public void gotoMainMenu(View view){
        setContentView(R.layout.main_menu);
    }

    public void gotoSearch(View view){
        setContentView(R.layout.list_search);
        final Button searchPerform = (Button) findViewById(R.id.searchPerform);
        searchPerform.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText edt = null;
                edt = (EditText)findViewById(R.id.ctIdSearch);
                String ctId = edt.getText().toString();
                edt = (EditText)findViewById(R.id.ctNameSearch);
                String ctName = edt.getText().toString();
                adapterCall(ctId,ctName);
            }
        });
    }


    public void adapterCall(String ctId,String ctName){
        String[] lists = {""};
        records = dbSqlite.getRecordList(ctId,ctName);
        setContentView(R.layout.list_records);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, records);
        ListView listView = (ListView) findViewById(R.id.recordsListView);
        listView.setAdapter(adapter);
    }

    public void gotoAddNewRecord(View view){
        setContentView(R.layout.add_new_record);
        final Button saveAddRecord = (Button) findViewById(R.id.savAddRecord);
        saveAddRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dbSqlite.addRecordToDb(createRecord());
            }
        });
    }


    // 2.0 and above
    @Override
    public void onBackPressed() {
        gotoMainMenu(null);
    }

    // Before 2.0
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            gotoMainMenu(null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public Record createRecord(){
        EditText edt = null;
        edt = (EditText)findViewById(R.id.ctId);
        String ctId = edt.getText().toString();
        edt = (EditText)findViewById(R.id.ctName);
        String ctName = edt.getText().toString();
        edt = (EditText)findViewById(R.id.ctPhone);
        String ctPhone = edt.getText().toString();
        edt = (EditText)findViewById(R.id.ctAddress);
        String ctAddress = edt.getText().toString();
        edt = (EditText)findViewById(R.id.dateOp);
        String dateOp = edt.getText().toString();
        edt = (EditText)findViewById(R.id.hvName);
        String hvName = edt.getText().toString();
        edt = (EditText)findViewById(R.id.hvArea);
        String hvArea = edt.getText().toString();
        edt = (EditText)findViewById(R.id.hvPriceArea);
        String hvPriceArea = edt.getText().toString();
        edt = (EditText)findViewById(R.id.hvAddress);
        String hvAddress = edt.getText().toString();
        edt = (EditText)findViewById(R.id.bhName);
        String bhName = edt.getText().toString();
        edt = (EditText)findViewById(R.id.bhHours);
        String bhHours = edt.getText().toString();
        edt = (EditText)findViewById(R.id.bhPriceHours);
        String bhPriceHours = edt.getText().toString();
        edt = (EditText)findViewById(R.id.trName);
        String trName = edt.getText().toString();
        edt = (EditText)findViewById(R.id.trNum);
        String trNum = edt.getText().toString();
        edt = (EditText)findViewById(R.id.trPriceNum);
        String trPriceNum = edt.getText().toString();
        edt = (EditText)findViewById(R.id.ttName);
        String ttName = edt.getText().toString();
        edt = (EditText)findViewById(R.id.ttNum);
        String ttNum = edt.getText().toString();
        edt = (EditText)findViewById(R.id.ttPriceNum);
        String ttPriceNum = edt.getText().toString();
        edt = (EditText)findViewById(R.id.price);
        String price = edt.getText().toString();
        return new Record(ctId,ctName,ctPhone,ctAddress,dateOp,hvName,hvArea,hvPriceArea,hvAddress,bhName,bhHours,bhPriceHours,trName,trNum,trPriceNum,ttName,ttNum,ttPriceNum,price,"admin");
    }
}