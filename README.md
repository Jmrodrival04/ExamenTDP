Este es mi reposito en github: https://github.com/Jmrodrival04/ExamenTDP.git

Ejercicio 5: Extensión del Juego "Hundir la Flota"
Objetivo: Implementar una versión extendida del juego "Hundir la flota" que incluya tres tipos diferentes de barcos (acorazados, fragatas y canoas), cada uno con características únicas en términos de tamaño y resistencia.

Acorazados (Battleship): Barcos grandes y fuertes con un tamaño fijo de 5 posiciones en el tablero. Requieren que todas sus posiciones sean golpeadas para ser hundidos debido a sus contenedores aislados.
Fragatas (Frigate): Barcos medianos con un tamaño fijo de 3 posiciones en el tablero.
Canoas (Canoe): Barcos pequeños que ocupan una sola posición en el tablero.
El juego se expande para permitir a los jugadores utilizar estas variedades de barcos, añadiendo complejidad y estrategia al juego clásico.

Ejercicio 6: Implementación del Módulo Principal del Juego
Objetivo: Desarrollar el módulo principal del juego "Hundir la flota", gestionando la configuración de los barcos en el tablero por parte de los usuarios y la lógica de turnos para atacar posiciones aleatorias hasta que un jugador gane al hundir todos los barcos del oponente.

Configuración de Barcos: Los usuarios posicionan un número limitado de barcos en el tablero al inicio del juego.
Lógica de Turnos: Los jugadores se turnan para atacar posiciones aleatorias en el tablero del oponente. El juego termina cuando todos los barcos de un jugador han sido hundidos, declarando al otro jugador como ganador.
Ejercicio 7: Uso de Tablas Hash para Datos de Barcos
Objetivo: Implementar tres tablas hash para gestionar datos de barcos, optimizando la búsqueda y el acceso a la información basada en diferentes claves: tipo de barco, número del barco y nombre del barco.

Tabla por Tipo de Barco: Clasifica los barcos según su tipo, utilizando funciones de sondeo para manejar colisiones.
Tabla por Número del Barco: Organiza los barcos por un número único, facilitando la búsqueda directa.
Tabla por Nombre del Barco: Permite acceder a los datos de los barcos mediante su nombre.
Esta estructura de datos mejora la eficiencia de las operaciones de búsqueda y actualización de la información de los barcos.

Ejercicio 8: Grafo para Puertos y Conexiones
Objetivo: Crear un grafo no dirigido que represente puertos y las rutas marítimas entre ellos, incluyendo funcionalidades para añadir puertos, conectarlos con aristas que representen las distancias, y realizar operaciones como el barrido en profundidad, la búsqueda del camino más corto entre dos puertos y la eliminación del puerto con mayor número de conexiones.

Añadir y Conectar Puertos: Modelar puertos como nodos en el grafo y las rutas marítimas como aristas que los conectan.
Barrido en Profundidad (DFS): Explorar el grafo para visitar todos los puertos alcanzables desde un puerto inicial.
Camino Más Corto: Utilizar algoritmos como Dijkstra para encontrar la ruta más eficiente entre dos puertos.
Eliminar Puerto con Más Conexiones: Identificar y eliminar el puerto que tenga el mayor número de rutas conectadas.

Todos los ejercicios estan relacionados entre si y se podrian juntar todos en un mismo programa
