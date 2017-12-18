package br.com.javafxpaint.padroes;

import javafx.scene.Group;

public class GroupPadrao extends Group {

    private static GroupPadrao instancia;

    public static synchronized GroupPadrao getInstance() {
        if (instancia == null) {
            instancia = new GroupPadrao();
        }
        return instancia;
    }

    private GroupPadrao() {
        super();
    }
}
