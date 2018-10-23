package opengl.model;

import javax.vecmath.Point3d;

public class TeaPotModel extends ModelObject {

	public TeaPotModel() {
		super();
		setLocation(new Point3d(4, 2, -3));
		setScale(1);
		setRotation(0);
		setRotation_direction(new Point3d(0, 1, 0));
		
		setAnimate(true);
	}

	@Override
	public void init() {
	}

	@Override
	public void display() {
		gl.glLoadIdentity(); 

		gl.glPushMatrix();

		gl.glTranslated(getLocation().x, getLocation().y, getLocation().z);
		gl.glRotated(getRotation(), getRotation_direction().x, getRotation_direction().y, getRotation_direction().z); 
		//gl.glScaled(getScale(), getScale(), getScale());

		setColor( 0.1, 0.1, 0.1 );
		setLights( 0.1, 0.1, 0.1 );
		glut.glutSolidTeapot(getScale());

		gl.glEnd(); // of the color cube

		gl.glPopMatrix();

	}

	@Override
	public void animate() {
		setRotation( getRotation()+1 );
	}

	@Override
	public boolean isInMiddle(Point3d p) {
		return false;
	}

	@Override
	public String getName() {
		return "Czajnik";
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
