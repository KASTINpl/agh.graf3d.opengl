package opengl.model;

import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

import java.nio.FloatBuffer;

import javax.vecmath.Point3d;

public class Light2 extends ModelObject {

	private Point3d prevLocation;
	private Point3d startLocation;
	public Light2() {

		setLocation(new Point3d(38, 80, 66)); // loc(38,1;80,0;66,5;);
		prevLocation = new Point3d(getLocation());
		startLocation = new Point3d(getLocation());
	}

	@Override
	public String getName() {
		return "Light 2";
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void display() {
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
		gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, FloatBuffer.wrap(new float[] { 1.0f, 0.8f, 0.6f, 0 }));
		 gl.glLightfv(GL_LIGHT1, GL_SPECULAR, FloatBuffer.wrap(new float[]{ 1.0f,0.8f,0.6f,0}) );
		 gl.glLightfv(GL_LIGHT1, GL_AMBIENT, FloatBuffer.wrap(new float[]{1.0f,0.8f,0.6f,0}) );
		gl.glLightfv(GL_LIGHT1, GL_POSITION, wrapLocation());
		gl.glEnable(GL_LIGHT1);
	}

	@Override
	public void animate() {
	}

	@Override
	public boolean isInMiddle(Point3d p) {
		return false;
	}

}
