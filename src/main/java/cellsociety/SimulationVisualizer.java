package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SimulationVisualizer {

  private final int FRAMES_PER_SECOND = 60;
  private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

  private boolean animationEnabled = false;
  private Circle ball;
  private Button playButton;
  private Button pauseButton;
  private Button stepButton;

  public SimulationVisualizer() {
  }

  public Scene setUpScene(int width, int height) {
    ball = new Circle(450, 250, 20);
    ball.setFill(Color.LIGHTSTEELBLUE);

    Rectangle tempGrid = new Rectangle(width * 0.25, 0, width * 0.75, height * 0.75);

    BorderPane root = new BorderPane();

    root.setBottom(createAllMenuControls());
    root.setRight(tempGrid);
    root.getChildren().add(ball);
    Scene scene = new Scene(root, width, height, Color.DARKBLUE);

    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      if (animationEnabled) {
        move(SECOND_DELAY);
      }
    });
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
    return scene;
  }

  //PLACEHODLER ANIMATION
  static Point2D ballSpeed = new Point2D(500, 0);

  public void move(double elapsedTime) {

    ball.setCenterX(ball.getCenterX() + ballSpeed.getX() * elapsedTime);
    if (ball.getCenterX() > 700) {
      ballSpeed = new Point2D(-500, 0);
    }
    if (ball.getCenterX() < 175) {
      ballSpeed = new Point2D(500, 0);
    }
  }

  private HBox createAllMenuControls() {
    playButton = makeButton("Play", e -> play());
    pauseButton = makeButton("Pause", e -> pause());
    stepButton = makeButton("Step", e -> step());

    HBox result = new HBox();
    result.getChildren().addAll(pauseButton, playButton, stepButton);
    result.setAlignment(Pos.CENTER);
    return result;
  }

  private Button makeButton(String buttonName, EventHandler<ActionEvent> handler) {
    Button button = new Button();

    button = new Button();
    button.setText(buttonName);
    button.setOnAction(handler);

    return button;
  }

  public void play() {
    animationEnabled = true;
  }

  public void pause() {
    animationEnabled = false;
  }

  public void step() {
    move(SECOND_DELAY);
  }

}
