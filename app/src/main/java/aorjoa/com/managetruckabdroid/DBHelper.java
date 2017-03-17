package aorjoa.com.managetruckabdroid;

/**
 * Created by AorJoa on 16/3/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "sqlite.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRecordTable = "create table if not exists records (recordId text, checkSync int, ctId text, ctName text, ctPhone text, ctAddress text, dateOp text, hvName text, hvArea int, hvPriceArea int, hvAddress text, bhName text, bhHours int, bhPriceHours int, trName text, trNum int, trPriceNum int, ttName text, ttNum int, ttPriceNum int, price int, recorder text)";
        db.execSQL(createRecordTable);
        String addFirstRowForTest = "insert into records (recordId) select ('') where not exists (select * from records)";
        db.execSQL(addFirstRowForTest);
        String createTableTransaction = "create table if not exists transactions (recordId text, pay int, recordDate text, recorder text)";
        db.execSQL(createTableTransaction);
        String createTableMember = "create table if not exists members (mbUsername text, mbName text, mbPassword text, unique (mbUsername))";
        db.execSQL(createTableMember);
        String createAdmin = "insert or ignore into members (mbUsername, mbName, mbPassword) values ('admin','admin','0B143597BAC95DA7BF4696A41D8C19CA')";
        db.execSQL(createAdmin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addRecordToDb(Record data){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement insertCmd;

        String strSQL = "insert into records (recordId, checkSync, ctId, ctName, ctPhone, ctAddress, dateOp, hvName, hvArea, hvPriceArea, hvAddress, " +
                "bhName, bhHours, bhPriceHours, trName, trNum , trPriceNum, ttName, ttNum, ttPriceNum, price, recorder) select printf('M%s',ROWID),0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? "+
                "from records order by ROWID desc limit 1";
        insertCmd = db.compileStatement(strSQL);
        insertCmd.bindString(1, data.getCtId());
        insertCmd.bindString(2, data.getCtName());
        insertCmd.bindString(3, data.getCtPhone());
        insertCmd.bindString(4, data.getCtAddress());
        insertCmd.bindString(5, data.getDateOp());
        insertCmd.bindString(6, data.getHvName());
        insertCmd.bindString(7, data.getHvArea());
        insertCmd.bindString(8, data.getHvPriceArea());
        insertCmd.bindString(9, data.getHvAddress());
        insertCmd.bindString(10, data.getBhName());
        insertCmd.bindString(11, data.getBhHours());
        insertCmd.bindString(12, data.getBhPriceHours());
        insertCmd.bindString(13, data.getTrName());
        insertCmd.bindString(14, data.getTrNum());
        insertCmd.bindString(15, data.getTrPriceNum());
        insertCmd.bindString(16, data.getTtName());
        insertCmd.bindString(17, data.getTtNum());
        insertCmd.bindString(18, data.getTrPriceNum());
        insertCmd.bindString(19, data.getPrice());
        insertCmd.bindString(20, data.getRecorder());
        insertCmd.executeInsert();
    }

    public void addNewRecordTransaction(String paid,String date,String recorder){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement insertCmd;

        String strSQL = "insert into transactions (recordId,pay,recordDate,recorder) select recordId,?,?,? from records order by ROWID desc limit 1";
        insertCmd = db.compileStatement(strSQL);
        insertCmd.bindString(1, paid);
        insertCmd.bindString(2, date);
        insertCmd.bindString(3, recorder);
        insertCmd.executeInsert();
    }

    public void paidMoreTransaction(String record,String paid,String date,String recorder){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement insertCmd;

        String strSQL = "insert into transactions (recordId,pay,recordDate,recorder) select ?,?,?,? from records order by ROWID desc limit 1";
        insertCmd = db.compileStatement(strSQL);
        insertCmd.bindString(1, record);
        insertCmd.bindString(2, paid);
        insertCmd.bindString(3, date);
        insertCmd.bindString(4, recorder);
        insertCmd.executeInsert();
    }

    public List<String> getRecordList(String ctId,String ctName) {
        List<String> records = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select recordId as เลขที่รายการ, ctId as รหัสลูกค้า, ctName as ชื่อลูกค้า, ctPhone as เบอร์โทร, ctAddress as ที่อยู่, dateOp as วันเวลา, " +
                "hvName as คนเกี่ยว, hvArea as จำนวนไร่, hvPriceArea as ไร่ละ, hvAddress as เกี่ยวที่, bhName as คนขับแบคโฮ, bhHours as ชั่วโมว, bhPriceHours as ชั่วโมงละ," +
                " trName as คนขับสิบล้อ, trNum as จำนวนรอบ, trPriceNum as เกรียนละ, ttName as คนขับแทรกเตอร์, ttNum as _จำนวนรอบ, ttPriceNum as _เกรียนละ, price as ราคารวม, " +
                "recorder as คนบันทึก from records where ctId like %s and ctName like %s";
        query = String.format(query, "\"%"+ctId + "%\"","\"%"+ctName + "%\"");
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {

            records.add(cursor.getString(0) + " " +
                    cursor.getString(1) + " " +
                    cursor.getString(2) + " " +
                    cursor.getString(3) + " " +
                    cursor.getString(4) + " " +
                    cursor.getString(5) + " " +
                    cursor.getString(6) + " " +
                    cursor.getString(7) + " " +
                    cursor.getString(8) + " " +
                    cursor.getString(9) + " " +
                    cursor.getString(10) + " " +
                    cursor.getString(11) + " " +
                    cursor.getString(12) + " " +
                    cursor.getString(13) + " " +
                    cursor.getString(14) + " " +
                    cursor.getString(15) + " " +
                    cursor.getString(16) + " " +
                    cursor.getString(17) + " " +
                    cursor.getString(18) + " " +
                    cursor.getString(19) + " " +
                    cursor.getString(20) + " "
            );

            cursor.moveToNext();
        }

        db.close();

        return records;
    }



    public List<String> getTransaction(String recordNo) {
        List<String> records = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select ROWID as เลขที่ชำระเงิน, recordId as เลขที่รายการ, pay as จำนวนเงินที่จ่าย, recordDate as วันที่จ่าย, recorder as คนบันทึก from transactions where recordId like %s";
        query = String.format(query, "\"%"+recordNo + "%\"");
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {

            records.add(cursor.getString(0) + " " +
                    cursor.getString(1) + " " +
                    cursor.getString(2) + " " +
                    cursor.getString(3) + " " +
                    cursor.getString(4) + " "
            );

            cursor.moveToNext();
        }

        db.close();

        return records;
    }

    public String getCostRemain(String recordNo) {
        String price = "";

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select((sum(price) / count(price)) - sum(pay)) from (select records.recordId, price, pay from records left join transactions on records.recordId = transactions.recordId) where recordId = %s group by recordId";
        query = String.format(query, "'"+recordNo + "'");
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            price = cursor.getString(0);
            cursor.moveToNext();
        }

        db.close();
        return price;
    }
}
