package com.co.gestionbiblioteca;

import com.co.gestionbiblioteca.ArbolBinario.Nodo;
import java.util.*;

/**
 *
 * @author Felipe Zuñiga
 */

class Libro {
    public String titulo;
    public String autor;
    public String codigoLibro;
    public boolean esPrestado;

    public Libro(String titulo, String autor, String codigoLibro) {
        this.titulo = titulo;
        this.autor = autor;
        this.codigoLibro = codigoLibro;
        this.esPrestado = false;
    }

    @Override
    public String toString() {
        String estadoPrestamo = esPrestado ? "Sí" : "No";
        return "Título: " + titulo + ", Autor: " + autor + ", Codigo Libro: " + codigoLibro + ", Prestado: " + estadoPrestamo;
    }
}

class Usuario {
    public String nombre;
    public int id;
    public LinkedList<Libro> librosPrestados;

    public Usuario(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
        this.librosPrestados = new LinkedList<>();
    }

    public void prestarLibro(Libro libro) {
        if (!libro.esPrestado) {
            librosPrestados.add(libro);
            libro.esPrestado = true;
            System.out.println("Libro prestado correctamente");
        } else {
            System.out.println("El libro ya está prestado");
        }
    }

    public void devolverLibro(Libro libro) {
        if (librosPrestados.contains(libro)) {
            librosPrestados.remove(libro);
            libro.esPrestado = false;
            System.out.println("Libro devuelto correctamente");
        } else {
            System.out.println("Este libro no fue prestado por este usuario");
        }
    }
}
/**
 * Clase Arbol Generica
 * @param <T> 
 */
class ArbolBinario<T> {
    public Nodo<T> raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

  
    public void insertar(T valor, Comparator<T> comparator) {
        raiz = insertar(raiz, valor, comparator);
    }

    private Nodo<T> insertar(Nodo<T> nodo, T valor, Comparator<T> comparator) {
        if (nodo == null) {
            return new Nodo<>(valor);
        }

        int comparacion = comparator.compare(valor, nodo.valor);
        if (comparacion < 0) {
            nodo.izquierdo = insertar(nodo.izquierdo, valor, comparator);
        } else if (comparacion > 0) {
            nodo.derecho = insertar(nodo.derecho, valor, comparator);
        }

        return nodo;
    }

    
    public T buscar(T valor, Comparator<T> comparator) {
        Nodo<T> nodo = buscar(raiz, valor, comparator);
        return nodo != null ? nodo.valor : null;
    }

    private Nodo<T> buscar(Nodo<T> nodo, T valor, Comparator<T> comparator) {
        if (nodo == null || comparator.compare(valor, nodo.valor) == 0) {
            return nodo;
        }

        int comparacion = comparator.compare(valor, nodo.valor);
        if (comparacion < 0) {
            return buscar(nodo.izquierdo, valor, comparator);
        }

        return buscar(nodo.derecho, valor, comparator);
    }

  
    /**
     * Clase Nodo genérica
     * @param <T> 
     */
    static class Nodo<T> {
        T valor;
        Nodo<T> izquierdo, derecho;

        public Nodo(T valor) {
            this.valor = valor;
            this.izquierdo = null;
            this.derecho = null;
        }
    }
}

public class GestionBiblioteca {

    static Scanner sc = new Scanner(System.in);
    static ArbolBinario<Usuario> arbolUsuarios = new ArbolBinario<>();
    static ArbolBinario<Libro> arbolLibros = new ArbolBinario<>();
    static int idUsuario = 1;
    static Usuario usuarioActual;

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();

            while (!sc.hasNextInt()) {
                System.out.println("Entrada Invalida por favor ingresa un numero");
                sc.next(); 
            }
            opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    registrarLibro();
                    break;

                case 2:
                    registrarUsuario();
                    break;

                case 3:
                    if (usuarioActual != null) {
                        prestarLibro();
                    } else {
                        System.out.println("Debes Registrarte Primero");
                    }
                    break;

                case 4:
                    if (usuarioActual != null) {
                        devolverLibro();
                    } else {
                        System.out.println("Debes registrarte primero");
                    }
                    break;

                case 5:
                    System.out.println("Saliendo del sistema . . . ");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo");
            }

        } while (opcion != 5);
    }

    public static void mostrarMenu() {
        System.out.println("\nSistema de Gestión de Biblioteca");
        System.out.println("1. Registrar libro");
        System.out.println("2. Registrar usuario");
        System.out.println("3. Prestar libro");
        System.out.println("4. Devolver libro");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public static void registrarLibro() {
        System.out.println("Registrar nuevo libro");
        System.out.print("Ingrese el título del libro: ");
        String titulo = sc.nextLine();
        System.out.print("Ingrese el autor del libro: ");
        String autor = sc.nextLine();
        System.out.print("Ingrese el Código del libro: ");
        String codigoLibro = sc.nextLine();

        Libro libro = new Libro(titulo, autor, codigoLibro);
        arbolLibros.insertar(libro, new Comparator<Libro>() {
            @Override
            public int compare(Libro l1, Libro l2) {
                return l1.codigoLibro.compareTo(l2.codigoLibro);
            }
        });
        System.out.println("Libro registrado exitosamente");
    }

    public static void registrarUsuario() {
        System.out.println("Registrar nuevo usuario");
        System.out.print("Ingrese el nombre del usuario: ");
        String nombre = sc.nextLine();

        usuarioActual = new Usuario(nombre, idUsuario);
        arbolUsuarios.insertar(usuarioActual, new Comparator<Usuario>() {
            @Override
            public int compare(Usuario u1, Usuario u2) {
                return Integer.compare(u1.id, u2.id);
            }
        });
        System.out.println(nombre + " registrado correctamente.");
        idUsuario++;
    }

    public static void prestarLibro() {
        System.out.println("Prestar libro para el usuario: " + usuarioActual.nombre);

        mostrarLibros();

        System.out.print("Ingrese el código del libro a prestar: ");
        String codigoLibro = sc.nextLine();

        Libro libro = arbolLibros.buscar(new Libro("", "", codigoLibro), new Comparator<Libro>() {
            @Override
            public int compare(Libro l1, Libro l2) {
                return l1.codigoLibro.compareTo(l2.codigoLibro);
            }
        });

        if (libro == null) {
            System.out.println("Libro no encontrado");
            return;
        }

        usuarioActual.prestarLibro(libro);
    }

    public static void devolverLibro() {
        System.out.println("Devolver Libro para el usuario: " + usuarioActual.nombre);

        mostrarLibrosPorEstado();

        System.out.print("Ingrese el código del libro a devolver: ");
        String codigoLibro = sc.nextLine();

        Libro libro = arbolLibros.buscar(new Libro("", "", codigoLibro), new Comparator<Libro>() {
            @Override
            public int compare(Libro l1, Libro l2) {
                return l1.codigoLibro.compareTo(l2.codigoLibro);
            }
        });

        if (libro == null) {
            System.out.println("Libro no encontrado");
            return;
        }

        usuarioActual.devolverLibro(libro);
    }

    private static void mostrarLibros() {
        System.out.println("\nLista de libros en la biblioteca:");
        if (arbolLibros == null) {
            System.out.println("No hay libros registrados");
        } else {
            mostrarLibros(arbolLibros.raiz);
        }
    }

    private static void mostrarLibros(Nodo<Libro> nodo) {
        if (nodo != null) {
            mostrarLibros(nodo.izquierdo);
            System.out.println(nodo.valor);
            mostrarLibros(nodo.derecho);
        }
    }

    private static void mostrarLibrosPorEstado() {
        System.out.println("\nLista de libros prestados por ti: ");
        if (usuarioActual.librosPrestados.isEmpty()) {
            System.out.println("No has prestado ningun libro");
        } else {
            for (Libro libro : usuarioActual.librosPrestados) {
                System.out.println(libro.toString());
            }
        }
    }
}
