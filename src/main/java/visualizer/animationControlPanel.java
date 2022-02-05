package visualizer;

import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class animationControlPanel extends controlPanel {
  private HBox animationControls;
  private Button playButton;
  private Button pauseButton;
  private Button stepButton;
  private boolean animationEnabled = false;
  private Timeline myAnimation;
  private SimulationVisualizer sv;

  public animationControlPanel(ResourceBundle resources, Timeline animation,SimulationVisualizer s) {
    super(resources);
    this.myAnimation=animation;
    sv=s;
  }
  public boolean getAnimationStatus(){
    return animationEnabled;
  }

  public HBox getAnimationControls(){return animationControls;}

  public void createAllAnimationControls() {
    playButton = makeButton("playCommand", e -> play());
    pauseButton = makeButton("pauseCommand", e -> pause());
    stepButton = makeButton("stepCommand", e -> step());

    Slider slider = setUpSlider();
    Text text = new Text();
    text.setText(getResourceBundle().getString("animationSpeedPrompt"));
    text.setId("animationSpeedPrompt");

    animationControls = new HBox();
    animationControls.getChildren().addAll(pauseButton, playButton, stepButton, text, slider);
    animationControls.setAlignment(Pos.CENTER);
  }
  private Slider setUpSlider() {
    Slider slider = new Slider();
    slider.setMin(0.1);
    slider.setMax(2);
    slider.setValue(1);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(0.1);
    slider.valueProperty()
        .addListener((observable, oldValue, newValue) -> setAnimationSpeed(newValue));
    return slider;
  }

  private void setAnimationSpeed(Number factor) {
    myAnimation.setRate(factor.doubleValue());
  }

  private void play() {
    animationEnabled = true;
    myAnimation.play();
  }
  public void pause() {
    myAnimation.pause();
  }

  public void step() {
    sv.updateGrid();
  }

}
