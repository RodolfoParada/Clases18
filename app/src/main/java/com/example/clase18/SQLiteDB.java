package com.example.clase18;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLiteDB extends SQLiteOpenHelper {
    public SQLiteDB(Context context) {
        super(context, "dbUsuario2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario ("+
                "nombre varchar(100)" +
                ",apellido varchar(100)"+
                ",edad int"+
                ",sexo varchar(1))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public SQLiteDatabase abrirBaseDeDatos()  {
        return getReadableDatabase();
    }

    public void modificarDatos(Usuario usuario) {
        SQLiteDatabase db1 = getWritableDatabase();
        String sql = String.format(
                "UPDATE usuario SET nombre='%s', apellido='%s', edad=%d, sexo='%s' " +
                        "WHERE nombre='%s';", usuario.getNombre(), usuario.getApellido(), usuario.getEdad(), usuario.getSexo(), usuario.getNombre());
        db1.execSQL(sql);
        db1.close();
    }

    public void eliminarDatos(Usuario usuario) {
        SQLiteDatabase db1 = getWritableDatabase();
        String sql = String.format(
                "DELETE FROM usuario " +
                        "WHERE nombre='%s';", usuario.getNombre());
        db1.execSQL(sql);
        db1.close();
    }

    public void insertarDatos(String nom, String ape, int eda, String sex) {
        SQLiteDatabase db1 = getWritableDatabase();
        String sql = String.format(
                "insert into Usuario (nombre, apellido, edad, sexo) " +
                        "values ('%s','%s','%d','%s');", nom, ape, eda, sex);
        db1.execSQL(sql);
        db1.close();
    }

    public ArrayList<Usuario> seleccionarUsuarios() {
        SQLiteDatabase db1 = getReadableDatabase();
        Cursor c;
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        c = db1.rawQuery("select * from Usuario", null);

        if (c.moveToFirst()) {
            do {
                usuarios.add(new Usuario(c.getString(0), c.getString(1), c.getInt(2), c.getString(3)));
            } while(c.moveToNext());
        }

        db1.close();
        return usuarios;
    }

    public void truncarTabla(String tabla) {
        SQLiteDatabase db1 = getWritableDatabase();
        db1.execSQL("DELETE FROM " + tabla);
        db1.close();
    }

    }

