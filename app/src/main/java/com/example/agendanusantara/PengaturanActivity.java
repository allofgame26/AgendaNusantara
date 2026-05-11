package com.example.agendanusantara;

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

public class PengaturanActivity extends AppCompatActivity {

    EditText inputPassLama, inputPassBaru;
    Button btnSimpanPass;
    MyDatabase myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pengaturan);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        inputPassLama = findViewById(R.id.input_pass_lama);
        inputPassBaru = findViewById(R.id.input_pass_baru);
        btnSimpanPass = findViewById(R.id.btn_simpan_password);
        myDB = new MyDatabase(this);

        btnSimpanPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passLama = inputPassLama.getText().toString().trim();
                String passBaru = inputPassBaru.getText().toString().trim();

                if (passLama.isEmpty() || passBaru.isEmpty()) {
                    Toast.makeText(PengaturanActivity.this, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show();
                } else {
                    // Cek database
                    if (myDB.cekPasswordLama(passLama)) {
                        myDB.updatePassword(passBaru);
                        Toast.makeText(PengaturanActivity.this, "Password Berhasil Diubah!", Toast.LENGTH_SHORT).show();
                        finish(); // Tutup halaman
                    } else {
                        Toast.makeText(PengaturanActivity.this, "Password Saat Ini SALAH!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}