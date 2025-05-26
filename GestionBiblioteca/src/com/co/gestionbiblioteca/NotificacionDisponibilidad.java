package com.co.gestionbiblioteca;

import java.util.ArrayList;
import java.util.List;




public class NotificacionDisponibilidad {

    private List<String> usuariosNotificados;

    public NotificacionDisponibilidad() {
        this.usuariosNotificados = new ArrayList<>();
    }

    public void notificarDisponibilidad(String usuario, String tituloLibro) {
        if (!usuariosNotificados.contains(usuario)) {
            System.out.println("Notificación enviada a " + usuario + ": El libro '" + tituloLibro + "' está disponible.");
            usuariosNotificados.add(usuario);
        } else {
            System.out.println("El usuario " + usuario + " ya fue notificado sobre el libro '" + tituloLibro + "'.");
        }
    }

    public void limpiarNotificaciones() {
        usuariosNotificados.clear();
        System.out.println("Lista de notificaciones limpiada.");
    }

    public static void main(String[] args) {
        NotificacionDisponibilidad notificacion = new NotificacionDisponibilidad();
        notificacion.notificarDisponibilidad("usuario1", "El Quijote");
        notificacion.notificarDisponibilidad("usuario2", "El Quijote");
        notificacion.notificarDisponibilidad("usuario1", "El Quijote");
        notificacion.limpiarNotificaciones();
        notificacion.notificarDisponibilidad("usuario1", "El Quijote");
    }
}