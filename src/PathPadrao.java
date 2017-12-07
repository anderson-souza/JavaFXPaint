
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class PathPadrao extends Path {

    MeusPinceis pinceis;
    int id;

    public PathPadrao() {
        super();
        //InicializarCaneta();
        pinceis = MeusPinceis.getInstance();
        this.setCursor(Cursor.CROSSHAIR);
        setOnMouseClicked(mouseHandler);
        setOnMouseDragEntered(mouseHandler);
        setOnMouseDragExited(mouseHandler);
        setOnMouseDragOver(mouseHandler);
        setOnMouseDragReleased(mouseHandler);
        setOnMouseDragged(mouseHandler);
        setOnMouseEntered(mouseHandler);
        setOnMouseExited(mouseHandler);
        setOnMouseMoved(mouseHandler);
        setOnMousePressed(mouseHandler);
        setOnMouseReleased(mouseHandler);
        System.out.println("PathPadrao.<init>()");
    }

    EventHandler<MouseEvent> mouseHandler = (MouseEvent mouseEvent) -> {
        System.out.println("mouseHandler: " + mouseEvent);
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
            getElements().clear();
            getElements().add(new MoveTo(mouseEvent.getX(), mouseEvent.getY()));
        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            getElements().add(new LineTo(mouseEvent.getX(), mouseEvent.getY()));
        }
    };

    private void InicializarCaneta() { //Função que inicializa algumas preferencias da caneta
        this.setStrokeLineCap(StrokeLineCap.ROUND); //Forma da Linha
        this.setStrokeLineJoin(StrokeLineJoin.ROUND); //Junção da Linha
        this.setFill(Color.BLACK); //Preenchimento das fórmas
        this.setStroke(Color.BLACK); //Cor da linha
        this.setStrokeWidth(1);
    }

    private void Borracha(GraphicsContext gc, double x, double y, double espessura) { //Função da Borracha
        gc.clearRect(x - (espessura / 2), y - (espessura / 2), espessura, espessura);
        //gc.setStroke(gc.getC);
    }

    private void Caneta(GraphicsContext gc, double xInicio, double yInicio, double xFim, double yFim) { //Função da Caneta
        gc.setLineWidth(pinceis.getEspessuraAtual()); //Alterar para um Listener Futuramente
        gc.strokeLine(xInicio, yInicio, xFim, yFim);
        /*velhoX = atualX;
        velhoY = atualY;*/
    }

    private void Lapis(GraphicsContext gc, double xInicio, double yInicio, double xFim, double yFim) { //Função do Lapis *Rever funcionalidades
        /*Color corLapis = pinceis.getCorAtual();
        corLapis.deriveColor(corLapis.getHue(), corLapis.getSaturation(), corLapis.getBrightness(), corLapis.getOpacity() - 0.20);
        mainGc.setStroke(corLapis); //Cor da linha*/

        gc.setLineWidth(pinceis.getEspessuraAtual()); //Alterar para um Listener Futuramente
        gc.strokeLine(xInicio, yInicio, xFim, yFim);
        /*velhoX = atualX;
        velhoY = atualY;*/
    }

    @Override
    public String toString() {
        return "Camada " + id;
    }

    /*private GraphicsContext mainGc;
    double atualX, atualY, velhoX, velhoY;
    MeusPinceis pinceis;
    int id;

    public CanvasPadrao(double largura, double altura, int id) {
        super(largura, altura);
        this.id = id;
        //setCache(true);
        mainGc = getGraphicsContext2D();
        InicializarCaneta();
        pinceis = MeusPinceis.getInstance();
        setCursor(Cursor.CROSSHAIR);

        setOnMousePressed((MouseEvent event) -> { //Evento que será chamado quando o mouse for pressionado
            if (event.isPrimaryButtonDown()) { //Se for o botão primário pressionado executa a operações abaixo
                //gc.setFill(pinceis.getCorAtual());
                AlteraCrossHair();
                mainGc.setStroke(pinceis.getCorAtual());
                velhoX = event.getX();
                velhoY = event.getY();
                atualX = event.getX();
                atualY = event.getY();
                switch (pinceis.getPincelAtual()) {
                    case CANETA:
                        Caneta(mainGc, atualX, atualY, atualX, atualY);
                        break;
                    case BORRACHA:
                        Borracha(mainGc, atualX, atualY, pinceis.getEspessuraAtual());
                        break;
                    case LAPIS:
                        //Lapis(mainGc, atualX, atualY, atualX, atualY);
                        mainGc.moveTo(event.getX() + 0.5, event.getY());
                        break;
                    default:
                        break;
                }
            }
        });
        setOnMouseDragged((MouseEvent event) -> { //Evento que será chamado quando o mouse for arrastado
            if (event.isPrimaryButtonDown()) {
                atualX = event.getX();
                atualY = event.getY();
                switch (pinceis.getPincelAtual()) {
                    case CANETA:
                        Caneta(mainGc, velhoX, velhoY, atualX, atualY);
                        break;
                    case BORRACHA:
                        Borracha(mainGc, atualX, atualY, pinceis.getEspessuraAtual());
                        break;
                    case LAPIS:
                        //Lapis(mainGc, velhoX, velhoY, atualX, atualY);
                        mainGc.lineTo(event.getX() + 0.5, event.getY());
                        mainGc.stroke();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void AlteraCrossHair() {
        switch (pinceis.getPincelAtual()) {
            case CANETA:
                Image imageCaneta = new Image("img/caneta.png");
                setCursor(new ImageCursor(imageCaneta));
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
    }

    private void InicializarCaneta() { //Função que inicializa algumas preferencias da caneta
        mainGc.setLineCap(StrokeLineCap.ROUND); //Forma da Linha
        mainGc.setLineJoin(StrokeLineJoin.ROUND); //Junção da Linha
        mainGc.setFill(Color.BLACK); //Preenchimento das fórmas
        mainGc.setStroke(Color.BLACK); //Cor da linha

        //Efeito para tentar melhorar a qualidade do traçejado
        BoxBlur blur = new BoxBlur();
        blur.setWidth(1);
        blur.setHeight(1);
        blur.setIterations(3);
        mainGc.setEffect(blur);
    }

    private void Borracha(GraphicsContext gc, double x, double y, double espessura) { //Função da Borracha
        gc.clearRect(x - (espessura / 2), y - (espessura / 2), espessura, espessura);
        //gc.setStroke(gc.getC);
    }

    private void Caneta(GraphicsContext gc, double xInicio, double yInicio, double xFim, double yFim) { //Função da Caneta
        gc.setLineWidth(pinceis.getEspessuraAtual()); //Alterar para um Listener Futuramente
        gc.strokeLine(xInicio, yInicio, xFim, yFim);
        velhoX = atualX;
        velhoY = atualY;
    }

    private void Lapis(GraphicsContext gc, double xInicio, double yInicio, double xFim, double yFim) { //Função do Lapis *Rever funcionalidades
        /*Color corLapis = pinceis.getCorAtual();
        corLapis.deriveColor(corLapis.getHue(), corLapis.getSaturation(), corLapis.getBrightness(), corLapis.getOpacity() - 0.20);
        mainGc.setStroke(corLapis); //Cor da linha

        gc.setLineWidth(pinceis.getEspessuraAtual()); //Alterar para um Listener Futuramente
        gc.strokeLine(xInicio, yInicio, xFim, yFim);
        velhoX = atualX;
        velhoY = atualY;
    }

    @Override
    public String toString() {
        return "Camada " + id;
    }*/
}
