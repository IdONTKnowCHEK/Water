package niu.edu.water;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class drinkLog extends AppCompatActivity {

    private ArrayList<String> logLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_log);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
// MemberAdapter 會在步驟7建立

//        logLists = (ArrayList<String>) getIntent().getSerializableExtra("LogList");
        List<String> environmentList = new ArrayList<String>();
        SharedPreferences preferDataList = getSharedPreferences("sp", MODE_PRIVATE);
        int environNums = preferDataList.getInt("ReasonNums", 0);
        Log.i("@@@@@@@@@@@@", String.valueOf(environNums));
        for (int i = 0; i < environNums; i++)
        {
            String environItem = preferDataList.getString("item_"+i, null);
            environmentList.add(environItem);
            Log.i("@@@@@@@@@@@@", String.valueOf(environmentList.get(i)));

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new LogAdapter(this, environmentList));
    }
}