package aorjoa.com.managetruckabdroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addListenerOnLogin(){

    }

    public void gotoMainMenu(View view){
        setContentView(R.layout.main_menu);
    }

    public void gotoAddNewRecord(View view){
        setContentView(R.layout.add_new_record);
    }
}
