package opengl.model;

import java.nio.FloatBuffer;

import static javax.media.opengl.GL2.*;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

import javax.vecmath.Point3d;

import opengl.Config;

public class Light1 extends ModelObject {

	private Point3d prevLocation;

	public Light1() {

		setLocation(new Point3d(-48, 80, 41)); // loc(-48,3;80,0;41,4;);
		prevLocation = new Point3d(getLocation());
	}

	@Override
	public String getName() {
		return "Light 1";
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void display() {
//		setLocation(Config.getInstance().getCameraLocation());
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
		gl.glLightfv(GL_LIGHT0, GL_DIFFUSE, FloatBuffer.wrap(new float[] { 1.0f, 1.0f, 0.85f, 0 }));
		 gl.glLightfv(GL_LIGHT0, GL_SPECULAR, FloatBuffer.wrap(new float[]{ 1.0f,1.0f,0.85f,0}) );
		 gl.glLightfv(GL_LIGHT0, GL_AMBIENT, FloatBuffer.wrap(new float[]{1.0f,1.0f,0.85f,0}) );
		gl.glLightfv(GL_LIGHT0, GL_POSITION, wrapLocation());
		gl.glEnable(GL_LIGHT0);
	}

	@Override
	public void animate() {

	}

	@Override
	public boolean isInMiddle(Point3d p) {
		return false;
	}

}
