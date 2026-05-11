package com.example.agendanusantara;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView sapaan, tanggal;
    CardView tombolPenting, tombolBiasa, tombolData, tombolPengaturan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        String namaUser = getIntent().getStringExtra("nama_user");
        if (namaUser == null){
            namaUser = "User";
        }

        tombolPenting = findViewById(R.id.btn_tambah_penting);
        tombolBiasa = findViewById(R.id.btn_tambah_biasa);
        tombolData = findViewById(R.id.btn_daftar_tugas);
        tombolPengaturan = findViewById(R.id.btn_pengaturan);
        sapaan = findViewById(R.id.textSapaan);
        tanggal = findViewById(R.id.textTanggal);

        String TanggalSekarang = getTanggal();
        tanggal.setText(TanggalSekarang);
        sapaan.setText("Selamat Datang, " + namaUser + "! 👋");

        tombolPenting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TugasPentingActivity.class);
                startActivity(intent);
            }
        });

        tombolBiasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TugasBiasaActivity.class);
                startActivity(intent);
            }
        });

        tombolData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DaftarTugasActivity.class);
                startActivity(intent);
            }
        });

        tombolPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PengaturanActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getTanggal(){
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE, d MMMM yyyy", new java.util.Locale("id", "ID"));
        return sdf.format(new java.util.Date());
    }
}