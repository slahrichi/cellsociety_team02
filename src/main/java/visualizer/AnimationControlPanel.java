package visualizer;

import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class AnimationControlPanel extends ControlPanel {

  private HBox animationControls;
  private boolean animationEnabled = false;
  private final Timeline myAnimation;
  private final SimulationVisualizer simulationVisualizer;

  public AnimationControlPanel(ResourceBundle resources, Timeline animation,
      SimulationVisualizer s) {
    super(resources);
    this.myAnimation = animation;
    simulationVisualizer = s;
  }

  public boolean getAnimationStatus() {
    return animationEnabled;
  }

  public HBox getAnimationControls() {
    return animationControls;
  }

  public void createAllAnimationControls() {
    Button playButton = makeButton("playCommand", e -> play());
    Button pauseButton = makeButton("pauseCommand", e -> pause());
    Button stepButton = makeButton("stepCommand", e -> step());

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
    simulationVisualizer.updateGrid();
  }

}
