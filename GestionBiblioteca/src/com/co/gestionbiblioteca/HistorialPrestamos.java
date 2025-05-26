package com.co.gestionbiblioteca;
import java.util.ArrayList;
import java.util.List;




public class HistorialPrestamos {

    private List<RegistroPrestamo> historial;

    public HistorialPrestamos() {
        this.historial = new ArrayList<>();
    }

    public void agregarRegistro(RegistroPrestamo registro) {
        historial.add(registro);
    }

    public List<RegistroPrestamo> consultarHistorial() {
        return new ArrayList<>(historial);
    }

    public static class RegistroPrestamo {
        private String tituloLibro;
        private String fechaPrestamo;
        private String fechaDevolucion;

        public RegistroPrestamo(String tituloLibro, String fechaPrestamo, String fechaDevolucion) {
            this.tituloLibro = tituloLibro;
            this.fechaPrestamo = fechaPrestamo;
            this.fechaDevolucion = fechaDevolucion;
        }

        public String getTituloLibro() {
            return tituloLibro;
        }

        public String getFechaPrestamo() {
            return fechaPrestamo;
        }

        public String getFechaDevolucion() {
            return fechaDevolucion;
        }

        @Override
        public String toString() {
            return "Libro: " + tituloLibro + ", Prestado: " + fechaPrestamo + ", Devuelto: " + fechaDevolucion;
        }
    }

    public static void main(String[] args) {
        HistorialPrestamos historial = new HistorialPrestamos();

        historial.agregarRegistro(new RegistroPrestamo("El Quijote", "2023-01-10", "2023-01-20"));
        historial.agregarRegistro(new RegistroPrestamo("Cien Años de Soledad", "2023-02-15", "2023-02-25"));

        System.out.println("Historial de Préstamos:");
        for (RegistroPrestamo registro : historial.consultarHistorial()) {
            System.out.println(registro);
        }
    }
}