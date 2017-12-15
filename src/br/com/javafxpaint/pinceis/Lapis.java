package br.com.javafxpaint.pinceis;

import javafx.scene.canvas.GraphicsContext;

/**
 * Singleton
 *
 * @author EAD-UNIPAR
 */
public class Lapis implements Pincel {

    private static Lapis instancia;

    public static synchronized Lapis getInstance() {
        if (instancia == null) {
            instancia = new Lapis();
        }
        return instancia;
    }

    private Lapis() {
    }

    @Override
    public void desenhar(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        gc.lineTo(x2, y2);
        gc.stroke();
    }
}
