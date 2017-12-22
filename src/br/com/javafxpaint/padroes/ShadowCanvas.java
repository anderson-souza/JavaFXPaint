package br.com.javafxpaint.padroes;

import br.com.javafxpaint.pinceis.PincelController;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public final class ShadowCanvas extends Canvas implements Observer {

    private final GraphicsContext graphicsContext;
    private double espessuraPincel;

    public ShadowCanvas(double largura, double altura) {
        super(largura, altura);
        graphicsContext = getCanvasGraphicsContext();
        final PincelController pincelController;
        pincelController = PincelController.getInstance();
        pincelController.addObserver(getInstance());

        setOpacity(0.1);
        setDisable(true);
        inicializarDesenhos(pincelController);

        setOnMouseMoved((MouseEvent event) -> {
            System.out.println("ShadowCanvas MouseMoved");
            graphicsContext.clearRect(0, 0, altura, largura);
            graphicsContext.fillOval(event.getX() - espessuraPincel / 2, event.getY() - espessuraPincel / 2, espessuraPincel, espessuraPincel);
        });
    }

    private void inicializarDesenhos(final PincelController pincelController) {
        //Configurações iniciais dos desenhos
        graphicsContext.setLineWidth(pincelController.getTamanhoPincelAtual());
        graphicsContext.setStroke(pincelController.getCorAtual());
        graphicsContext.setLineCap(StrokeLineCap.ROUND);
        graphicsContext.setLineJoin(StrokeLineJoin.ROUND);
    }

    public final ShadowCanvas getInstance() {
        return this;
    }

    protected GraphicsContext getCanvasGraphicsContext() {
        return getInstance().getGraphicsContext2D();
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof PincelController) {
            final Color novaCor;
            novaCor = ((PincelController) observable).getCorAtual();
            graphicsContext.setStroke(novaCor);
            espessuraPincel = ((PincelController) observable).getTamanhoPincelAtual();
            graphicsContext.setLineWidth(espessuraPincel);
        }
    }
}
