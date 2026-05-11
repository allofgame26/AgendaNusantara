package com.example.agendanusantara;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    EditText inputUsername, inputPassword;
    Button login;
    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        inputUsername = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);
        login = findViewById(R.id.btn_login);

        db = new MyDatabase(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (user.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username & Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean cekAkun = db.login(user, password);

                if (cekAkun == true){
                    Toast.makeText(LoginActivity.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("nama_user", user);
                    startActivity(intent);

                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Username atau Password salah!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}