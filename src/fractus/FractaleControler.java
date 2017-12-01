package fractus;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FractaleControler {
	FractaleModele fracModele ;
	public FractaleControler(FractaleModele modele) {	
		fracModele = modele ;


	}
	public void setZoomPlus(int zoomX,int zoomY)
	{
		fracModele.setZoom(zoomX,zoomY,fracModele.getZoomingValue());
	}

	public void setZoomMinus(int zoomX, int zoomY) {
		fracModele.setZoom(zoomX,zoomY,1/fracModele.getZoomingValue());
	}


	public double getResoY() {
		return fracModele.getResolutionY();
	}
	public double getResoX() {
		return fracModele.getResolutionX();
	}
	public void setNbIteration(Integer it) {
		fracModele.setNbIteration(it);		
	}

	public void setFractaleType(String type) {
		fracModele.setFractaleType(type);
	}

	public void setInsideColor(Color value) {
		fracModele.setInsideColor(value);
	}
	public void setCurrentColor(Color value) {
		fracModele.setCurrentColor(value);		

	}
	public WritableImage getImage() {

		fracModele.createFractale();
		return fracModele.getImage();
	}
	public void reset() {
		fracModele.reset();
	}
	public Color getCurrentColor() {
		return fracModele.getCurrentColor();
	}
	public Color getColorInside() {
		return fracModele.getColorInside();
	}
	public int getNbIteration() {
		return fracModele.getNbIteration();
	}




}
