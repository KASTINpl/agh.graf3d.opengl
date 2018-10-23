package opengl.model;

import javax.media.opengl.GL2;
import javax.vecmath.Point3d;

public class BallModel extends ModelObject {

	public BallModel() {
		super();
		setLocation(new Point3d(-3, 1, -1));
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

//		gl.glEnable(GL2.GL_BLEND);
		setColor( 0.7, 0.1, 0.6, 0.5 );
		setLights( 0.7, 0.1, 0.6 );
		glut.glutSolidSphere(getScale(), 15, 15);
//		gl.glDisable(GL2.GL_BLEND);

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
		return "Piłka";
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
