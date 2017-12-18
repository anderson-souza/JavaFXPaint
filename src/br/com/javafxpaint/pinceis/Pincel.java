package br.com.javafxpaint.pinceis;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Pincel {

    protected Color corAtual;
    protected double tamanhoPincelAtual;

    public abstract void desenhar(GraphicsContext gc, double x1, double y1, double x2, double y2);

    public abstract void inicializarPincel();

}
