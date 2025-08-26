package com.vitoraguiaragua;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class WaterViewModel extends ViewModel {
    private MutableLiveData<String> peso = new MutableLiveData<>("70");
    private MutableLiveData<List<Boolean>> copos = new MutableLiveData<>();
    private MutableLiveData<Integer> aguaTotal = new MutableLiveData<>(0);

    public WaterViewModel() {
        // Inicializa com 15 copos (todos vazios)
        List<Boolean> coposIniciais = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            coposIniciais.add(false);
        }
        copos.setValue(coposIniciais);
        calcularAgua("70"); // Calcula a água inicial
    }

    public LiveData<String> getPeso() {
        return peso;
    }

    public LiveData<List<Boolean>> getCopos() {
        return copos;
    }

    public LiveData<Integer> getAguaTotal() {
        return aguaTotal;
    }

    public void calcularAgua(String pesoStr) {
        try {
            int pesoValue = Integer.parseInt(pesoStr);
            aguaTotal.setValue(pesoValue * 35);
            peso.setValue(pesoStr);
        } catch (NumberFormatException e) {
            aguaTotal.setValue(0);
        }
    }

    public void toggleCup(int position) {
        List<Boolean> currentCopos = copos.getValue();
        if (currentCopos != null && position >= 0 && position < currentCopos.size()) {
            currentCopos.set(position, !currentCopos.get(position));
            copos.setValue(currentCopos);
        }
    }

    public void limparCopos() {
        List<Boolean> currentCopos = copos.getValue();
        if (currentCopos != null) {
            for (int i = 0; i < currentCopos.size(); i++) {
                currentCopos.set(i, false);
            }
            copos.setValue(currentCopos);
        }
    }

    // Novo método para restaurar o estado dos copos
    public void setCopos(List<Boolean> novosCopos) {
        copos.setValue(novosCopos);
    }

    public int getAguaIngerida() {
        List<Boolean> currentCopos = copos.getValue();
        if (currentCopos == null) return 0;

        int count = 0;
        for (Boolean tomado : currentCopos) {
            if (tomado) count++;
        }
        return count * 150;
    }

    public int getAguaRestante() {
        Integer total = aguaTotal.getValue();
        return (total != null ? total : 0) - getAguaIngerida();
    }

    public int getCoposRestantes() {
        int aguaRestante = getAguaRestante();
        return Math.max(0, aguaRestante / 150);
    }

    public void setPeso(String peso) {
        this.peso.setValue(peso);
    }
}