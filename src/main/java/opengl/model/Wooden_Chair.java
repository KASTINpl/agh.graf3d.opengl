package opengl.model;

import java.io.File;

import javax.vecmath.Point3d;

public class Wooden_Chair extends ObjModel {

	public Wooden_Chair() {
		super();
		
		setScale(0.5f);
		setLocation(new Point3d(-53,-10,-28));  // loc(-53,5;-10,0;-28,4;);
	}
	
	@Override
	public String getSourceDir() {
		return "objects"+File.separator+"Wooden_Chair"+File.separator; 
	}

	@Override
	public String getObjFile() {
		return "Wooden_Chair.obj";
	}

	@Override
	public String getName() {
		return "Wooden_Chair"; 
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
		return true;
	}
}
