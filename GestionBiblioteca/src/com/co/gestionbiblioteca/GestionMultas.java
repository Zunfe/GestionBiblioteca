package com.co.gestionbiblioteca;

import java.util.HashMap;
import java.util.Map;



public class GestionMultas {
    private Map<String, Double> multas = new HashMap<>();
    private static final double MULTA_POR_DIA = 2.0;

    public void registrarMulta(String usuario, int diasRetraso) {
        if (diasRetraso > 0) {
            double montoMulta = diasRetraso * MULTA_POR_DIA;
            multas.put(usuario, multas.getOrDefault(usuario, 0.0) + montoMulta);
        }
    }

    public double consultarMulta(String usuario) {
        return multas.getOrDefault(usuario, 0.0);
    }

    public void pagarMulta(String usuario) {
        if (multas.containsKey(usuario)) {
            multas.remove(usuario);
        }
    }

    public void listarMultas() {
        for (Map.Entry<String, Double> entry : multas.entrySet()) {
            System.out.println("Usuario: " + entry.getKey() + ", Multa: $" + entry.getValue());
        }
    }
}