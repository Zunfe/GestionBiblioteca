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

class  GestionBibliotecaConGrafos {
    static class NodoGrafo {
        String id, tipo;
        List<Arista> conexiones = new ArrayList<>();

        NodoGrafo(String id, String tipo) {
            this.id = id;
            this.tipo = tipo;
        }

        @Override
        public String toString() {
            return String.format("NodoGrafo{id='%s', tipo='%s'}", id, tipo);
        }
    }

    static class Arista {
        NodoGrafo destino;
        int peso;

        Arista(NodoGrafo destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public String toString() {
            return String.format("Arista{destino=%s, peso=%d}", destino.id, peso);
        }
    }

    static Map<String, NodoGrafo> nodos = new HashMap<>();

    public static void agregarNodo(String id, String tipo) {
        nodos.computeIfAbsent(id, k -> new NodoGrafo(id, tipo));
    }

    public static void agregarConexion(String idOrigen, String idDestino, int peso) {
        NodoGrafo origen = nodos.get(idOrigen);
        NodoGrafo destino = nodos.get(idDestino);

        if (origen != null && destino != null) {
            origen.conexiones.add(new Arista(destino, peso));
        } else {
            System.out.printf("Uno de los nodos no existe: Origen(%s), Destino(%s)%n", idOrigen, idDestino);
        }
    }

    public static void mostrarGrafo() {
        nodos.values().forEach(nodo -> {
            System.out.printf("Nodo: %s (%s)%n", nodo.id, nodo.tipo);
            nodo.conexiones.forEach(arista ->
                System.out.printf("  -> %s (Peso: %d)%n", arista.destino.id, arista.peso));
        });
    }

    public static void registrarUsuario(String idUsuario) {
        agregarNodo(idUsuario, "usuario");
    }

    public static void registrarLibro(String idLibro) {
        agregarNodo(idLibro, "libro");
    }

    public static void prestarLibro(String idUsuario, String idLibro) {
        agregarConexion(idUsuario, idLibro, 1);
    }

    public static void devolverLibro(String idUsuario, String idLibro) {
        NodoGrafo usuario = nodos.get(idUsuario);
        if (usuario != null) {
            usuario.conexiones.removeIf(arista -> arista.destino.id.equals(idLibro));
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
                case 1 -> registrarLibro();
                case 2 -> registrarUsuario();
                case 3 -> {
                    if (usuarioActual != null) {
                        prestarLibro();
                    } else {
                        System.out.println("Debes Registrarte Primero");
                    }
                }
                case 4 -> {
                    if (usuarioActual != null) {
                        devolverLibro();
                    } else {
                        System.out.println("Debes registrarte primero");
                    }
                }
                case 5 -> GestionBibliotecaConGrafos.mostrarGrafo();
                default -> System.out.println("Opción no válida. Intente de nuevo");
            }

        } while (opcion != 6);
    }

    public static void mostrarMenu() {
        System.out.println("\nSistema de Gestión de Biblioteca");
        System.out.println("1. Registrar libro");
        System.out.println("2. Registrar usuario");
        System.out.println("3. Prestar libro");
        System.out.println("4. Devolver libro");
        System.out.println("5. Mostrar Grafo");
        System.out.println("6. Salir");
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
        arbolLibros.insertar(libro, Comparator.comparing(l -> l.codigoLibro));
        GestionBibliotecaConGrafos.registrarLibro(codigoLibro);
        System.out.println("Libro registrado exitosamente");
         
        
    }

    public static void registrarUsuario() {
        System.out.println("Registrar nuevo usuario");
        System.out.print("Ingrese el nombre del usuario: ");
        String nombre = sc.nextLine();

        usuarioActual = new Usuario(nombre, idUsuario);
        arbolUsuarios.insertar(usuarioActual, Comparator.comparingInt(u -> u.id));
        GestionBibliotecaConGrafos.registrarUsuario(String.valueOf(idUsuario));
        System.out.println(nombre + " registrado correctamente.");
        idUsuario++;
    }

    public static void prestarLibro() {
        System.out.println("Prestar libro para el usuario: " + usuarioActual.nombre);

        mostrarLibros();

        System.out.print("Ingrese el código del libro a prestar: ");
        String codigoLibro = sc.nextLine();

        Libro libro = arbolLibros.buscar(new Libro("", "", codigoLibro), Comparator.comparing(l -> l.codigoLibro));

        if (libro == null) {
            System.out.println("Libro no encontrado");
            return;
        }

        usuarioActual.prestarLibro(libro);
        GestionBibliotecaConGrafos.prestarLibro(String.valueOf(usuarioActual.id), codigoLibro);
    }

    public static void devolverLibro() {
        System.out.println("Devolver Libro para el usuario: " + usuarioActual.nombre);

        mostrarLibrosPorEstado();

        System.out.print("Ingrese el código del libro a devolver: ");
        String codigoLibro = sc.nextLine();

        Libro libro = arbolLibros.buscar(new Libro("", "", codigoLibro), Comparator.comparing(l -> l.codigoLibro));

        if (libro == null) {
            System.out.println("Libro no encontrado");
            return;
        }

        usuarioActual.devolverLibro(libro);
        GestionBibliotecaConGrafos.devolverLibro(String.valueOf(usuarioActual.id), codigoLibro);
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
            usuarioActual.librosPrestados.forEach(System.out::println);
            }
        }
    }

