package br.com.javafxpaint.pinceis;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Borracha extends Pincel {

    private static Borracha instancia;

    public static synchronized Borracha getInstance() {
        if (instancia == null) {
            instancia = new Borracha();
        }
        return instancia;
    }

    private Borracha() {
        inicializarPincel();
    }

    @Override
    public void desenhar(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        final double espessura = tamanhoPincelAtual;
        final double centro = Math.round(espessura / 2);
        gc.clearRect(x1 - centro, y1 - centro, espessura, espessura);
    }

    @Override
    public final void inicializarPincel() {
        tamanhoPincelAtual = 10;
        cursor = new Image("img/borracha.png");
    }
}
