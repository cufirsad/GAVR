package com.example.sanyagavrsam;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView userList;
    ArrayAdapter<Workers> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = findViewById(R.id.list);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long id) {
                Workers workers = arrayAdapter.getItem(position);
                Intent intent = null;
                if (workers != null) {
                    intent = new Intent(getApplicationContext(),
                            WorkActivity.class);
                }
                intent.putExtra("ID", workers.getId());
                startActivity(intent);
            }
        });
    }
    public void showDialog(View v) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }


    @Override
    public void onResume() { super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this); adapter.open();
        List<Workers> workers = adapter.getUsers();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, workers);
        userList.setAdapter(arrayAdapter); adapter.close();
    }
    public void add(View view){
        Intent intent = new Intent(this, WorkActivity.class);
        startActivity(intent);
    }
}