package org.example;

public class Ship {
    protected int size;
    protected int hits;

    public Ship(int size) {
        this.size = size;
        this.hits = 0;
    }

    public boolean hit() {
        this.hits++;
        return this.isSunk();
    }

    public boolean isSunk() {
        return this.hits >= this.size;
    }
}
