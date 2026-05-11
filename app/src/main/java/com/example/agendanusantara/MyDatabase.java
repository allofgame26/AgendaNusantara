package com.example.agendanusantara;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    private Context context;

    private static final String Database_Name = "agendaNusantara.db";
    private static final int Database_Version = 1;


    public MyDatabase(@Nullable Context context) {
        super(context, Database_Name, null, Database_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryAkun = "Create table tabel_akun (" +
                "id_user INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT, " +
                "password TEXT);";
        db.execSQL(queryAkun);

        String queryTugas = "CREATE TABLE tabel_tugas (" +
                "id_tugas INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "judul TEXT, " +
                "deskripsi TEXT, " +
                "tanggal TEXT, " +
                "kategori TEXT, " +
                "status_selesai INTEGER);";
        db.execSQL(queryTugas);

        String insertAkun = "INSERT INTO tabel_akun (username, password) VALUES ('user', 'user');";
        db.execSQL(insertAkun);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tabel_akun");
        db.execSQL("DROP TABLE IF EXISTS tabel_tugas");
        onCreate(db);
    }

    public boolean login(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        // Melakukan query pencarian ke tabel_akun
        Cursor cursor = db.rawQuery("SELECT * FROM tabel_akun WHERE username = ? AND password = ?", new String[]{username,password});

        // data ditemukan
        if (cursor.getCount() > 0){
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public void addTugas (String judul, String deskripsi, String tanggal, String kategori){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("judul", judul);
        cv.put("deskripsi", deskripsi);
        cv.put("tanggal", tanggal);
        cv.put("kategori", kategori);
        cv.put("status_selesai", 0);

        long result = db.insert("tabel_tugas", null, cv);
        if (result == -1) {
            Toast.makeText(context, "Tugas Gagal Disimpan!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Tugas Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readSemuaTugas(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tabel_tugas",null);
    }
}
