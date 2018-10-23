package opengl.model;

import java.awt.Point;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Point3d;

import opengl.Config;
import lombok.Getter;
import lombok.Setter;

import com.jogamp.opengl.util.gl2.GLUT;


public abstract class GraphicObject {
	@Getter @Setter
	private Point3d location;
	@Getter @Setter
	private Point3d location_direction;
	@Getter @Setter
	private float scale;

	@Getter @Setter
	private int rotation;
	@Getter @Setter
	private Point3d rotation_direction;
	
	private boolean visable;
	private boolean animate;

	@Getter @Setter
	protected static GL2 gl;
	@Getter @Setter
	protected static GLU glu;
	@Getter @Setter
	protected static GLUT glut;
	@Getter @Setter
	protected static Config c;
	
	public GraphicObject() {
		location = new Point3d(0,0,0);
		location_direction = new Point3d(0,1,0);
		scale = 1;
		rotation = 0;
		rotation_direction = new Point3d(1,0,0);
		visable = true;
		animate = false;
	}

	/**
	 * uruchamiane w chwili uruchomienia openGL
	 */
	public abstract void init();
	
	/**
	 * wykonuj cyklicznie zgodnie z Config.FPS (uruchamiane z Config.prepareDisplay())
	 */
	public abstract void animate();
	
	/**
	 * czy punkt jest w srodku obiektu
	 * @param p
	 * @return
	 */
	public abstract boolean isInMiddle(Point3d p);

	/**
	 * wzgledne przesuniecie myszki x i y, wylicz wektor przesuniecia i update location
	 * @param xystep przesuniecie myszki drag&drop
	 */
	public void updateLocation(Point xystep) {
		double a_step = (double)xystep.x / 50.0;
		double b_step = (double)xystep.y / 50.0;
		if ( location_direction.x > 0 ) {
			location.y -= b_step;
			location.z -= a_step;
		}
		else if ( location_direction.y > 0 ) {
			location.x -= a_step;
			location.z -= b_step;
		}
		else if ( location_direction.z > 0 ) {
			location.x += a_step;
			location.y -= b_step;
		}
		System.out.printf("loc(%.1f;%.1f;%.1f;);\n", location.x, location.y, location.z);
	}
	

	public boolean isVisable() {
		return visable;
	}

	public void setVisable(boolean visable) {
		this.visable = visable;
	}
	public void toggleVisable() {
		this.visable = !this.visable;
	}

	public boolean isAnimate() {
		return animate;
	}
	public void toggleAnimate() {
		this.animate = !this.animate;
	}

	public void setAnimate(boolean animate) {
		this.animate = animate;
	}

	
	
	//=====================================================================
	
}
