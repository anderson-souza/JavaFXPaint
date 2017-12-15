package br.com.javafxpaint;

import java.util.Observable;
import javafx.scene.paint.Color;

//Classe MeusPinceis é um Singleton que irá cuidar do pincel atual, dos pinceis possiveis e das cores
//É uma classe que é instanciada uma única vez, e sempre que necessário, será chamado sua instância
//Também extende de Observable para notificar quando a cor do pincel ou tamanho do mesmo for modificado
public class MeusPinceis extends Observable {

    private static MeusPinceis instancia;

    public static synchronized MeusPinceis getInstance() {
        if (instancia == null) {
            instancia = new MeusPinceis();
            instancia.setPincelAtual(Pinceis.CANETA);
            instancia.setEspessuraAtual(5);
            instancia.setCorAtual(Color.BLACK);
        }
        return instancia;
    }

    private Pinceis pincelAtual;
    private double espessuraAtual;
    private Color corAtual;

    public synchronized Pinceis getPincelAtual() {
        return this.pincelAtual;
    }

    protected void setPincelAtual(Pinceis pincelAtual) {
        synchronized (this) {
            this.pincelAtual = pincelAtual;
        }
        setChanged();
        notifyObservers();
    }

    public synchronized double getEspessuraAtual() {
        return espessuraAtual;
    }

    protected void setEspessuraAtual(double espessuraAtual) {
        synchronized (this) {
            this.espessuraAtual = espessuraAtual;
        }
        setChanged();
        notifyObservers();
    }

    public synchronized Color getCorAtual() {
        return corAtual;
    }

    protected void setCorAtual(Color corAtual) {
        synchronized (this) {
            this.corAtual = corAtual;
        }
        setChanged();
        notifyObservers();
    }

    static enum Pinceis {
        LAPIS("Lápis"), CANETA("Caneta"), BORRACHA("Borracha");

        public String getNome() {
            return nome;
        }

        final String nome;

        Pinceis(String nome) {
            this.nome = nome;
        }

        @Override
        public String toString() {
            return nome;
        }
    }

}
