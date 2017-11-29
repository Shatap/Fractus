package fractus;



import java.util.Observable;

import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


public class FractaleModele extends Observable {	
	ImageView img = new ImageView();

	private Picture pic ;
	private int resoX = 1020 ;
	private int resoY = 480; 
	private int zoom = 200 ;
	private int max_it = 50;
	private int x,y;
	private double x1 = -2.1;
	private double x2 = 3.0;
	private double y1 = -1.2;
	private double y2 = 1.2;
	private Color currentColor = Color.GREEN;
	private Color colorInside = Color.BLACK;	
	private int xOffset =10;
	private int yOffset = 10;

	private WritableImage image = new WritableImage(resoX,resoY);

	private String fractalType="Mandelbrot";


	public FractaleModele() {
	}	

	public void createMandelBrot() {

		//https://stackoverflow.com/questions/14097559/zooming-in-on-mandelbrot-set-fractal-in-java


		for(int x = 0 ; x < 1020;x++)
		{
			for(int y = 0; y < 480 ; y++) 
			{
				double c_r = (x+xOffset)/((double)zoom)+x1 ;
				double c_i = (y+yOffset)/((double)zoom)+y1 ;
				double z_r = 0;
				double z_i = 0;
				double i = 0;   

				do
				{
					double tmp = z_r ;
					z_r = Math.pow(z_r,2) - Math.pow(z_i,2) + c_r ;
					z_i= 2*z_i*tmp + c_i;
					++i ;
				}
				while((Math.pow(z_r,2)) + (Math.pow(z_i,2)) < 4 && i <max_it);


				if(i == max_it) {

					image.getPixelWriter().setColor(x,y,colorInside);
				}
				else
				{

					image.getPixelWriter().setColor(x, y,currentColor);

				}    			   			

			}
		}


	}


	public  void setResolutionX(double reso) {
		this.resoX *= reso;
		setChanged();
		notifyObservers();
	}
	public  void setResolutionY(double reso) {
		this.resoY *= reso;
		setChanged();
		notifyObservers();
	}   
	public void setNbIteration(int nbiterations){
		max_it=nbiterations;
	}

	public void setZoom(int zoomX,int zoomY) {
		zoom *=1.6 ;
		x = zoomX;
		y=zoomY;
	}


	public Color getCurrentColor() {
		return currentColor;
	}

	public void setFractaleType(String type) {
		fractalType=type;
	}

	public String getFractalType() {
		return fractalType;
	}

	public void setInsideColor(Color value) {
		colorInside=value;
	}

	public void setCurrentColor(Color value) {
		currentColor=value;		
	}

	public WritableImage getImage() {
		return image;
	}

	public void createOtherFractale() {
		for(int x = 0 ; x < 1020;x++)
		{
			for(int y = 0; y < 480 ; y++) 
			{
				image.getPixelWriter().setColor(x,y,colorInside);
			}
		}
	}

	public double getResoY() {
		return resoY;
	}

	public double getResoX() {
		// TODO Auto-generated method stub
		return resoX;
	}




}
