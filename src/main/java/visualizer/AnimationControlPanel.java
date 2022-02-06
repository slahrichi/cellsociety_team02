package visualizer;

import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * This class extends the ControlPanel superclass, it is used to create the animation controls,so
 * they can be displayed on the screen.
 * <p>
 * This class depends on javaFX, as well as <class>SimulationVisualizer</class> which calls it. The
 * <class>MenuBarControlPanel</class> and <class>SimulationVisualizer</class> also  depend on this
 * class since it needs the Pause() method.
 *
 * @author Luka Mdivani
 */
public class AnimationControlPanel extends ControlPanel {

  private HBox animationControls;
  private boolean animationEnabled = false;
  private final Timeline myAnimation;

  /**
   * @param resources the resourceBundle used to display texts on the UI.
   * @param animation the main animation, needed here to implement play/pause/step functionalities.
   */
  public AnimationControlPanel(ResourceBundle resources, Timeline animation) {
    super(resources);
    this.myAnimation = animation;
  }

  /**
   * getter function used to check whether animation has started.
   *
   * @return animationEnabled checks whether animation process has been enabled since first launch
   * of the UI.
   */
  public boolean getAnimationStatus() {
    return animationEnabled;
  }

  /**
   * returns the HBox object with all animation controls, to be added to the root.
   *
   * @return HBox of all animation controllers.
   */
  public HBox getAnimationControls() {
    return animationControls;
  }

  /**
   * Creates all the necessary controls for the simulation animation when called.
   *
   * @param mySimulationVisualizer the simulationVisualizer instance used to make a call to the
   *                               UpdateGrid() method in that class
   */
  public void createAllAnimationControls(SimulationVisualizer mySimulationVisualizer) {
    Button playButton = makeButton("playCommand", e -> play());
    Button pauseButton = makeButton("pauseCommand", e -> pause());
    Button stepButton = makeButton("stepCommand", e -> step(mySimulationVisualizer));

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

  /**
   * pauses the simulation animation.
   */
  public void pause() {
    myAnimation.pause();
  }

  /**
   * calls the step method to update the timeline by 1 step.
   *
   * @param mySimulationVisualizer the instance of the SimulationVisualizer where this
   *                               AnimationControlPanel was created.
   */
  public void step(SimulationVisualizer mySimulationVisualizer) {
    mySimulationVisualizer.updateGrid();
  }

}
