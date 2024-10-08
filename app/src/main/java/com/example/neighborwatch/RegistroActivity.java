package com.example.neighborwatch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegistroActivity extends AppCompatActivity {

    private EditText etCURP;
    private EditText etNombre;
    private EditText etApellidos;
    private EditText etEdad;
    private EditText etPhoneNumber;
    private EditText etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Asignar referencias a los elementos de la UI
        etCURP = findViewById(R.id.etCURP);
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etEdad = findViewById(R.id.etEdad);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(view -> {
            String curp = etCURP.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();
            String apellidos = etApellidos.getText().toString().trim();
            String edad = etEdad.getText().toString().trim();
            String phoneNumber = etPhoneNumber.getText().toString().trim();
            String password = etPassword.getText().toString().trim(); // Contraseña en texto plano

            // Validación de campos vacíos
            if (curp.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() ||
                    edad.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Llamar a la función para registrar al usuario
                registerUser(curp, nombre, apellidos, edad, phoneNumber, password);
            }
        });
    }

    private void registerUser(String curp, String nombre, String apellidos, String edad, String phoneNumber, String password) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("curp", curp)
                .add("nombre", nombre)
                .add("apellidos", apellidos)
                .add("edad", edad)
                .add("numero_telefono", phoneNumber)
                .add("contraseña", password) // Contraseña en texto plano
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2/NeighborWatch/register.php") // Cambia a la URL local de XAMPP
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(RegistroActivity.this, "Error de conexión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("RegistroActivity", "Response Data: " + responseData);

                    runOnUiThread(() -> {
                        try {
                            JSONObject json = new JSONObject(responseData);
                            if (json.getBoolean("success")) {
                                Toast.makeText(RegistroActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                // Aquí puedes redirigir al usuario o cerrar la actividad
                                Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                                finish(); // Opcional: cerrar la actividad de registro
                            } else {
                                Toast.makeText(RegistroActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            runOnUiThread(() -> Toast.makeText(RegistroActivity.this, "Error al procesar la respuesta: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(RegistroActivity.this, "Error en la respuesta del servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
}
