package com.example.clase18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDB database;
    EditText nombre;
    EditText apellido;
    EditText edad;
    EditText sexo;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        nombre = (EditText) findViewById(R.id.txtNombre);
        apellido = (EditText) findViewById(R.id.txtApellido);
        edad = (EditText) findViewById(R.id.txtEdad);
        sexo = (EditText) findViewById(R.id.txtSexo);
        database = new SQLiteDB(getApplicationContext());
        seleccionarUsuarios(this.getCurrentFocus());
    }

    public void limpiar(View view) {
        nombre.setText(null);
        apellido.setText(null);
        edad.setText(null);
        sexo.setText(null);
    }

    public void insertar(View view) {
        database.insertarDatos(nombre.getText().toString()
                ,apellido.getText().toString()
                ,Integer.parseInt(edad.getText().toString())
                ,sexo.getText().toString());
        limpiar(view);
        seleccionarUsuarios(view);
    }

    public void modificar(View view) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre.getText().toString());
        usuario.setApellido(apellido.getText().toString());
        usuario.setEdad(Integer.parseInt(edad.getText().toString()));
        usuario.setSexo(sexo.getText().toString());

        database.modificarDatos(usuario);
        limpiar(view);
        seleccionarUsuarios(view);
    }

    public void eliminar(View view) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre.getText().toString());

        database.eliminarDatos(usuario);
        limpiar(view);
        seleccionarUsuarios(view);
    }

    public void seleccionarUsuarios(View view) {
        ArrayList<Usuario> usuarios = database.seleccionarUsuarios();
        TableLayout tabla = (TableLayout) findViewById(R.id.tblUsuarios);
        tabla.removeAllViews();

        TableRow rowHeader = new TableRow(getApplicationContext());
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT
                                            , TableLayout.LayoutParams.WRAP_CONTENT));

        String[] encabezado = {"Nombre","Apellido","Edad","Sexo"};

        for(String c:encabezado) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT
                    , TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(2, 2, 2, 2);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tabla.addView(rowHeader);

        if (usuarios.size() > 0) {
            for (int i = 0; i < usuarios.size(); i++) {
                TableRow tr = new TableRow(getApplicationContext());
                tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT
                        , TableLayout.LayoutParams.WRAP_CONTENT));
                TextView nom = new TextView(this);
                nom.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT
                        , TableRow.LayoutParams.WRAP_CONTENT));
                nom.setGravity(Gravity.CENTER);
                nom.setTextSize(18);
                nom.setPadding(2, 2, 2, 2);
                nom.setText(usuarios.get(i).getNombre());
                TextView ape = new TextView(this);
                ape.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT
                        , TableRow.LayoutParams.WRAP_CONTENT));
                ape.setGravity(Gravity.CENTER);
                ape.setTextSize(18);
                ape.setPadding(2, 2, 2, 2);
                ape.setText(usuarios.get(i).getApellido());
                TextView eda = new TextView(this);
                eda.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT
                        , TableRow.LayoutParams.WRAP_CONTENT));
                eda.setGravity(Gravity.CENTER);
                eda.setTextSize(18);
                eda.setPadding(2, 2, 2, 2);
                eda.setText(Integer.toString(usuarios.get(i).getEdad()));
                TextView sex = new TextView(this);
                sex.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT
                        , TableRow.LayoutParams.WRAP_CONTENT));
                sex.setGravity(Gravity.CENTER);
                sex.setTextSize(18);
                sex.setPadding(2, 2, 2, 2);
                sex.setText(usuarios.get(i).getSexo());
                tr.addView(nom);
                tr.addView(ape);
                tr.addView(eda);
                tr.addView(sex);
                tr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView nom = (TextView) tr.getChildAt(0);
                        TextView ape = (TextView) tr.getChildAt(1);
                        TextView eda = (TextView) tr.getChildAt(2);
                        TextView sex = (TextView) tr.getChildAt(3);

                        nombre.setText(nom.getText().toString());
                        apellido.setText(ape.getText().toString());
                        edad.setText(eda.getText().toString());
                        sexo.setText(sex.getText().toString());
                    }
                });
                tabla.addView(tr);
            }
        }
    }



    public void cerrarBase(View view) {
        if (db.isOpen()) {
            db.close();
        }
    }
    public void truncarTabla(View view) {
        database.truncarTabla("USUARIO");
    }

    public void abrirBase(View view) {
        if (!db.isOpen()) {
            db = database.abrirBaseDeDatos();
        }
    }
}