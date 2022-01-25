package cellsociety;

import Util.menuController;
import Util.menuControllerPause;
import Util.menuControllerPlay;
import Util.menuControllerStep;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {
    // useful names for constant values used
    public static final String TITLE = "Example JavaFX Animation";
    private static final int SIZE_HORIZONTAL = 700;
    private static final int SIZE_VERTICAL = 500;
    private static final int BUTTON_EDGE_GAP = 50;
    private static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private String[] BUTTON_NAMES = {"Play","Pause","Step"};
    private double[][] BUTTON_POSITIONS = {{SIZE_HORIZONTAL*0.25,SIZE_VERTICAL*0.75+BUTTON_EDGE_GAP},
                                            {SIZE_HORIZONTAL*0.25+BUTTON_EDGE_GAP,SIZE_VERTICAL*0.75+BUTTON_EDGE_GAP},
                                            {SIZE_HORIZONTAL*0.25+2*BUTTON_EDGE_GAP,SIZE_VERTICAL*0.75+BUTTON_EDGE_GAP}};
    public static boolean animationEnabled =false;
    static Circle ball;
    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start (Stage stage) {
        ball = new Circle(450, 250, 20);
        ball.setFill(Color.LIGHTSTEELBLUE);

        Rectangle tempGrid= new Rectangle(SIZE_HORIZONTAL*0.25,0,SIZE_HORIZONTAL*0.75,SIZE_VERTICAL*0.75);

        Group root = new Group();

        createAndAddAllMenuControlsToRoot(root);
        root.getChildren().add(tempGrid);
        root.getChildren().add(ball);
        Scene scene = new Scene(root, SIZE_HORIZONTAL, SIZE_VERTICAL, Color.DARKBLUE);

        stage.setScene(scene);

        stage.setTitle(TITLE);
        stage.show();

        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {if(animationEnabled){move(SECOND_DELAY);}});
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }
    //PLACEHODLER ANIMATION
    static Point2D ballSpeed = new Point2D(500, 0);
    public static void move(double elapsedTime) {

        ball.setCenterX(ball.getCenterX() + ballSpeed.getX() * elapsedTime);
        if(ball.getCenterX()>700){
        ballSpeed = new Point2D(-500, 0);}
        if(ball.getCenterX()<175){
            ballSpeed = new Point2D(500, 0);}
    }
private void createAndAddAllMenuControlsToRoot(Group root){
    menuController playButton= new menuControllerPlay(BUTTON_NAMES[0],BUTTON_POSITIONS[0][0],BUTTON_POSITIONS[0][1]);
    menuController pauseButton= new menuControllerPause(BUTTON_NAMES[1],BUTTON_POSITIONS[1][0],BUTTON_POSITIONS[1][1]);
    menuController stepButton= new menuControllerStep(BUTTON_NAMES[2],BUTTON_POSITIONS[2][0],BUTTON_POSITIONS[2][1]);
    addButtonToRoot(root,playButton.getButtonNode() );
    addButtonToRoot(root,pauseButton.getButtonNode() );
    addButtonToRoot(root,stepButton.getButtonNode() );
}

    private void addButtonToRoot(Group rootGroup, Button b){
        rootGroup.getChildren().add(b);

    }





}



