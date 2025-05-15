package com.example.hamburgueriaz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResumoPedidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pedido);

        TextView tvResumoPedido = findViewById(R.id.tvResumoPedido);
        TextView tvValorTotal = findViewById(R.id.tvValorTotal);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        // Recebe os dados da MainActivity
        Intent intent = getIntent();
        String resumoPedido = intent.getStringExtra("resumoPedido");
        String valorTotal = intent.getStringExtra("valorTotal");

        // Exibe os dados
        tvResumoPedido.setText(resumoPedido);
        tvValorTotal.setText(valorTotal);

        // Botão para voltar ao cardápio
        btnVoltar.setOnClickListener(v -> {
            finish(); // Fecha a activity atual e volta para a MainActivity
        });
    }
}