package org.example;
import java.util.*;

class Puerto {
    String nombre;
    Map<Puerto, Integer> conexiones = new HashMap<>();

    Puerto(String nombre) {
        this.nombre = nombre;
    }

    void conectar(Puerto destino, int distancia) {
        conexiones.put(destino, distancia);
        destino.conexiones.put(this, distancia); // Es un grafo no dirigido
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Puerto puerto = (Puerto) obj;
        return Objects.equals(nombre, puerto.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}

class Grafo {
    private Map<String, Puerto> puertos = new HashMap<>();

    void agregarPuerto(String nombre) {
        puertos.putIfAbsent(nombre, new Puerto(nombre));
    }

    void conectar(String nombrePuerto1, String nombrePuerto2, int distancia) {
        Puerto puerto1 = puertos.get(nombrePuerto1);
        Puerto puerto2 = puertos.get(nombrePuerto2);

        if (puerto1 != null && puerto2 != null) {
            puerto1.conectar(puerto2, distancia);
        } else {
            System.out.println("Al menos uno de los puertos no existe.");
        }
    }

    void dfs(String nombreInicio) {
        Puerto inicio = puertos.get(nombreInicio);
        if (inicio == null) {
            System.out.println("El puerto de inicio no existe.");
            return;
        }

        Set<Puerto> visitados = new HashSet<>();
        dfsAux(inicio, visitados);
    }

    private void dfsAux(Puerto puerto, Set<Puerto> visitados) {
        visitados.add(puerto);
        System.out.println("Visitado: " + puerto.nombre);

        for (Map.Entry<Puerto, Integer> conexion : puerto.conexiones.entrySet()) {
            if (!visitados.contains(conexion.getKey())) {
                dfsAux(conexion.getKey(), visitados);
            }
        }
    }

    void dijkstra(String nombreInicio, String nombreDestino) {
        if (!puertos.containsKey(nombreInicio) || !puertos.containsKey(nombreDestino)) {
            System.out.println("Uno o ambos puertos no existen.");
            return;
        }

        final Map<Puerto, Integer> distancias = new HashMap<>();
        PriorityQueue<Puerto> cola = new PriorityQueue<>(Comparator.comparingInt(distancias::get));
        Map<Puerto, Puerto> predecesores = new HashMap<>();
        Set<Puerto> visitados = new HashSet<>();

        Puerto inicio = puertos.get(nombreInicio);
        distancias.put(inicio, 0);
        cola.add(inicio);

        while (!cola.isEmpty()) {
            Puerto actual = cola.poll();
            if (!visitados.add(actual)) continue;

            for (Map.Entry<Puerto, Integer> vecino : actual.conexiones.entrySet()) {
                int nuevaDist = distancias.get(actual) + vecino.getValue();
                if (nuevaDist < distancias.getOrDefault(vecino.getKey(), Integer.MAX_VALUE)) {
                    distancias.put(vecino.getKey(), nuevaDist);
                    predecesores.put(vecino.getKey(), actual);
                    cola.add(vecino.getKey());
                }
            }
        }

        LinkedList<Puerto> camino = new LinkedList<>();
        Puerto destino = puertos.get(nombreDestino);
        for (Puerto at = destino; at != null; at = predecesores.get(at)) {
            camino.push(at);
        }

        if (!camino.isEmpty() && camino.peek().equals(inicio)) {
            System.out.print("El camino más corto es: ");
            while (!camino.isEmpty()) {
                System.out.print(camino.pop().nombre + (camino.size() > 0 ? " -> " : "\n"));
            }
        } else {
            System.out.println("No existe un camino entre " + nombreInicio + " y " + nombreDestino);
        }
    }

    void eliminarPuertoConMasConexiones() {
        Puerto maxPuerto = null;
        int maxConexiones = 0;
        for (Puerto puerto : puertos.values()) {
            if (puerto.conexiones.size() > maxConexiones) {
                maxConexiones = puerto.conexiones.size();
                maxPuerto = puerto;
            }
        }

        if (maxPuerto != null) {
            for (Puerto puerto : puertos.values()) {
                puerto.conexiones.remove(maxPuerto);
            }
            puertos.remove(maxPuerto.nombre);
            System.out.println("Puerto con más conexiones eliminado: " + maxPuerto.nombre);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafo = new Grafo();
        boolean corriendo = true;

        while (corriendo) {
            System.out.println("\nOpciones:");
            System.out.println("1 - Agregar puerto");
            System.out.println("2 - Conectar puertos");
            System.out.println("3 - Realizar DFS desde un puerto");
            System.out.println("4 - Encontrar camino más corto (Dijkstra)");
            System.out.println("5 - Eliminar puerto con más conexiones");
            System.out.println("6 - Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Nombre del nuevo puerto: ");
                    String nuevoPuerto = scanner.nextLine();
                    grafo.agregarPuerto(nuevoPuerto);
                    break;
                case "2":
                    System.out.print("Nombre del puerto origen: ");
                    String origen = scanner.nextLine();
                    System.out.print("Nombre del puerto destino: ");
                    String destino = scanner.nextLine();
                    System.out.print("Distancia entre puertos: ");
                    int distancia = Integer.parseInt(scanner.nextLine());
                    grafo.conectar(origen, destino, distancia);
                    break;
                case "3":
                    System.out.print("Nombre del puerto para iniciar DFS: ");
                    String inicioDFS = scanner.nextLine();
                    grafo.dfs(inicioDFS);
                    break;
                case "4":
                    System.out.print("Puerto de origen para Dijkstra: ");
                    String inicioDijkstra = scanner.nextLine();
                    System.out.print("Puerto de destino para Dijkstra: ");
                    String finDijkstra = scanner.nextLine();
                    grafo.dijkstra(inicioDijkstra, finDijkstra);
                    break;
                case "5":
                    grafo.eliminarPuertoConMasConexiones();
                    break;
                case "6":
                    corriendo = false;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }
}
