package br.com.javafxpaint;

import br.com.javafxpaint.pinceis.PincelController;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class DrawCanvas extends Canvas implements Observer {

    private final GraphicsContext graphicsContext;
    private final int id;
    private double atualX, atualY, velhoX, velhoY, espessuraPincel;

    public DrawCanvas(double largura, double altura, int id) {
        super(largura, altura);
        this.id = id;
        graphicsContext = getCanvasGraphicsContext();
        PincelController pincelController = PincelController.getInstance();
        pincelController.addObserver(getInstance());

        graphicsContext.setLineWidth(pincelController.getTamanhoPincelAtual());
        graphicsContext.setStroke(pincelController.getCorAtual());
        graphicsContext.setLineCap(StrokeLineCap.ROUND);
        graphicsContext.setLineJoin(StrokeLineJoin.ROUND);

        setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown()) {
                velhoX = event.getX();
                velhoY = event.getY();
                atualX = event.getX();
                atualY = event.getY();
                graphicsContext.beginPath();
                pincelController.getPincelAtual().desenhar(graphicsContext, atualX, atualY, atualX, atualY);
            }
        });

        setOnMouseDragged((MouseEvent event) -> {
            if (event.isPrimaryButtonDown()) {
                atualX = event.getX();
                atualY = event.getY();
                pincelController.getPincelAtual().desenhar(graphicsContext, velhoX, velhoY, atualX, atualY);
                velhoX = atualX;
                velhoY = atualY;
            }
        });

        setOnMouseReleased((MouseEvent event) -> {
            graphicsContext.closePath();
        });
    }

    public final DrawCanvas getInstance() {
        return this;
    }

    private GraphicsContext getCanvasGraphicsContext() {
        return this.getGraphicsContext2D();
    }

    //TODO
    //Alterar cursor
    //-----
    @Override
    public String toString() {
        return "Camada " + id;
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof PincelController) {
            Color novaCor = ((PincelController) observable).getCorAtual();
            graphicsContext.setStroke(novaCor);
            espessuraPincel = ((PincelController) observable).getTamanhoPincelAtual();
            graphicsContext.setLineWidth(espessuraPincel);
        }
    }
}
