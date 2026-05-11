package com.example.agendanusantara;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DaftarTugasActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabase myDB;
    ArrayList<String> id_tugas, judul, tanggal, kategori;
    TugasAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daftar_tugas);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerViewTugas);
        myDB = new MyDatabase(this);

        // Siapkan keranjang Array kosong
        id_tugas = new ArrayList<>();
        judul = new ArrayList<>();
        tanggal = new ArrayList<>();
        kategori = new ArrayList<>();

        // Panggil data dari SQLite
        tampilkanData();

        // Hubungkan Array dengan Adapter dan pasang ke RecyclerView
        customAdapter = new TugasAdapter(this, id_tugas, judul, tanggal, kategori);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void tampilkanData() {
        Cursor cursor = myDB.readSemuaTugas();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Tidak ada data tugas.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                // Indeks 0=id, 1=judul, 2=deskripsi, 3=tanggal, 4=kategori, 5=status
                id_tugas.add(cursor.getString(0));
                judul.add(cursor.getString(1));
                tanggal.add(cursor.getString(3));
                kategori.add(cursor.getString(4));
            }
        }
    }
}