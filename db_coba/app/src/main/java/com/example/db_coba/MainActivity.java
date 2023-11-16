package com.example.db_coba;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText nrp,nama;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper Opendb;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nrp = (EditText)findViewById(R.id.nrp);
        nama= (EditText) findViewById(R.id.nama);
        Button simpan = (Button) findViewById(R.id.Simpan);
        Button ambildata = (Button) findViewById(R.id.ambildata);
        Button tampil = (Button) findViewById(R.id.tampil);
        Button update = (Button) findViewById(R.id.update);
        Button delete = (Button) findViewById(R.id.delete);
        simpan.setOnClickListener(operasi);
        ambildata.setOnClickListener(operasi);
        tampil.setOnClickListener(operasi);
        update.setOnClickListener(operasi);
        delete.setOnClickListener(operasi);

        Opendb = new SQLiteOpenHelper(this,"db.sql",null,1) {
        @Override
        public void onCreate(SQLiteDatabase db) {}
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    };
    dbku = Opendb.getWritableDatabase();
    dbku.execSQL("create table if not exists mhs(nrp TEXT, nama TEXT);");
}
    @Override
    protected void onStop(){
        dbku.close();
        Opendb.close();
        super.onStop();

    }

    @SuppressLint("NonConstantResourceId")
    View.OnClickListener operasi = v -> {
        int viewId = v.getId();
        if (viewId == R.id.Simpan) {
            simpan();
        } else if (viewId == R.id.ambildata) {
            ambildata();
        } else if (viewId == R.id.tampil) {
            tampildata();
        } else if (viewId == R.id.update){
            update();
        } else if (viewId == R.id.delete){
            delete();
        }
    };

    private void simpan()
    {
        ContentValues dataku = new ContentValues();

        dataku.put("nrp",nrp.getText().toString());
        dataku.put("nama",nama.getText().toString());
        dbku.insert("mhs",null,dataku);
        Toast.makeText(this,"Data Tersimpan",Toast.LENGTH_LONG).show();
    }

    private void update()
    {
        ContentValues dataku = new ContentValues();

        dataku.put("nrp",nrp.getText().toString());
        dataku.put("nama",nama.getText().toString());
        dbku.update("mhs",dataku, "nrp = ?", new String[]{nrp.getText().toString()});
        Toast.makeText(this,"Data Update",Toast.LENGTH_LONG).show();
    }

    private void delete()
    {
        ContentValues dataku = new ContentValues();

        dataku.put("nrp",nrp.getText().toString());
        dataku.put("nama",nama.getText().toString());
        dbku.delete("mhs","nrp = ?", new String[]{nrp.getText().toString()});
        Toast.makeText(this,"Data Terdelete",Toast.LENGTH_LONG).show();
    }
    @SuppressLint("Range")
    private void ambildata(){
        @SuppressLint("Recycle") Cursor cur = dbku.rawQuery("select * from mhs where nrp='" +
        nrp.getText().toString()+ "'",null);

        if(cur.getCount() >0)
        {
            StringBuilder data = new StringBuilder();
            Toast.makeText(this,"Data Ditemukan Sejumlah " +
            cur.getCount(),Toast.LENGTH_LONG).show();
            cur.moveToFirst();
            do {
                String nrpValue = cur.getString(cur.getColumnIndex("nrp"));
                String namaValue = cur.getString(cur.getColumnIndex("nama"));
                data.append("NRP: ").append(nrpValue).append(", Nama: ").append(namaValue).append("\n");
            } while (cur.moveToNext());

            TextView dataTextView = findViewById(R.id.dataTextView);
            dataTextView.setText(data.toString());
            //nama.setText(cur.getString(cur.getColumnIndex("nama")));
        }
        else {
            TextView dataTextView = findViewById(R.id.dataTextView);
            dataTextView.setText("Data Tidak Ditemukan");
            Toast.makeText(this, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
        }
    }

    private void tampildata(){
       Cursor cur = dbku.rawQuery("select * from mhs",null);

        if(cur.getCount() >0)
        {
            StringBuilder data = new StringBuilder();
            Toast.makeText(this,"Data Ditemukan Sejumlah " +
                    cur.getCount(),Toast.LENGTH_LONG).show();
            cur.moveToFirst();
            do {
                String nrpValue = cur.getString(cur.getColumnIndex("nrp"));
                String namaValue = cur.getString(cur.getColumnIndex("nama"));
                data.append("NRP: ").append(nrpValue).append(", Nama: ").append(namaValue).append("\n");
            } while (cur.moveToNext());

            TextView dataTextView = findViewById(R.id.dataTextView);
            dataTextView.setText(data.toString());
        }
        else {
            TextView dataTextView = findViewById(R.id.dataTextView);
            dataTextView.setText("Data Tidak Ditemukan");
            Toast.makeText(this, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
        }
    }
}
