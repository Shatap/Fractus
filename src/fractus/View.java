package fractus;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;


public class View extends Application {

	final double WINDOW_HEIGHT = 1920.;
	final double WINDOW_WIDTH = 1080.;
	final double RECT_HEIGHT = WINDOW_HEIGHT;
	final double RECT_WIDTH = WINDOW_WIDTH/5;
	final double BUTTON_SIZE = RECT_WIDTH/6;
	final double BUTTON_Y = 100.;
	final double SPHERE_RADIUS = 400.; 
	final double SPHERE_X = RECT_WIDTH+(WINDOW_WIDTH-RECT_WIDTH)/2;
	final double SPHERE_Y = WINDOW_WIDTH/2;
	final double BUT_ZOOM_X =0.25*((RECT_WIDTH)/2-BUTTON_SIZE/2);
	final double BUT_PLAY_X =(RECT_WIDTH)/2-BUTTON_SIZE/2;
	final double BUT_PAUSE_X =1.75*((RECT_WIDTH)/2-BUTTON_SIZE/2);
	final double ELEMENT_HEIGHT=20.;
	final double ELEMENT_X=20.;
	final double FRACTALE_TYPE_Y=190.;
	final double NB_ITERATION_Y=270.;
	final double COLOR_PICKER_Y=350;
	final double COLOR_PICKER_HEIGHT = 285.;
	final double SHIFT=10.;




	final FractaleControler fracControl = new FractaleControler(new FractaleModele());

	Group group = new Group();
	Scene scene = new Scene(group,600,800);
	Globe globe = new Globe(SPHERE_RADIUS,SPHERE_X,SPHERE_Y,scene,fracControl.getImage());
	final Rectangle rectangle = new Rectangle(0,0,RECT_WIDTH,RECT_HEIGHT);
	Button pauseButton = new Button("||");
	Button playButton = new Button("|>");
	Button zoomButton = new Button("+");
	ObservableList<String> options = 
			FXCollections.observableArrayList(
					"Mandelbrot",
					"Option 2"
					);
	Button[] buttons = {zoomButton,pauseButton,playButton};
	double[] buttonX = {BUT_ZOOM_X,BUT_PAUSE_X,BUT_PLAY_X};
	final ComboBox<String> fractaleType = new ComboBox<String>(options);
	PhongMaterial phongMaterial = new PhongMaterial();

	final Label textIterWarningLabel = new Label();
	final Label nbIterationLabel = new Label();
	final Label fractaleTypeLabel = new Label();
	final Label colorLabel = new Label();
	final Label colorInsideLabel = new Label();
	TextField nbIteration = new TextField();
	ColorPicker colorPicker = new ColorPicker();
	ColorPicker colorInsidePicker = new ColorPicker();
	Button resetButton = new Button("Cancel");




	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Fractus				|				Tricha-Jolliet 	S3A");
		primaryStage.setHeight(WINDOW_HEIGHT);
		primaryStage.setWidth(WINDOW_WIDTH);
		primaryStage.setScene(scene);

		globe.handleScroll();

		for(Button button : buttons)
		{
			button.setMinSize(BUTTON_SIZE,BUTTON_SIZE);
			button.setLayoutY(BUTTON_Y);
		}
		for(int num=0;num<buttonX.length;num++)
			buttons[num].setLayoutX(buttonX[num]);

		fractaleTypeLabel.setText("Fractale Type :");
		fractaleTypeLabel.setLayoutY(FRACTALE_TYPE_Y-ELEMENT_HEIGHT);
		fractaleTypeLabel.setLayoutX(ELEMENT_X);
		fractaleType.setLayoutY(FRACTALE_TYPE_Y);
		fractaleType.setLayoutX(ELEMENT_X);
		fractaleType.setValue((String) options.toArray()[0]);

		nbIterationLabel.setText("Iteration number :");
		nbIterationLabel.setLayoutX(ELEMENT_X);
		nbIterationLabel.setLayoutY(NB_ITERATION_Y-ELEMENT_HEIGHT);
		nbIteration.setLayoutX(ELEMENT_X);
		nbIteration.setLayoutY(NB_ITERATION_Y);
		nbIteration.setText("50");
		textIterWarningLabel.setLayoutX(ELEMENT_X);
		textIterWarningLabel.setLayoutY(NB_ITERATION_Y+ELEMENT_HEIGHT+SHIFT);

		colorLabel.setText("Color :");
		colorLabel.setLayoutX(ELEMENT_X);
		colorLabel.setLayoutY(COLOR_PICKER_Y-ELEMENT_HEIGHT);		
		colorPicker.setLayoutX(ELEMENT_X);
		colorPicker.setLayoutY(COLOR_PICKER_Y);
		colorPicker.setValue(Color.GREEN);

		colorInsideLabel.setText("Color inside :");
		colorInsideLabel.setLayoutX(ELEMENT_X);
		colorInsideLabel.setLayoutY(COLOR_PICKER_Y+COLOR_PICKER_HEIGHT-ELEMENT_HEIGHT);		
		colorInsidePicker.setLayoutX(ELEMENT_X);
		colorInsidePicker.setLayoutY(COLOR_PICKER_Y+COLOR_PICKER_HEIGHT);
		colorInsidePicker.setValue(Color.BLACK);

		resetButton.setLayoutX(ELEMENT_X);
		resetButton.setLayoutY(COLOR_PICKER_Y+2*COLOR_PICKER_HEIGHT+4*SHIFT);


		rectangle.setFill(Color.GREY);

		group.getChildren().add(globe.getSphere());
		group.getChildren().add(rectangle);
		group.getChildren().add(pauseButton);
		group.getChildren().add(playButton);
		group.getChildren().add(zoomButton);
		group.getChildren().add(fractaleType);
		group.getChildren().add(nbIterationLabel);
		group.getChildren().add(nbIteration);
		group.getChildren().add(textIterWarningLabel);
		group.getChildren().add(fractaleTypeLabel);
		group.getChildren().add(colorPicker);
		group.getChildren().add(colorLabel); 
		group.getChildren().add(colorInsidePicker);
		group.getChildren().add(colorInsideLabel); 
		group.getChildren().add(resetButton);

		phongMaterial.setDiffuseMap(fracControl.getImage());


		globe.getSphere().setMaterial(phongMaterial);
		rotateAroundYAxis(globe.getSphere()).play();
		globe.handleRotationEvents();

		actionEventManagement();

		primaryStage.show();

	}



	private RotateTransition rotateAroundYAxis(Sphere s) {
		RotateTransition rotate = new RotateTransition(Duration.seconds(25), s);
		rotate.setFromAngle(360);
		rotate.setToAngle(0);
		rotate.setAxis(Rotate.Y_AXIS);
		rotate.setCycleCount(RotateTransition.INDEFINITE);
		rotate.setInterpolator(Interpolator.LINEAR);
		pauseButton.setOnAction((ActionEvent e)->{rotate.pause();});
		playButton.setOnAction((ActionEvent e)-> {rotate.play();});

		return rotate;
	}

	private void actionEventManagement(){
		//Iteration's number 
		nbIteration.setOnAction((ActionEvent e) -> {
			try{
				Integer it = Integer.parseInt(nbIteration.getText());
				textIterWarningLabel.setText(null);	     
				fracControl.setNbIteration(it);
				phongMaterial.setDiffuseMap(fracControl.getImage());

			}catch (NumberFormatException ex){
				textIterWarningLabel.setText("Not an integer!");
			}
		});
		//color
		colorPicker.setOnAction((ActionEvent event)->{
			fracControl.setCurrentColor(colorPicker.getValue());
			phongMaterial.setDiffuseMap(fracControl.getImage());
		});
		colorInsidePicker.setOnAction((ActionEvent event)->{
			fracControl.setInsideColor(colorInsidePicker.getValue());
			phongMaterial.setDiffuseMap(fracControl.getImage());
		});
		//zoom
		zoomButton.setOnAction((ActionEvent e)->{
			fracControl.setZoom(0,0);
			phongMaterial.setDiffuseMap(fracControl.getImage());
			globe.getSphere().setRadius(460);
			});			
		// fractal type
		fractaleType.setOnAction((ActionEvent e)->{
			fracControl.setFractaleType(fractaleType.getValue());
			phongMaterial.setDiffuseMap(fracControl.getImage());
			});
		// reset
		resetButton.setOnAction((ActionEvent e )->{
			fracControl.reset();
			phongMaterial.setDiffuseMap(fracControl.getImage());
		});
		


	}

	public static void main(String[] args)
	{
		Application.launch(args);
	}

}
