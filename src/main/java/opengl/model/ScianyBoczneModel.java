package opengl.model;

import javax.media.opengl.GL2;
import javax.vecmath.Point3d;

public class ScianyBoczneModel extends ModelObject {
	
	float size = 100;
	private int wallTextureId = -1;
	private int woodTextureId = -1;
	private Point3d locationTop;
	
	double x_L;
	double x_R;
	double z_T;
	double z_B;

	public ScianyBoczneModel() {
		super();
		setLocation(new Point3d(0, -5, 0));
		setScale(1);
		setRotation(0);
		setRotation_direction(new Point3d(0, 1, 0));
		
		locationTop = new Point3d(getLocation());
		locationTop.y = 25;
//		setAnimate(true);

		x_L = getLocation().x-size/2.0;
		x_R = getLocation().x+size/2.0;
		z_T = getLocation().z+size/2.0;
		z_B = getLocation().z-size/2.0;
		
	}
	@Override
	public void init() {
		wallTextureId = addTexture(new SimpleTexture("images/glazura.jpg")); // getWallTexture()
		woodTextureId = addTexture(new SimpleTexture("images/wood.jpg")); // getWallTexture()
	}

	private SimpleTexture getWoodTexture() {
		return (SimpleTexture) getTexture(woodTextureId);
	}
	
	private SimpleTexture getWallTexture() {
		return (SimpleTexture) getTexture(wallTextureId);
	}
	
	@Override
	public void display() {

		gl.glLoadIdentity(); 

		gl.glPushMatrix();

		gl.glTranslated(getLocation().x, getLocation().y, getLocation().z);
		gl.glRotated(getRotation(), getRotation_direction().x, getRotation_direction().y, getRotation_direction().z); 
		gl.glScaled(getScale(), getScale(), getScale());

		getWallTexture().on();
		getWallTexture().setScale(12f);
		
		gl.glNormal3f(1.0f, 0.0f, 0.0f);
		
		gl.glBegin(GL2.GL_QUADS); // of the color cube

		
		/**
		 * =========== LEFT ==========
		 */
		setColor(COLOR.wall);
		gl.glNormal3f(1.0f, 0.0f, 0.0f);

		gl.glTexCoord2f(getWallTexture().getLeft(), getWallTexture().getBottom());
		gl.glVertex3d(x_L, getLocation().y, z_T);
		gl.glTexCoord2f(getWallTexture().getRight(), getWallTexture().getBottom());
		gl.glVertex3d(x_L, getLocation().y, z_B);
		gl.glTexCoord2f(getWallTexture().getRight(), getWallTexture().getTop());
		gl.glVertex3d(x_L, locationTop.y, z_B);
		gl.glTexCoord2f(getWallTexture().getLeft(), getWallTexture().getTop());
		gl.glVertex3d(x_L, locationTop.y, z_T);

		gl.glEnd(); // of the color cube
		/**
		 * =========== RIGHT ==========
		 */
		getWallTexture().off();
		
		getWoodTexture().on();
		getWoodTexture().setScale(6f);
		
		gl.glBegin(GL2.GL_QUADS); // of the color cube
		setColor(1, 1, 1);
		gl.glNormal3f(-1.0f, 0.0f, 0.0f);

		gl.glTexCoord2f(getWoodTexture().getLeft(), getWoodTexture().getBottom());
		gl.glVertex3d(x_R, getLocation().y, z_T);
		gl.glTexCoord2f(getWoodTexture().getRight(), getWoodTexture().getBottom());
		gl.glVertex3d(x_R, getLocation().y, z_B);
		gl.glTexCoord2f(getWoodTexture().getRight(), getWoodTexture().getTop());
		gl.glVertex3d(x_R-10, locationTop.y, z_B);
		gl.glTexCoord2f(getWoodTexture().getLeft(), getWoodTexture().getTop());
		gl.glVertex3d(x_R-10, locationTop.y, z_T);

		gl.glEnd(); // of the color cube
		getWoodTexture().off();
		getWallTexture().on();
		
		/**
		 * =========== FRONT ==========
		 */
		gl.glBegin(GL2.GL_QUADS); // of the color cube
		setColor(COLOR.wall);
		gl.glNormal3f(0.0f, 0.0f, 1.0f);

		gl.glTexCoord2f(getWallTexture().getLeft(), getWallTexture().getBottom());
		gl.glVertex3d(x_L, getLocation().y, z_B);
		gl.glTexCoord2f(getWallTexture().getLeft(), getWallTexture().getTop());
		gl.glVertex3d(x_L, locationTop.y, z_B);
		gl.glTexCoord2f(getWallTexture().getRight(), getWallTexture().getTop());
		gl.glVertex3d(x_R, locationTop.y, z_B);
		gl.glTexCoord2f(getWallTexture().getRight(), getWallTexture().getBottom());
		gl.glVertex3d(x_R, getLocation().y, z_B);

		/**
		 * =========== BACK ==========
		 */
		gl.glNormal3f(0.0f, 0.0f, -1.0f);

		gl.glTexCoord2f(getWallTexture().getLeft(), getWallTexture().getBottom());
		gl.glVertex3d(x_L, getLocation().y, z_T);
		gl.glTexCoord2f(getWallTexture().getLeft(), getWallTexture().getTop());
		gl.glVertex3d(x_L, locationTop.y, z_T);
		gl.glTexCoord2f(getWallTexture().getRight(), getWallTexture().getTop());
		gl.glVertex3d(x_R, locationTop.y, z_T);
		gl.glTexCoord2f(getWallTexture().getRight(), getWallTexture().getBottom());
		gl.glVertex3d(x_R, getLocation().y, z_T);

		gl.glEnd(); // of the color cube

		getWallTexture().off();

		gl.glPopMatrix();
	}
	

	@Override
	public void animate() {

	}

	@Override
	public boolean isInMiddle(Point3d p) {
		if ( isVisable() && (p.x <= x_L || p.x >= x_R || p.z >= z_T || p.z <= z_B) )  return true;
		return false;
	}

	@Override
	public String getName() {
		return "Ściany boczne";
	}


	@Override
	public boolean isActive() {
		return false;
	}
}
