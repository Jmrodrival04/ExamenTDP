package org.example;
import java.util.*;

public class Grafo {
    private Map<String, Nodo> nodos = new HashMap<>();

    private class Nodo {
        String nombre;
        Map<Nodo, Integer> vecinos = new HashMap<>();

        Nodo(String nombre) {
            this.nombre = nombre;
        }
    }

    public void agregarPuerto(String nombre) {
        nodos.putIfAbsent(nombre, new Nodo(nombre));
    }

    public void conectarPuertos(String origen, String destino, int distancia) {
        Nodo nodoOrigen = nodos.get(origen);
        Nodo nodoDestino = nodos.get(destino);
        nodoOrigen.vecinos.put(nodoDestino, distancia);
        nodoDestino.vecinos.put(nodoOrigen, distancia); // Grafo no dirigido
    }

    public void barridoEnProfundidad(String inicio) {
        Nodo nodoInicio = nodos.get(inicio);
        Set<Nodo> visitados = new HashSet<>();
        dfs(nodoInicio, visitados);
    }

    private void dfs(Nodo actual, Set<Nodo> visitados) {
        visitados.add(actual);
        System.out.println("Visitando: " + actual.nombre);
        for (Nodo vecino : actual.vecinos.keySet()) {
            if (!visitados.contains(vecino)) {
                dfs(vecino, visitados);
            }
        }
    }

    public List<String> encontrarCaminoMasCorto(String inicio, String fin) {
        if (!nodos.containsKey(inicio) || !nodos.containsKey(fin)) {
            throw new NoSuchElementException("Los puertos especificados no existen en el grafo.");
        }

        final Map<Nodo, Integer> distancias = new HashMap<>();
        PriorityQueue<Nodo> nodosPorVisitar = new PriorityQueue<>(Comparator.comparingInt(distancias::get));
        Map<Nodo, Nodo> previos = new HashMap<>();
        Nodo inicioNodo = nodos.get(inicio);

        nodos.values().forEach(nodo -> {
            distancias.put(nodo, Integer.MAX_VALUE);
            nodosPorVisitar.add(nodo);
            previos.put(nodo, null);
        });

        distancias.put(inicioNodo, 0);
        nodosPorVisitar.add(inicioNodo);

        while (!nodosPorVisitar.isEmpty()) {
            Nodo actual = nodosPorVisitar.poll();
            for (Map.Entry<Nodo, Integer> vecino : actual.vecinos.entrySet()) {
                int nuevaDist = distancias.get(actual) + vecino.getValue();
                if (nuevaDist < distancias.get(vecino.getKey())) {
                    distancias.put(vecino.getKey(), nuevaDist);
                    previos.put(vecino.getKey(), actual);
                    nodosPorVisitar.add(vecino.getKey());
                }
            }
        }

        List<String> camino = new ArrayList<>();
        for (Nodo at = nodos.get(fin); at != null; at = previos.get(at)) {
            camino.add(at.nombre);
        }
        Collections.reverse(camino);
        return camino;
    }

    public void eliminarPuertoConMasAristas() {
        Nodo nodoAEliminar = nodos.values().stream().max(Comparator.comparingInt(n -> n.vecinos.size())).orElse(null);
        if (nodoAEliminar != null) {
            nodos.values().forEach(nodo -> nodo.vecinos.remove(nodoAEliminar));
            nodos.remove(nodoAEliminar.nombre);
            System.out.println("Puerto eliminado: " + nodoAEliminar.nombre);
        }
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        grafo.agregarPuerto("Puerto Madero");
        grafo.agregarPuerto("Puerto de Rodas");
        grafo.agregarPuerto("Puerto de Valencia");
        grafo.agregarPuerto("Puerto de Barcelona");

        grafo.conectarPuertos("Puerto Madero", "Puerto de Rodas", 5);
        grafo.conectarPuertos("Puerto de Rodas", "Puerto de Valencia", 3);
        grafo.conectarPuertos("Puerto de Valencia", "Puerto de Barcelona", 4);
        grafo.conectarPuertos("Puerto Madero", "Puerto de Barcelona", 10);

        System.out.println("Barrido en profundidad desde Puerto Madero:");
        grafo.barridoEnProfundidad("Puerto Madero");

        System.out.println("\nCamino más corto de Puerto Madero a Puerto de Barcelona:");
        List<String> caminoMasCorto = grafo.encontrarCaminoMasCorto("Puerto Madero", "Puerto de Barcelona");
        System.out.println(String.join(" -> ", caminoMasCorto));

        System.out.println("\nEliminando el puerto con más aristas...");
        grafo.eliminarPuertoConMasAristas();
    }
}
