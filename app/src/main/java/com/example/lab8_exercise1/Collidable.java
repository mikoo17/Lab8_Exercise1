package com.example.lab8_exercise1;

public interface Collidable {
    float getX(); // Środek X
    float getY(); // Środek Y
    float getCollisionSize(); // Rozmiar używany do sprawdzania kolizji (np. promień lub połowa długości boku)
    void reverseY(); // Metoda do odwracania ruchu w osi Y
}
