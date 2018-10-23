package opengl.model;

import javax.media.opengl.GL2;
import javax.vecmath.Point3d;

public class DachModel extends ModelObject {
	
	float size = 100;
	private int sufitTextureId = -1;
	
	double x_L;
	double x_R;
	double z_T;
	double z_B;

	public DachModel() {
		super();
		setLocation(new Point3d(0, 10, 0));
		setScale(1);
		setRotation(0);
		setRotation_direction(new Point3d(0, 1, 0));
		
//		setAnimate(true);

		x_L = getLocation().x-size/2.0;
		x_R = getLocation().x+size/2.0;
		z_T = getLocation().z+size/2.0;
		z_B = getLocation().z-size/2.0;
		
	}
	@Override
	public void init() {
		sufitTextureId = addTexture(new SimpleTexture("images/white-painted-brick-wall-texture.jpg")); // getSufitTexture()
	}
	
	private SimpleTexture getSufitTexture() {
		return (SimpleTexture) getTexture(sufitTextureId);
	}
	
	@Override
	public void display() {

		gl.glLoadIdentity(); 

		gl.glPushMatrix();

		gl.glTranslated(getLocation().x, getLocation().y, getLocation().z);
		gl.glRotated(getRotation(), getRotation_direction().x, getRotation_direction().y, getRotation_direction().z); 
		gl.glScaled(getScale(), getScale(), getScale());

		getSufitTexture().on();
		
		setColor(1, 1, 1);
		gl.glNormal3f(0.0f, -1.0f, 0.0f);
		
		gl.glBegin(GL2.GL_QUADS); // of the color cube

		getSufitTexture().setScale(20.0f);
		gl.glTexCoord2f(getSufitTexture().getRight(), getSufitTexture().getTop());
		gl.glVertex3d(x_L, getLocation().y, z_T);
		gl.glTexCoord2f(getSufitTexture().getLeft(), getSufitTexture().getTop());
		gl.glVertex3d(x_L, getLocation().y, z_B);
		gl.glTexCoord2f(getSufitTexture().getLeft(), getSufitTexture().getBottom());
		gl.glVertex3d(x_R, getLocation().y, z_B);
		gl.glTexCoord2f(getSufitTexture().getRight(), getSufitTexture().getBottom());
		gl.glVertex3d(x_R, getLocation().y, z_T);
		
		gl.glEnd(); // of the color cube
		
		getSufitTexture().off();

		gl.glPopMatrix();
	}
	

	@Override
	public void animate() {

	}

	@Override
	public boolean isInMiddle(Point3d p) {
		if ( isVisable() && p.y > getLocation().y ) return true;
		return false;
	}

	@Override
	public String getName() {
		return "Dach";
	}

	@Override
	public boolean isActive() {
		return false;
	}

}
