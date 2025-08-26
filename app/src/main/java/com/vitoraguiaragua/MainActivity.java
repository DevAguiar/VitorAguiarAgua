package com.vitoraguiaragua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import com.vitoraguiaragua.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private WaterViewModel viewModel;
    private CupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(WaterViewModel.class);

        // Configurar RecyclerView
        RecyclerView recyclerView = binding.recyclerViewCopos;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        // Inicializar adapter
        adapter = new CupAdapter(viewModel.getCopos().getValue(), viewModel);
        recyclerView.setAdapter(adapter);

        // Configurar observadores
        viewModel.getCopos().observe(this, copos -> {
            adapter.setCopos(copos);
            atualizarUI();
        });

        viewModel.getPeso().observe(this, peso -> {
            binding.editTextPeso.setText(peso);
        });

        // Configurar botões
        binding.buttonCalcular.setOnClickListener(v -> {
            String peso = binding.editTextPeso.getText().toString();
            viewModel.calcularAgua(peso);
        });

        binding.buttonLimpar.setOnClickListener(v -> {
            viewModel.limparCopos();
        });

        // Inicializar a UI
        atualizarUI();
    }

    private void atualizarUI() {
        // Atualizar textos
        binding.textViewAguaIngerida.setText(viewModel.getAguaIngerida() + " ml");

        int aguaRestante = viewModel.getAguaRestante();
        int coposRestantes = viewModel.getCoposRestantes();
        binding.textViewAguaRestante.setText(aguaRestante + " ml (" + coposRestantes + " copos)");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Salvar estado dos copos
        if (viewModel.getCopos().getValue() != null) {
            boolean[] coposArray = new boolean[viewModel.getCopos().getValue().size()];
            for (int i = 0; i < viewModel.getCopos().getValue().size(); i++) {
                coposArray[i] = viewModel.getCopos().getValue().get(i);
            }
            outState.putBooleanArray("copos", coposArray);
        }
        outState.putString("peso", viewModel.getPeso().getValue());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restaurar estado dos copos
        boolean[] coposArray = savedInstanceState.getBooleanArray("copos");
        if (coposArray != null) {
            List<Boolean> coposList = new ArrayList<>();
            for (boolean b : coposArray) {
                coposList.add(b);
            }
            viewModel.setCopos(coposList);
        }

        String peso = savedInstanceState.getString("peso");
        if (peso != null) {
            viewModel.setPeso(peso);
            viewModel.calcularAgua(peso);
        }
    }
}