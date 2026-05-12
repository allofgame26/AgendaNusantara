package com.example.agendanusantara;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView sapaan, tanggal, angkaSelesai, angkaBelumSelesai, tvAngkaChart;
    LinearLayout containerBarChart;
    CardView tombolPenting, tombolBiasa, tombolData, tombolPengaturan;
    View barSelesaiSingle, spacerSelesaiSingle;
    MyDatabase db;
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
        containerBarChart = findViewById(R.id.container_bar_chart);

        String TanggalSekarang = getTanggal();
        tanggal.setText(TanggalSekarang);
        sapaan.setText("Selamat Datang, " + namaUser + "! 👋");

        angkaSelesai = findViewById(R.id.angkaSelesai);
        angkaBelumSelesai = findViewById(R.id.angkaBelumSelesai);
        db = new MyDatabase(this);

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

    @Override
    protected void onResume() {
        super.onResume();

        // 1. Bersihkan wadah grafik sebelum menggambar ulang
        containerBarChart.removeAllViews();

        // 2. Ambil data dari database
        android.database.Cursor cursor = db.getDataSelesaiPerTanggal();

        int jumlahBelum = db.getJumlahTugas(0);    // 0 = Belum Selesai
        int jumlahSelesai = db.getJumlahTugas(1);  // 1 = Selesai

        // Tampilkan ke TextView (misalnya)
        angkaBelumSelesai.setText(String.valueOf(jumlahBelum));
        angkaSelesai.setText(String.valueOf(jumlahSelesai));

        if (cursor.moveToFirst()) {
            // Cari nilai tertinggi untuk menentukan skala tinggi grafik (agar tidak melebihi layar)
            int maxTugas = 0;
            do {
                int jml = cursor.getInt(1);
                if (jml > maxTugas) maxTugas = jml;
            } while (cursor.moveToNext());

            cursor.moveToFirst(); // Kembali ke baris pertama

            // 3. Loop data untuk membuat batang grafik
            do {
                String tanggal = cursor.getString(0);
                int jumlah = cursor.getInt(1);

                // Buat Layout untuk satu kolom (Batang + Teks Tanggal)
                LinearLayout column = new LinearLayout(this);
                column.setOrientation(LinearLayout.VERTICAL);
                column.setGravity(android.view.Gravity.BOTTOM | android.view.Gravity.CENTER_HORIZONTAL);
                column.setPadding(20, 0, 20, 0);

                // Buat Batang (Warna Hijau)
                View bar = new View(this);
                int tinggiBar = (jumlah * 350) / (maxTugas > 0 ? maxTugas : 1); // Skala tinggi (maks 350px)

                LinearLayout.LayoutParams barParams = new LinearLayout.LayoutParams(60, tinggiBar);
                bar.setLayoutParams(barParams);
                bar.setBackgroundColor(android.graphics.Color.parseColor("#4CAF50")); // Hijau

                // Tambahkan Angka di atas batang
                TextView tvJumlah = new TextView(this);
                tvJumlah.setText(String.valueOf(jumlah));
                tvJumlah.setGravity(android.view.Gravity.CENTER);
                tvJumlah.setTextSize(10);

                // Tambahkan Label Tanggal di bawah batang
                TextView tvTanggal = new TextView(this);
                tvTanggal.setText(tanggal);
                tvTanggal.setTextSize(9);
                tvTanggal.setGravity(android.view.Gravity.CENTER);

                // Masukkan elemen ke dalam kolom
                column.addView(tvJumlah);
                column.addView(bar);
                column.addView(tvTanggal);

                // Masukkan kolom ke wadah utama
                containerBarChart.addView(column);

            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}