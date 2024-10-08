package com.example.neighborwatch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etPhoneNumber;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Asignar referencias a los elementos de la UI
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(view -> {
            String phoneNumber = etPhoneNumber.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validación de campos vacíos
            if (phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Llamar a la función para realizar la autenticación
                loginUser(phoneNumber, password);
            }
        });

        // Configurar el listener para el registro
        tvRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String phoneNumber, String password) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("numero_telefono", phoneNumber) // Cambiado a "numero_telefono"
                .add("contraseña", password) // Cambiado a "contraseña"
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2/NeighborWatch/login.php") // Cambiar a la URL local de XAMPP
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Error de conexión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("LoginActivity", "Response Data: " + responseData);

                    runOnUiThread(() -> {
                        try {
                            JSONObject json = new JSONObject(responseData);
                            if (json.getBoolean("success")) {
                                Toast.makeText(LoginActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                // Redirigir a DashboardActivity
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish(); // Opcional: Cierra LoginActivity para que no se pueda regresar a ella
                            } else {
                                Toast.makeText(LoginActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Error al procesar la respuesta: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "Error en la respuesta del servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

}
