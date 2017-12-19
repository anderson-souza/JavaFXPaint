package br.com.javafxpaint.pinceis;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Pincel {

    protected Color corAtual;
    protected double tamanhoPincelAtual;
    protected Image cursor;

    public abstract void desenhar(GraphicsContext gc, double x1, double y1, double x2, double y2);

    protected abstract void inicializarPincel();

}
