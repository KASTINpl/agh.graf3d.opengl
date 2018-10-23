package opengl.model;

import javax.vecmath.Point3d;

public class fre005 extends ObjModel {

	public fre005() {
		super();
		
		setScale(0.02f);
		setLocation(new Point3d(63,-4.5,-37)); // loc(63,9;-4,5;-37,3;);
	}
	
	@Override
	public String getSourceDir() {
		return "objects/fre005/";
	}

	@Override
	public String getObjFile() {
		return "fre005.obj";
	}

	@Override
	public String getName() {
		return "Drewno z lasu"; 
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
