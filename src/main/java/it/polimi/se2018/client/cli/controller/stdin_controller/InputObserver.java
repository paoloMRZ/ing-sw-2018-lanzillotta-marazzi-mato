package it.polimi.se2018.client.cli.controller.stdin_controller;

/**
 * Interfacci che deve essere implementata dall'osservatore della classe Input.
 */
public interface InputObserver {

    void inputRequest(int request);
}
