
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UniparPaint extends Application {

    public PanePadrao mainPane;

    @Override
    public void start(Stage stage) throws Exception {

        GroupPadrao root = GroupPadrao.getInstance();
        Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), true, SceneAntialiasing.BALANCED);
        Node node = FXMLLoader.load(getClass().getResource("BarraFerramentas.fxml"));
        BorderPanePadrao.getInstance().setTop(node);
        root.getChildren().add(BorderPanePadrao.getInstance());

        //Atributos da Cena
        stage.setTitle("Unipar Paint");
        stage.setMaximized(true);
        stage.setScene(scene);
        //ScenicView.show(scene); //Visualizador de Cena, utilizado para Depurar a construção das cenas no JavaFX
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
