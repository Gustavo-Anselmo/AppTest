// src/main/java/com/example/app_sus/observer/MedicoObservavel.java
package com.example.app_sus.observer;

public interface MedicoObservavel {
    void adicionarObservador(MedicoObservador observador);
    void removerObservador(MedicoObservador observador);
    void notificarObservadores();
}