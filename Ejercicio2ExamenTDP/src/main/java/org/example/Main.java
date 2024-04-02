package org.example;
import java.util.Scanner;
import java.util.Random;

class Player {
    private Board board;

    public Player() {
        this.board = new Board();
    }

    public void configureShips(Scanner scanner) {
        System.out.println("Configurando barcos para " + this);
        board.placeShipsAtRandom();
    }

    public boolean hasShipsRemaining() {
        return board.hasShipsRemaining();
    }

    public void attack(Player opponent) {
        Random rand = new Random();
        int row = rand.nextInt(Board.SIZE);
        int column = rand.nextInt(Board.SIZE);
        opponent.board.receiveAttack(row, column);
    }

    @Override
    public String toString() {
        return "Player@" + Integer.toHexString(hashCode());
    }
}

class Board {
    static final int SIZE = 10;
    private boolean[][] ships;
    private boolean[][] attacks;

    public Board() {
        ships = new boolean[SIZE][SIZE];
        attacks = new boolean[SIZE][SIZE];
    }

    public void placeShipsAtRandom() {
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            ships[rand.nextInt(SIZE)][rand.nextInt(SIZE)] = true;
        }
    }

    public boolean hasShipsRemaining() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (ships[row][col] && !attacks[row][col]) {
                    return true;
                }
            }
        }
        return false;
    }

    public void receiveAttack(int row, int column) {
        attacks[row][column] = true;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Player player1 = new Player();
        Player player2 = new Player();

        System.out.println("Jugador 1, por favor configure sus barcos:");
        player1.configureShips(scanner);
        System.out.println("Jugador 2, por favor configure sus barcos:");
        player2.configureShips(scanner);

        boolean gameRunning = true;
        while (gameRunning) {
            // Jugador 1 ataca a Jugador 2
            if (player1.hasShipsRemaining() && player2.hasShipsRemaining()) {
                player1.attack(player2);
            } else {
                gameRunning = false;
                break;
            }

            if (player1.hasShipsRemaining() && player2.hasShipsRemaining()) {
                player2.attack(player1);
            } else {
                gameRunning = false;
                break;
            }
        }

        if (!player1.hasShipsRemaining() && !player2.hasShipsRemaining()) {
            System.out.println("El juego termina en empate.");
        } else if (!player1.hasShipsRemaining()) {
            System.out.println("Jugador 2 gana!");
        } else {
            System.out.println("Jugador 1 gana!");
        }

        scanner.close();
    }
}
