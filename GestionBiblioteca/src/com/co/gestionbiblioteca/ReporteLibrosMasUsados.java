package com.co.gestionbiblioteca;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public class ReporteLibrosMasUsados {
    public static class Prestamo {
        private String tituloLibro;
        private String fecha;

        public Prestamo(String tituloLibro, String fecha) {
            this.tituloLibro = tituloLibro;
            this.fecha = fecha;
        }

        public String getTituloLibro() {
            return tituloLibro;
        }

        public String getFecha() {
            return fecha;
        }
    }
    

public void generarReporte(List<Prestamo> prestamos, String fechaInicio, String fechaFin) {
    Map<String, Long> librosMasPrestados = prestamos.stream()
        .filter(prestamo -> prestamo.getFecha().compareTo(fechaInicio) >= 0 && prestamo.getFecha().compareTo(fechaFin) <= 0)
        .collect(Collectors.groupingBy(Prestamo::getTituloLibro, Collectors.counting()));

    librosMasPrestados.entrySet().stream()
        .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
        .forEach(entry -> System.out.println("Libro: " + entry.getKey() + ", Veces Prestado: " + entry.getValue()));
}
}