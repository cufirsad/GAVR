package com.example.sanyagavrsam;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WorkActivity extends AppCompatActivity {
    private EditText nameBox;
    private EditText yearBox;
    private Button delButton;
    private DatabaseAdapter adapter;
    private long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        nameBox = findViewById(R.id.name);
        yearBox = findViewById(R.id.year);
        delButton = findViewById(R.id.deleteButton);
        adapter = new DatabaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("ID");
        }
        if (userId > 0) {
            adapter.open();
            Workers workers = adapter.getUser(userId);
            nameBox.setText(workers.getName());
            yearBox.setText(String.valueOf(workers.getYear()));

            adapter.close();
        } else {
            delButton.setVisibility(View.GONE);
        }
    }
    public void save(View view){
        String name = nameBox.getText().toString();
        int year = Integer.parseInt(yearBox.getText().toString());
        Workers workers = new Workers(userId, name, year);

        adapter.open();
        if (userId > 0) {
            adapter.update(workers);
        } else {
            adapter.insert(workers);
        }
        adapter.close();
        goHome();
    }
    public void delete(View view){
        adapter.open();
        adapter.delete(userId);
        adapter.close();
        goHome();
    }
    private void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}