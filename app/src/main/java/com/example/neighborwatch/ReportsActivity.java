package com.example.neighborwatch;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ReportsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        recyclerViewReports = findViewById(R.id.recyclerViewReports);

        // Aquí puedes configurar el RecyclerView y el adaptador para mostrar los reportes
    }
}
