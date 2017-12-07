
import javafx.scene.layout.BorderPane;

public class BorderPanePadrao extends BorderPane {

    private static BorderPanePadrao instancia;

    public static synchronized BorderPanePadrao getInstance() {
        if (instancia == null) {
            instancia = new BorderPanePadrao();
        }
        return instancia;
    }

    private BorderPanePadrao() {
        super();
    }
}
