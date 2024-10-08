package com.example.neighborwatch;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

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
            String password = etPassword.getText().toString().trim();

            // Validación de campos vacíos
            if (curp.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() ||
                    edad.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Aquí se añadiría la lógica para enviar los datos al backend y registrar al usuario
                Toast.makeText(RegistroActivity.this, "Registrando usuario...", Toast.LENGTH_SHORT).show();
                // Puedes añadir la lógica para guardar el usuario en la base de datos aquí
            }
        });
    }
}
