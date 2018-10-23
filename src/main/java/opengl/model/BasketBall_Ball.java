package opengl.model;

import java.io.File;

import javax.vecmath.Point3d;

public class BasketBall_Ball extends ObjModel {

	public BasketBall_Ball() {
		super();
		
		setScale(1f);
		setLocation(new Point3d(73,20,-29)); // loc(72,9;20,4;-28,8;);
		setVisable(true);
	}
	
	@Override
	public String getSourceDir() {
		return "objects"+File.separator+"Baskball_Ball_OBJ"+File.separator; 
	}

	@Override
	public String getObjFile() {
		return "BasketBall_Ball.obj";
	}

	@Override
	public String getName() {
		return "BasketBall_Ball"; 
	}

	@Override
	public void display() {
		gl.glLoadIdentity(); 

		gl.glPushMatrix();

		gl.glTranslated(getLocation().x, getLocation().y, getLocation().z);
		gl.glRotated(getRotation(), getRotation_direction().x, getRotation_direction().y, getRotation_direction().z); 
		gl.glScaled(getScale(), getScale(), getScale());

		setColor(1, 1, 1);
		drawModel();
		
		gl.glPopMatrix();
	}

	@Override
	public void init() {
		load();
	}

	@Override
	public void animate() {
	}

	@Override
	public boolean isInMiddle(Point3d p) {
		return false;
	}

	@Override
	public boolean isActive() {
		return false;
	}
}
