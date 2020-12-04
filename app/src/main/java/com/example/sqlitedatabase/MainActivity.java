package com.example.sqlitedatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler mydb;
    private EditText editname;
    private EditText editsurname;
    private EditText editmarks;
    private EditText editId;
    private Button btnadd;
    private Button btnViewAll;
    private Button btnUpdate;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DatabaseHandler(this);

        editname = (EditText) findViewById(R.id.editText1);
        editsurname = (EditText) findViewById(R.id.editText2);
        editmarks = (EditText) findViewById(R.id.editText3);
        editId = (EditText) findViewById(R.id.editText4);
        btnadd = (Button) findViewById(R.id.button);
        btnViewAll = (Button) findViewById(R.id.button1);
        btnUpdate = (Button) findViewById(R.id.button2);
        btnDelete = (Button) findViewById(R.id.button3);

        AddData();
        viewAll();
        addData();
        deleteData();
    }

    public void deleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = mydb.deleteData(editId.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addData() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = mydb.updateData(editId.getText().toString(), editname.getText().toString(), editsurname.getText().toString(),
                        editmarks.getText().toString());
                if (isUpdate == true)
                    Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void AddData() {
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = mydb.insertData(editname.getText().toString(), editsurname.getText().toString(), editmarks.getText().toString());
                if (isInserted = true)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getAllData();
                if (res.getCount() == 0) {
                    showMessage("error", "No found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (res.moveToNext()) {
                    stringBuffer.append("ID :" + res.getString(0) + "\n");
                    stringBuffer.append("NAME :" + res.getString(1) + "\n");
                    stringBuffer.append("SURNAME :" + res.getString(2) + "\n");
                    stringBuffer.append("MARKS :" + res.getString(3) + "\n");
                }
                showMessage("Data", stringBuffer.toString());
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}