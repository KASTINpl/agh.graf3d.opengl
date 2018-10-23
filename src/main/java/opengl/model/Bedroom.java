package opengl.model;

import static javax.media.opengl.GL.GL_FRONT;
import static javax.media.opengl.GL2ES1.GL_LIGHT_MODEL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;

import java.nio.FloatBuffer;

import javax.vecmath.Point3d;

public class Bedroom extends ObjModel {

	public Bedroom() {
		super();
		
		setScale(1f);
		setLocation(new Point3d(0,-10,0));
	}
	
	@Override
	public String getSourceDir() {
		return "objects/Bedroom/";
	}

	@Override
	public String getObjFile() {
		return "Bedroom.obj";
	}

	@Override
	public String getName() {
		return "Bedroom"; 
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
		if ( p.z <= -173 || p.z >= 92.6 || p.x <= -71.9 || p.x >= 193.8 || p.y >= 105.5 ) return true;
		if ( p.z >= 46.2 && p.x >= 95.1 ) return true;
		if ( p.x >= 95.1 && p.z >= -46.2 ) return true;
		return false;
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
