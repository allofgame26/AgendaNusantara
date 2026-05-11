package com.example.agendanusantara;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class TugasBiasaActivity extends AppCompatActivity {

    EditText inputJudul, inputDeskripsi, inputTanggal;
    Button tombolSimpan;
    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tugas_biasa);

        inputJudul = findViewById(R.id.input_judul1);
        inputDeskripsi = findViewById(R.id.input_deskripsi1);
        inputTanggal = findViewById(R.id.input_tanggal1);
        tombolSimpan = findViewById(R.id.btn_simpan1);
        db = new MyDatabase(this);

        inputTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TugasBiasaActivity.this,
                        (view, year1, monthOfYear, dayOfMonth) -> {
                            // Format: Hari-Bulan-Tahun
                            inputTanggal.setText(dayOfMonth + " " + (monthOfYear + 1) + " " + year1);
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        tombolSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul = inputJudul.getText().toString().trim();
                String desk = inputDeskripsi.getText().toString().trim();
                String tgl = inputTanggal.getText().toString().trim();

                if (judul.isEmpty() || tgl.isEmpty()) {
                    Toast.makeText(TugasBiasaActivity.this, "Judul dan Tanggal wajib diisi!", Toast.LENGTH_SHORT).show();
                } else {
                    // Panggil fungsi database, kategori disetel manual ke "Biasa"
                    db.addTugas(judul, desk, tgl, "Biasa");
                    finish(); // Kembali ke Beranda
                }
            }
        });
    }
}