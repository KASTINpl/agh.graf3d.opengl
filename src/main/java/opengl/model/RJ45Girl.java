package opengl.model;

import javax.vecmath.Point3d;

public class RJ45Girl extends ObjModel {

	public RJ45Girl() {
		super();
		
		setScale(1f);
		setLocation(new Point3d(-39.1, 26.6, 70.9)); // loc(-39,1;26,6;70,9;);
	}
	
	@Override
	public String getSourceDir() {
		return "objects/rj45/";
	}

	@Override
	public String getObjFile() {
		return "socket.obj";
	}

	@Override
	public String getName() {
		return "RJ-45 Girl"; 
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
