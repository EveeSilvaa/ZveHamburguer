package com.example.hamburgueriaz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Variáveis para ingredientes
    private TextView tvQtdCarne, tvQtdQueijo, tvQtdBacon, tvQtdAlface, tvQtdTomate, tvQtdCebola, tvQtdPicles, tvQtdMolho;
    private int qtdCarne = 1, qtdQueijo = 1, qtdBacon = 1, qtdAlface = 1, qtdTomate = 1, qtdCebola = 1, qtdPicles = 1, qtdMolho = 1;

    // Variáveis para bebidas
    private TextView tvQtdFreeRefill, tvQtdPepsiLight, tvQtdGuarana, tvQtdGuaranaLight, tvQtdSukita, tvQtdSoda, tvQtdPepsiCola, tvQtdPepsiTwist, tvQtdH2ohLimao, tvQtdShakeMorango;
    private int qtdFreeRefill = 0, qtdPepsiLight = 0, qtdGuarana = 0, qtdGuaranaLight = 0, qtdSukita = 0, qtdSoda = 0, qtdPepsiCola = 0, qtdPepsiTwist = 0, qtdH2ohLimao = 0, qtdShakeMorango = 0;

    // Outros componentes
    private RadioGroup rgTipoPedido;
    private Button btnAdicionarSacola;
    private View layoutFreeRefill, layoutPepsiLight, layoutGuarana, layoutGuaranaLight, layoutSukita, layoutSoda, layoutPepsiCola, layoutPepsiTwist, layoutH2ohLimao, layoutShakeMorango;

    // Preços
    private final double PRECO_INDIVIDUAL = 35.90;
    private final double PRECO_COMBO = 39.90;
    private final double PRECO_CARNE_EXTRA = 5.00;
    private final double PRECO_QUEIJO_EXTRA = 3.00;
    private final double PRECO_BACON_EXTRA = 4.00;
    private final double PRECO_MOLHO_EXTRA = 2.00;
    private final double PRECO_BEBIDA = 7.50;
    private final double PRECO_SHAKE = 9.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar componentes
        inicializarComponentes();
        configurarTodosBotoesQuantidade();

        // Selecionar combo por padrão
        rgTipoPedido.check(R.id.rbCombo);
        mostrarOpcoesBebidas(true);
        calcularTotal(); // Calcular o total inicial

        // Configurar listener do botão "Adicionar à Sacola"
        btnAdicionarSacola.setOnClickListener(v -> {
            if (rgTipoPedido.getCheckedRadioButtonId() == R.id.rbCombo && getTotalBebidas() == 0) {
                Toast.makeText(this, "Selecione pelo menos uma bebida para o combo", Toast.LENGTH_SHORT).show();
                return;
            }

            // Criar resumo do pedido
            StringBuilder resumo = new StringBuilder();
            resumo.append("🍔 Hambúrguer Personalizado\n\n");
            resumo.append("Tipo: ").append(rgTipoPedido.getCheckedRadioButtonId() == R.id.rbCombo ? "Combo" : "Individual").append("\n\n");

            resumo.append("Ingredientes:\n");
            if (qtdCarne > 0) resumo.append("- Carne: ").append(qtdCarne).append("\n");
            if (qtdQueijo > 0) resumo.append("- Queijo: ").append(qtdQueijo).append("\n");
            if (qtdBacon > 0) resumo.append("- Bacon: ").append(qtdBacon).append("\n");
            if (qtdAlface > 0) resumo.append("- Alface: ").append(qtdAlface).append("\n");
            if (qtdTomate > 0) resumo.append("- Tomate: ").append(qtdTomate).append("\n");
            if (qtdCebola > 0) resumo.append("- Cebola: ").append(qtdCebola).append("\n");
            if (qtdPicles > 0) resumo.append("- Picles: ").append(qtdPicles).append("\n");
            if (qtdMolho > 0) resumo.append("- Molho: ").append(qtdMolho).append("\n\n");

            if (rgTipoPedido.getCheckedRadioButtonId() == R.id.rbCombo) {
                resumo.append("Bebidas:\n");
                if (qtdFreeRefill > 0) resumo.append("- Free Refill: ").append(qtdFreeRefill).append("\n");
                if (qtdPepsiLight > 0) resumo.append("- Pepsi Light: ").append(qtdPepsiLight).append("\n");
                if (qtdGuarana > 0) resumo.append("- Guaraná: ").append(qtdGuarana).append("\n");
                if (qtdGuaranaLight > 0) resumo.append("- Guaraná Light: ").append(qtdGuaranaLight).append("\n");
                if (qtdSukita > 0) resumo.append("- Sukita: ").append(qtdSukita).append("\n");
                if (qtdSoda > 0) resumo.append("- Soda: ").append(qtdSoda).append("\n");
                if (qtdPepsiCola > 0) resumo.append("- Pepsi Cola: ").append(qtdPepsiCola).append("\n");
                if (qtdPepsiTwist > 0) resumo.append("- Pepsi Twist: ").append(qtdPepsiTwist).append("\n");
                if (qtdH2ohLimao > 0) resumo.append("- H2OH! Limão: ").append(qtdH2ohLimao).append("\n");
                if (qtdShakeMorango > 0) resumo.append("- Shake Morango: ").append(qtdShakeMorango).append("\n\n");
            }

            // Obter valor total
            String valorTotal = btnAdicionarSacola.getText().toString();

            // Iniciar a ResumoPedidoActivity
            Intent intent = new Intent(MainActivity.this, ResumoPedidoActivity.class);
            intent.putExtra("resumoPedido", resumo.toString());
            intent.putExtra("valorTotal", valorTotal);
            startActivity(intent);
        });
    }

    private void inicializarComponentes() {
        // Ingredientes
        tvQtdCarne = findViewById(R.id.tvQtdCarne);
        tvQtdQueijo = findViewById(R.id.tvQtdQueijo);
        tvQtdBacon = findViewById(R.id.tvQtdBacon);
        tvQtdAlface = findViewById(R.id.tvQtdAlface);
        tvQtdTomate = findViewById(R.id.tvQtdTomate);
        tvQtdCebola = findViewById(R.id.tvQtdCebola);
        tvQtdPicles = findViewById(R.id.tvQtdPicles);
        tvQtdMolho = findViewById(R.id.tvQtdMolho);

        // Bebidas
        tvQtdFreeRefill = findViewById(R.id.tvQtdFreeRefill);
        tvQtdPepsiLight = findViewById(R.id.tvQtdPepsiLight);
        tvQtdGuarana = findViewById(R.id.tvQtdGuarana);
        tvQtdGuaranaLight = findViewById(R.id.tvQtdGuaranaLight);
        tvQtdSukita = findViewById(R.id.tvQtdSukita);
        tvQtdSoda = findViewById(R.id.tvQtdSoda);
        tvQtdPepsiCola = findViewById(R.id.tvQtdPepsiCola);
        tvQtdPepsiTwist = findViewById(R.id.tvQtdPepsiTwist);
        tvQtdH2ohLimao = findViewById(R.id.tvQtdH2ohLimao);
        tvQtdShakeMorango = findViewById(R.id.tvQtdShakeMorango);

        // Outros componentes
        rgTipoPedido = findViewById(R.id.rgTipoPedido);
        btnAdicionarSacola = findViewById(R.id.btnAdicionarSacola);

        // Layouts de bebidas
        layoutFreeRefill = findViewById(R.id.layoutFreeRefill);
        layoutPepsiLight = findViewById(R.id.layoutPepsiLight);
        layoutGuarana = findViewById(R.id.layoutGuarana);
        layoutGuaranaLight = findViewById(R.id.layoutGuaranaLight);
        layoutSukita = findViewById(R.id.layoutSukita);
        layoutSoda = findViewById(R.id.layoutSoda);
        layoutPepsiCola = findViewById(R.id.layoutPepsiCola);
        layoutPepsiTwist = findViewById(R.id.layoutPepsiTwist);
        layoutH2ohLimao = findViewById(R.id.layoutH2ohLimao);
        layoutShakeMorango = findViewById(R.id.layoutShakeMorango);
    }

    private void configurarTodosBotoesQuantidade() {
        // Ingredientes
        configurarBotaoQuantidade(R.id.btnMaisCarne, R.id.btnMenosCarne, tvQtdCarne,
                () -> ++qtdCarne, () -> qtdCarne > 0 ? --qtdCarne : 0);

        configurarBotaoQuantidade(R.id.btnMaisQueijo, R.id.btnMenosQueijo, tvQtdQueijo,
                () -> ++qtdQueijo, () -> qtdQueijo > 0 ? --qtdQueijo : 0);

        configurarBotaoQuantidade(R.id.btnMaisBacon, R.id.btnMenosBacon, tvQtdBacon,
                () -> ++qtdBacon, () -> qtdBacon > 0 ? --qtdBacon : 0);

        configurarBotaoQuantidade(R.id.btnMaisAlface, R.id.btnMenosAlface, tvQtdAlface,
                () -> ++qtdAlface, () -> qtdAlface > 0 ? --qtdAlface : 0);

        configurarBotaoQuantidade(R.id.btnMaisTomate, R.id.btnMenosTomate, tvQtdTomate,
                () -> ++qtdTomate, () -> qtdTomate > 0 ? --qtdTomate : 0);

        configurarBotaoQuantidade(R.id.btnMaisCebola, R.id.btnMenosCebola, tvQtdCebola,
                () -> ++qtdCebola, () -> qtdCebola > 0 ? --qtdCebola : 0);

        configurarBotaoQuantidade(R.id.btnMaisPicles, R.id.btnMenosPicles, tvQtdPicles,
                () -> ++qtdPicles, () -> qtdPicles > 0 ? --qtdPicles : 0);

        configurarBotaoQuantidade(R.id.btnMaisMolho, R.id.btnMenosMolho, tvQtdMolho,
                () -> ++qtdMolho, () -> qtdMolho > 0 ? --qtdMolho : 0);

        // Bebidas
        configurarBotaoQuantidade(R.id.btnMaisFreeRefill, R.id.btnMenosFreeRefill, tvQtdFreeRefill,
                () -> ++qtdFreeRefill, () -> qtdFreeRefill > 0 ? --qtdFreeRefill : 0);

        configurarBotaoQuantidade(R.id.btnMaisPepsiLight, R.id.btnMenosPepsiLight, tvQtdPepsiLight,
                () -> ++qtdPepsiLight, () -> qtdPepsiLight > 0 ? --qtdPepsiLight : 0);

        configurarBotaoQuantidade(R.id.btnMaisGuarana, R.id.btnMenosGuarana, tvQtdGuarana,
                () -> ++qtdGuarana, () -> qtdGuarana > 0 ? --qtdGuarana : 0);

        configurarBotaoQuantidade(R.id.btnMaisGuaranaLight, R.id.btnMenosGuaranaLight, tvQtdGuaranaLight,
                () -> ++qtdGuaranaLight, () -> qtdGuaranaLight > 0 ? --qtdGuaranaLight : 0);

        configurarBotaoQuantidade(R.id.btnMaisSukita, R.id.btnMenosSukita, tvQtdSukita,
                () -> ++qtdSukita, () -> qtdSukita > 0 ? --qtdSukita : 0);

        configurarBotaoQuantidade(R.id.btnMaisSoda, R.id.btnMenosSoda, tvQtdSoda,
                () -> ++qtdSoda, () -> qtdSoda > 0 ? --qtdSoda : 0);

        configurarBotaoQuantidade(R.id.btnMaisPepsiCola, R.id.btnMenosPepsiCola, tvQtdPepsiCola,
                () -> ++qtdPepsiCola, () -> qtdPepsiCola > 0 ? --qtdPepsiCola : 0);

        configurarBotaoQuantidade(R.id.btnMaisPepsiTwist, R.id.btnMenosPepsiTwist, tvQtdPepsiTwist,
                () -> ++qtdPepsiTwist, () -> qtdPepsiTwist > 0 ? --qtdPepsiTwist : 0);

        configurarBotaoQuantidade(R.id.btnMaisH2ohLimao, R.id.btnMenosH2ohLimao, tvQtdH2ohLimao,
                () -> ++qtdH2ohLimao, () -> qtdH2ohLimao > 0 ? --qtdH2ohLimao : 0);

        configurarBotaoQuantidade(R.id.btnMaisShakeMorango, R.id.btnMenosShakeMorango, tvQtdShakeMorango,
                () -> ++qtdShakeMorango, () -> qtdShakeMorango > 0 ? --qtdShakeMorango : 0);
    }

    private void configurarBotaoQuantidade(int btnMaisId, int btnMenosId, TextView tvQuantidade,
                                           QuantidadeProvider incremento, QuantidadeProvider decremento) {
        findViewById(btnMaisId).setOnClickListener(v -> {
            tvQuantidade.setText(String.valueOf(incremento.getNovaQuantidade()));
            calcularTotal();
        });

        findViewById(btnMenosId).setOnClickListener(v -> {
            tvQuantidade.setText(String.valueOf(decremento.getNovaQuantidade()));
            calcularTotal();
        });
    }

    private interface QuantidadeProvider {
        int getNovaQuantidade();
    }

    private void mostrarOpcoesBebidas(boolean mostrar) {
        int visibilidade = mostrar ? View.VISIBLE : View.GONE;

        layoutFreeRefill.setVisibility(visibilidade);
        layoutPepsiLight.setVisibility(visibilidade);
        layoutGuarana.setVisibility(visibilidade);
        layoutGuaranaLight.setVisibility(visibilidade);
        layoutSukita.setVisibility(visibilidade);
        layoutSoda.setVisibility(visibilidade);
        layoutPepsiCola.setVisibility(visibilidade);
        layoutPepsiTwist.setVisibility(visibilidade);
        layoutH2ohLimao.setVisibility(visibilidade);
        layoutShakeMorango.setVisibility(visibilidade);
    }

    private int getTotalBebidas() {
        return qtdFreeRefill + qtdPepsiLight + qtdGuarana + qtdGuaranaLight + qtdSukita +
                qtdSoda + qtdPepsiCola + qtdPepsiTwist + qtdH2ohLimao + qtdShakeMorango;
    }

    private void calcularTotal() {
        double precoBase = rgTipoPedido.getCheckedRadioButtonId() == R.id.rbCombo ? PRECO_COMBO : PRECO_INDIVIDUAL;
        double precoTotal = precoBase;

        // Calcular extras de ingredientes (subtraindo 1 pois a primeira unidade já está incluída no preço base)
        precoTotal += (qtdCarne - 1) * PRECO_CARNE_EXTRA;
        precoTotal += (qtdQueijo - 1) * PRECO_QUEIJO_EXTRA;
        precoTotal += (qtdBacon - 1) * PRECO_BACON_EXTRA;
        precoTotal += (qtdMolho - 1) * PRECO_MOLHO_EXTRA;

        // Calcular bebidas (apenas para combo)
        if (rgTipoPedido.getCheckedRadioButtonId() == R.id.rbCombo) {
            precoTotal += (qtdFreeRefill + qtdPepsiLight + qtdGuarana + qtdGuaranaLight + qtdSukita +
                    qtdSoda + qtdPepsiCola + qtdPepsiTwist) * PRECO_BEBIDA;
            precoTotal += qtdH2ohLimao * PRECO_BEBIDA;
            precoTotal += qtdShakeMorango * PRECO_SHAKE;
        }

        // Atualizar o botão com o novo total
        btnAdicionarSacola.setText(String.format("ADICIONAR NA SACOLA - R$ %.2f", precoTotal));
    }
}

