package br.com.javafxpaint.pinceis;

import javafx.scene.canvas.GraphicsContext;

public interface Pincel {

    void desenhar(GraphicsContext gc, double x1, double y1, double x2, double y2);

}
