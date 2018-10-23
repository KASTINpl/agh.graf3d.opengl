package opengl.model;

import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT3;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

import java.nio.FloatBuffer;

import javax.vecmath.Point3d;

import opengl.Config;

public class Light4 extends ModelObject {
	private Point3d prevLocation;

	public Light4() {

		setLocation(new Point3d(-75, 107, -177)); // -75,9, 107,0, -177,2
		prevLocation = new Point3d(getLocation());
	}

	@Override
	public String getName() {
		return "Light 4";
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public void display() {
		setLocation(Config.getInstance().getCameraLocation());
		if (!getLocation().equals(prevLocation)) {
			prevLocation = new Point3d(getLocation());
			gl.glLoadIdentity();

			gl.glPushMatrix();

			gl.glTranslated(getLocation().x, getLocation().y, getLocation().z);
			gl.glLightfv(GL_LIGHT0, GL_POSITION, wrapLocation());
			gl.glPopMatrix();
		}
	}

	private FloatBuffer wrapLocation() {
		return FloatBuffer.wrap(new float[] { (float) getLocation().x, (float) getLocation().y,
				(float) getLocation().z, 1f });
	}

	@Override
	public void init() {
		//float[] color = new float[] { 0.9f, 0.9f, 1f, 0 };
		float[] color = new float[] { 0.9f, 0.9f, 1f, 0 };
		gl.glLightfv(GL_LIGHT3, GL_DIFFUSE, FloatBuffer.wrap(color));
		 gl.glLightfv(GL_LIGHT3, GL_SPECULAR, FloatBuffer.wrap(color) );
		 gl.glLightfv(GL_LIGHT3, GL_AMBIENT, FloatBuffer.wrap(color) );
		gl.glLightfv(GL_LIGHT3, GL_POSITION, wrapLocation());
		gl.glEnable(GL_LIGHT3);
	}

	@Override
	public void animate() {

	}

	@Override
	public boolean isInMiddle(Point3d p) {
		return false;
	}

}
