package br.com.javafxpaint.pinceis;

import java.util.Observable;
import javafx.scene.paint.Color;

/**
 * PincelController é um Singleton que só é instanciando uma vez e pode ser
 * utilizado por toda a aplicação. Ele manterá informações sobre os dados atuais
 * dos pincéis, como Cor e Tamanho atual. Esta classe extende de um Observable
 * para alertar as outras classes quando houver modificações.
 *
 * @author EAD-UNIPAR
 */
public class PincelController extends Observable {

    private static PincelController instancia;

    public static synchronized PincelController getInstance() {
        if (instancia == null) {
            instancia = new PincelController();
            instancia.setPincelAtual(Caneta.getInstance());
        }
        return instancia;
    }

    private Pincel pincelAtual;
    private Color corAtual;
    private double tamanhoPincelAtual;

    private PincelController() {

    }

    public synchronized Pincel getPincelAtual() {
        return pincelAtual;
    }

    public void setPincelAtual(Pincel pincelAtual) {
        synchronized (this) {
            this.pincelAtual = pincelAtual;
        }
        setChanged();
        notifyObservers();
    }

    public synchronized Color getCorAtual() {
        return pincelAtual.corAtual;
    }

    public void setCorAtual(Color corAtual) {
        synchronized (this) {
            this.pincelAtual.corAtual = corAtual;
        }
        setChanged();
        notifyObservers();
    }

    public synchronized double getTamanhoPincelAtual() {
        return pincelAtual.tamanhoPincelAtual;
    }

    public void setTamanhoPincelAtual(double tamanhoPincelAtual) {
        synchronized (this) {
            this.pincelAtual.tamanhoPincelAtual = tamanhoPincelAtual;
        }
        setChanged();
        notifyObservers();
    }
}
