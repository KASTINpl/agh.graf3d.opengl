package opengl.model;

import javax.vecmath.Point3d;

public class Lampa extends ObjModel {

	public Lampa() {
		super();
		
		setScale(0.2f);
		setLocation(new Point3d(-10,-10,-15));
	}
	
	@Override
	public String getSourceDir() {
		return "objects/lamp/";
	}

	@Override
	public String getObjFile() {
		return "lamp.obj";
	}

	@Override
	public String getName() {
		return "Lampa"; 
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
