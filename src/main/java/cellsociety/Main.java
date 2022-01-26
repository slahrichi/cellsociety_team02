package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    public static final int SIZE_HORIZONTAL = 700;
    public static final int SIZE_VERTICAL = 500;
    private  final int FRAMES_PER_SECOND = 60;
    private  final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private  final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    private boolean animationEnabled =false;
    private Circle ball;
    private Button playButton;
    private Button pauseButton;
    private Button stepButton;

    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start (Stage stage) {
        ball = new Circle(450, 250, 20);
        ball.setFill(Color.LIGHTSTEELBLUE);

        Rectangle tempGrid= new Rectangle(SIZE_HORIZONTAL*0.25,0,SIZE_HORIZONTAL*0.75,SIZE_VERTICAL*0.75);

        BorderPane root = new BorderPane();

        root.setBottom(createAllMenuControls());
        root.setRight(tempGrid);
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
    public void move(double elapsedTime) {

        ball.setCenterX(ball.getCenterX() + ballSpeed.getX() * elapsedTime);
        if(ball.getCenterX()>700){
            ballSpeed = new Point2D(-500, 0);}
        if(ball.getCenterX()<175){
            ballSpeed = new Point2D(500, 0);}
    }
    private HBox createAllMenuControls(){
        playButton= makeButton("Play",e->play());
        pauseButton=makeButton("Pause",e->pause());
        stepButton= makeButton("Step",e->step());

        HBox result = new HBox();
        result.getChildren().addAll(pauseButton,playButton,stepButton);
        result.setAlignment(Pos.CENTER);
        return result;
    }
    private Button makeButton(String buttonName,EventHandler<ActionEvent> handler){
        Button button=new Button();

            button = new Button();
            button.setText(buttonName);
            button.setOnAction(handler);

        return button;
    }

    public void play(){animationEnabled=true ;}
    public void pause(){animationEnabled=false ;}
    public void step(){move(SECOND_DELAY);}

}



