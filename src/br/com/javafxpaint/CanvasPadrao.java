package br.com.javafxpaint;

import java.util.Observable;
import java.util.Observer;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class CanvasPadrao extends Canvas implements Observer {

    private GraphicsContext mainGc;
    private double atualX, atualY, velhoX, velhoY, espessuraPincel;
    private final int id;
    private MeusPinceis.Pinceis pincelAtual;
    MeusPinceis pinceis;

    public CanvasPadrao(double largura, double altura, int id) {
        super(largura, altura);
        this.id = id;
        mainGc = getCanvasGraphicsContext();
        pinceis = MeusPinceis.getInstance();
        pinceis.addObserver(this);
        InicializarDesenho(mainGc);
        setCursor(Cursor.CROSSHAIR);

        setOnMousePressed((MouseEvent event) -> { //Evento que será chamado quando o mouse for pressionado
            if (event.isPrimaryButtonDown()) { //Se for o botão primário pressionado executa a operações abaixo
                velhoX = event.getX();
                velhoY = event.getY();
                atualX = event.getX();
                atualY = event.getY();
                mainGc.beginPath();
                switch (pincelAtual) {
                    case CANETA:
                        Caneta(mainGc, atualX, atualY, atualX, atualY);
                        break;
                    case BORRACHA:
                        Borracha(mainGc, atualX, atualY, espessuraPincel);
                        break;
                    case LAPIS:
                        //Lapis(mainGc, atualX, atualY, atualX, atualY);
                        mainGc.moveTo(atualX, atualY);
                        mainGc.stroke();
                        break;
                    default:
                        break;
                }
            }
        });
        setOnMouseDragged((MouseEvent event) -> {
            //Evento que será chamado quando o mouse for arrastado
            if (event.isPrimaryButtonDown()) {
                atualX = event.getX();
                atualY = event.getY();
                switch (pincelAtual) {
                    case BORRACHA:
                        Borracha(mainGc, atualX, atualY, espessuraPincel);
                        break;
                    case CANETA:
                        Caneta(mainGc, velhoX, velhoY, atualX, atualY);
                        break;
                    case LAPIS:
                        //Lapis(mainGc, velhoX, velhoY, atualX, atualY);
                        mainGc.lineTo(atualX, atualY);
                        mainGc.stroke();
                        break;
                    default:
                        break;
                }
            }
        });

        setOnMouseReleased((MouseEvent event) -> { //Evento que será chamado quando o mouse for liberado
            mainGc.closePath();
        });
    }

    private GraphicsContext getCanvasGraphicsContext() {
        return this.getGraphicsContext2D();
    }

    //TODO
    //Alterar o ícone do cursor baseando-se no pincel selecionado
    /*public void AlteraCursor() {
        switch (pincelAtual) {
            case CANETA:
                Image imageCaneta = new Image("img/caneta.png");
                setCursor(new ImageCursor(imageCaneta, imageCaneta.getWidth() / 2 + 1, imageCaneta.getHeight() / 2 + 1));
                break;
            case BORRACHA:
                Image imageBorracha = new Image("img/borracha.png");
                setCursor(new ImageCursor(imageBorracha));
                break;
            case LAPIS:
                break;
            default:
                setCursor(Cursor.CROSSHAIR);
                break;
        }
    }*/
    private void InicializarDesenho(GraphicsContext gc) { //Função que inicializa algumas preferencias para o desenho
        pincelAtual = MeusPinceis.Pinceis.CANETA;
        gc.setStroke(pinceis.getCorAtual());
        gc.setLineWidth(pinceis.getEspessuraAtual());
        espessuraPincel = pinceis.getEspessuraAtual();
        gc.setLineCap(StrokeLineCap.ROUND); //Forma da Linha
        gc.setLineJoin(StrokeLineJoin.ROUND); //Junção da Linha
        gc.setFill(Color.BLACK); //Preenchimento das fórmas
        //AplicarEfeito(mainGc);
    }

    private void Borracha(GraphicsContext gc, double x, double y, double espessura) { //Função da Borracha
        gc.clearRect(x - (espessura / 2), y - (espessura / 2), espessura, espessura);
    }

    private void Caneta(GraphicsContext gc, double xInicio, double yInicio, double xFim, double yFim) { //Função da Caneta
        gc.strokeLine(xInicio, yInicio, xFim, yFim);
        velhoX = atualX;
        velhoY = atualY;
    }

    private void Lapis(GraphicsContext gc, double xInicio, double yInicio, double xFim, double yFim) { //Função do Lapis *Rever funcionalidades
        gc.setLineWidth(pinceis.getEspessuraAtual()); //Alterar para um Listener Futuramente
        gc.strokeLine(xInicio, yInicio, xFim, yFim);
        velhoX = atualX;
        velhoY = atualY;
    }

    private void AplicarEfeito(GraphicsContext GcContext) {
        /*BoxBlur blur = new BoxBlur();
        blur.setWidth(1);
        blur.setHeight(1);
        blur.setIterations(3);
        GcContext.setEffect(blur);*/
    }

    @Override
    public String toString() {
        return "Camada " + id;
    }

    @Override
    public void update(Observable o, Object arg) { //Função que é executada sempre que algum parâmetro da classe MeusPinceis é executada
        Color newColor = ((MeusPinceis) o).getCorAtual();
        mainGc.setStroke(newColor);
        espessuraPincel = ((MeusPinceis) o).getEspessuraAtual();
        mainGc.setLineWidth(espessuraPincel);
        pincelAtual = ((MeusPinceis) o).getPincelAtual();
    }
}
