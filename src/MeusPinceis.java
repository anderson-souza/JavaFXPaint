
import javafx.scene.paint.Color;

//Classe MeusPinceis é um Singleton que irá cuidar do pincel atual, dos pinceis possiveis e das cores
//É uma classe que é instanciada uma única vez, e sempre que necessário, será chamado sua instância
public class MeusPinceis {

    //Definição do PincelAtual, Getter e Setter
    private Pinceis pincelAtual;

    public Pinceis getPincelAtual() {
        return this.pincelAtual;
    }

    protected void setPincelAtual(Pinceis pincelAtual) {
        this.pincelAtual = pincelAtual;
    }
    //Fim da definição do pincel atual

    //Definição da espessuraAtual
    private double espessuraAtual;

    public double getEspessuraAtual() {
        return espessuraAtual;
    }

    protected void setEspessuraAtual(double espessuraAtual) {
        this.espessuraAtual = espessuraAtual;
    }
    //Fim da definição da espessuraAtual

    //Definição da corAtual
    private Color corAtual;

    public Color getCorAtual() {
        return corAtual;
    }

    protected void setCorAtual(Color corAtual) {
        this.corAtual = corAtual;
    }
    //Fim da Definição da corAtual

    //Definição dos tipos de Pinceis
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
    //Fim dos Tipos de Pinceis

    //Definição da instancia e construção da mesma
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
    //Fim da Definição da instância

}
