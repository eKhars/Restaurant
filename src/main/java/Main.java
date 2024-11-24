import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Main extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Restaurant Simulator");
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        // Crear una mesa (rectángulo marrón)
        Entity table = FXGL.entityBuilder()
                .at(300, 300)
                .view(new Rectangle(50, 50, Color.BROWN))
                .buildAndAttach();

        // Crear un mesero (rectángulo azul)
        Entity waiter = FXGL.entityBuilder()
                .at(100, 100)
                .view(new Rectangle(30, 30, Color.BLUE))
                .buildAndAttach();

        // Crear un cliente (rectángulo verde)
        Entity customer = FXGL.entityBuilder()
                .at(200, 200)
                .view(new Rectangle(30, 30, Color.GREEN))
                .buildAndAttach();

        // Añadir texto explicativo
        FXGL.getGameScene().addUINode(new Text(10, 20, "Mesa (Marrón)"));
        FXGL.getGameScene().addUINode(new Text(10, 40, "Mesero (Azul)"));
        FXGL.getGameScene().addUINode(new Text(10, 60, "Cliente (Verde)"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}