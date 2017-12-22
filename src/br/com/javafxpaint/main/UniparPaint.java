package br.com.javafxpaint.main;

import br.com.javafxpaint.padroes.BorderPanePadrao;
import br.com.javafxpaint.padroes.GroupPadrao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UniparPaint extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    Scene scene;
    Node node;

    @Override
    public void start(Stage stage) throws Exception {

        final GroupPadrao group = GroupPadrao.getInstance();
        scene = new Scene(group, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), true, SceneAntialiasing.BALANCED);
        group.getChildren().add(BorderPanePadrao.getInstance());
        node = FXMLLoader.load(getClass().getResource("/br/com/javafxpaint/fxml/BarraFerramentas.fxml"));
        //Atributos da Cena
        BorderPanePadrao.getInstance().setTop(node);
        stage.setTitle("Unipar EAD Paint");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.getIcons().add(new Image("img/logo_mini_unipar_icon.png"));
        //ScenicView.show(scene); //Visualizador de Cena, utilizado para Depurar a construção das cenas no JavaFX
        stage.show();

    }

    @Override
    public void stop() {
        System.exit(0);
    }
}
