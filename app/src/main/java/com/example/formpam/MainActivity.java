package com.example.formpam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> OtimoIdades = new ArrayList<>();
    int totalRegular = 0;
    int totalBom = 0;
    int totalPessoas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputEditText idade = findViewById(R.id.IdadeText);

        idade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idade.setText("");
            }
        });

        Button enviar = findViewById(R.id.EnviarBtn);
        RadioGroup opcoes = findViewById(R.id.radioGroup);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Respostas();
                idade.setText("");
                opcoes.clearCheck();
            }
        });
    }

    private void Respostas() {
        TextInputEditText idade = findViewById(R.id.IdadeText);

        String idadePessoa = idade.getText().toString();

        if (idadePessoa.isEmpty() || idadePessoa == "0") {
            Toast.makeText(this, "Insira uma idade válida!", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioGroup opinion = findViewById(R.id.radioGroup);
        int opinionId = opinion.getCheckedRadioButtonId();

        if (opinionId == -1) {
            Toast.makeText(this, "Selecione uma opção!", Toast.LENGTH_SHORT).show();
            return;
        }

        totalPessoas++;

        if (opinionId == R.id.radioButton) {  //otimo
            OtimoIdades.add(Integer.parseInt(idadePessoa));
        } else if (opinionId == R.id.radioButton2) { //bom
            totalBom++;
        } else if (opinionId == R.id.radioButton3) { //regular
            totalRegular++;
        }

        double mediaOtimo = 0;

        if (totalPessoas == 15) {
            if (!OtimoIdades.isEmpty()) {
                int soma = 0;
                for (int idadeMedia : OtimoIdades) {
                    soma += idadeMedia;
                }
                mediaOtimo = (double) soma / OtimoIdades.size();
            }
            double porcentagem = (double) totalBom / totalPessoas * 100;

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Resultados");

            String mensagem = "Media de idade Ótimo: " + mediaOtimo + "\n";
            mensagem += "Total de votos Regular: " + totalRegular + "\n";
            mensagem += "Porcentagem de votos Bom: " + porcentagem + "\n";

            builder.setMessage(mensagem);
            builder.setPositiveButton("OK", null);
            builder.show();

            totalPessoas = 0;
            totalRegular = 0;
            totalBom = 0;
        } else {
            Toast.makeText(this, "Resposta adicionada!", Toast.LENGTH_SHORT).show();
        }
    }
}
