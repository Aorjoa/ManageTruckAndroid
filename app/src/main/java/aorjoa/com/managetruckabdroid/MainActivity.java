package aorjoa.com.managetruckabdroid;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button login;
    DBHelper dbSqlite;
    List<String> records;
    String recorderLogedIn;
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
            EditText edt = (EditText) findViewById(R.id.usernameInput);
            String username = edt.getText().toString();
            edt = (EditText) findViewById(R.id.passwordInput);
            String password = edt.getText().toString();
            recorderLogedIn = dbSqlite.checkLogin(username, password);
            if (recorderLogedIn != null) {
                setContentView(R.layout.main_menu);
            } else {
                alertMsg("ไม่สามารถ Login ได้!");
            }
    }

    public void checkGoToMainMenu(View view) {
            if(recorderLogedIn == null){
                setContentView(R.layout.activity_main);
            }else{
                setContentView(R.layout.main_menu);
            }
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

    public void exitApp(View view){
        System.exit(0);
    }

    public void gotoPaidMoreTrasaction(View view){
        setContentView(R.layout.paid_transaction);
        String[] lists = {""};

        final Button searchRecordForPaid = (Button) findViewById(R.id.searchRecordForPaid);
        searchRecordForPaid.setOnClickListener(new View.OnClickListener() {
                                                   public void onClick(View v) {
                                                       EditText edt = null;
                                                       edt = (EditText)findViewById(R.id.recordNo);
                                                       String recordNo = edt.getText().toString();
                                                       adapterForPaidMore(recordNo);
                                                   }
                                               });
        final Button savePaidMore = (Button) findViewById(R.id.savePaidMore);
        savePaidMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    TextView recordNotLbl = (TextView) findViewById(R.id.recordNoLbl);
                    String lblNowStatus = recordNotLbl.getText().toString();
                    if (!lblNowStatus.equals("ค้นหา") || !lblNowStatus.equals("") || !lblNowStatus.equals(" ")) {
                        EditText edt = (EditText) findViewById(R.id.paidMore);
                        String paidMore = edt.getText().toString();
                        TextView txtDate = (TextView) findViewById(R.id.dateOpForTransaction);
                        String dateop = txtDate.getText().toString();
                        if (verifyDate(dateop)) {
                            dbSqlite.paidMoreTransaction(lblNowStatus, paidMore, dateop, recorderLogedIn);
                            alertMsg("บันทึกรายการชำระเงินเรียบร้อย");
                            edt.setText("");
                        }
                    }
                }catch (Exception e){
                alertMsg("พบข้อผิดพลาด กรุณาตรวจสอบข้อมูลให้ถูกต้อง");
            }
            }
        });
    }

    public void adapterForPaidMore(String recordNo){
        records = dbSqlite.getTransaction(recordNo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, records);
        ListView listView = (ListView) findViewById(R.id.ListViewPaid);
        if (adapter.getCount() > 0) {
            listView.setAdapter(adapter);
            String _recordNoFromDB = records.get(0).split(" ")[1];
            TextView recordNotLbl = (TextView) findViewById(R.id.recordNoLbl);
            recordNotLbl.setText(_recordNoFromDB);
            String costRemain = dbSqlite.getCostRemain(_recordNoFromDB);
            TextView costLbl = (TextView) findViewById(R.id.remainPaidLbl);
            costLbl.setText(costRemain);
        }
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
        final Button calPrice = (Button) findViewById(R.id.calPrice);
        calPrice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               Record record = createRecord();
               calculatePriceAllInput(record);
            }
        });
        final Button saveAddRecord = (Button) findViewById(R.id.saveAddRecord);
        saveAddRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    RadioButton paidFull = (RadioButton) findViewById(R.id.paidFull);
                    RadioButton paidPart = (RadioButton) findViewById(R.id.paidPart);
                    EditText dateopEdt = (EditText) findViewById(R.id.dateOp);
                    String dateOp = dateopEdt.getText().toString();
                    Record record = createRecord();
                    calculatePriceAllInput(record);
                    if(verifyDate(dateOp)) {
                        record = setPriceAndRecorderForRecord(record);
                        dbSqlite.addRecordToDb(record);

                        if (paidFull.isChecked()) {
                            EditText edt = (EditText) findViewById(R.id.prices);
                            dbSqlite.addNewRecordTransaction(edt.getText().toString(), dateOp, recorderLogedIn);
                        } else if (paidPart.isChecked()) {
                            EditText edt = (EditText) findViewById(R.id.paidPartPrices);
                            dbSqlite.addNewRecordTransaction(edt.getText().toString(), dateOp, recorderLogedIn);
                        } else {
                            dbSqlite.addNewRecordTransaction("0", dateOp, recorderLogedIn);
                        }

                        alertMsg("บันทึกข้อมูลเรียบร้อยแล้ว");
                        clearAfterAddRecord();
                    }
                }catch (Exception e){
                    alertMsg("พบข้อผิดพลาด กรุณาตรวจสอบข้อมูลให้ถูกต้อง");
                }
            }
        });
    }

    private boolean verifyDate(String input){
        boolean checkFormat = false;
        if (input.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})"))
            checkFormat=true;
        else
            alertMsg("ตรวจสอบวันที่ให้ถูกต้อง");
        return checkFormat;
    }

    private boolean calculatePriceAllInput(Record record) {
        boolean checkInPutOK =false;
        try {
            Double hvArea = Double.parseDouble(record.getHvArea());
            Double hvAreaPrice = Double.parseDouble(record.getHvPriceArea());
            Double bhHour = Double.parseDouble(record.getBhHours());
            Double bhPriceHour = Double.parseDouble(record.getBhPriceHours());
            Double trNum = Double.parseDouble(record.getTrNum());
            Double trPriceNum = Double.parseDouble(record.getTrPriceNum());
            Double ttNum = Double.parseDouble(record.getTtNum());
            Double ttPriceNum = Double.parseDouble(record.getTtPriceNum());
            EditText edt = (EditText)findViewById(R.id.prices);
            edt.setText(""+(hvArea*hvAreaPrice + bhHour*bhPriceHour + trNum*trPriceNum + ttNum*ttPriceNum));
            checkInPutOK =true;
        }catch (Exception e){
            alertMsg("กรุณากรอบข้อมูลให้ถูกต้อง");
        }
        return checkInPutOK;
    }

    public void alertMsg(String msg){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("โปรดทราบ");
        dialog.setMessage(msg);
        dialog.setPositiveButton("เข้าใจแล้ว", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        final AlertDialog alert = dialog.create();
        alert.show();
    }

    // 2.0 and above
    @Override
    public void onBackPressed()
    {
        checkGoToMainMenu(null);
    }

    // Before 2.0
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            checkGoToMainMenu(null);
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

        return new Record(ctId,ctName,ctPhone,ctAddress,dateOp,hvName,hvArea,hvPriceArea,hvAddress,bhName,bhHours,bhPriceHours,trName,trNum,trPriceNum,ttName,ttNum,ttPriceNum,null,null);
    }

    public Record setPriceAndRecorderForRecord(Record record){
        EditText edt = (EditText)findViewById(R.id.prices);
        String price = edt.getText().toString();
        record.setPrice(price);
        record.setRecorder("admin");
        return record;
    }

    public void clearAfterAddRecord(){
        EditText edt = null;
        edt = (EditText)findViewById(R.id.ctId);
        edt.setText("");
        edt = (EditText)findViewById(R.id.ctName);
        edt.setText("");
        edt = (EditText)findViewById(R.id.ctPhone);
        edt.setText("");
        edt = (EditText)findViewById(R.id.ctAddress);
        edt.setText("");
        edt = (EditText)findViewById(R.id.dateOp);
        edt.setText("");
        edt = (EditText)findViewById(R.id.hvName);
        edt.setText("");
        edt = (EditText)findViewById(R.id.hvArea);
        edt.setText("");
        edt = (EditText)findViewById(R.id.hvPriceArea);
        edt.setText("");
        edt = (EditText)findViewById(R.id.hvAddress);
        edt.setText("");
        edt = (EditText)findViewById(R.id.bhName);
        edt.setText("");
        edt = (EditText)findViewById(R.id.bhHours);
        edt.setText("");
        edt = (EditText)findViewById(R.id.bhPriceHours);
        edt.setText("");
        edt = (EditText)findViewById(R.id.trName);
        edt.setText("");
        edt = (EditText)findViewById(R.id.trNum);
        edt.setText("");
        edt = (EditText)findViewById(R.id.trPriceNum);
        edt.setText("");
        edt = (EditText)findViewById(R.id.ttName);
        edt.setText("");
        edt = (EditText)findViewById(R.id.ttNum);
        edt.setText("");
        edt = (EditText)findViewById(R.id.ttPriceNum);
        edt.setText("");
        edt = (EditText)findViewById(R.id.prices);
        edt.setText("");
        edt = (EditText)findViewById(R.id.paidPartPrices);
        edt.setText("");
    }
}