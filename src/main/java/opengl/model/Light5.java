package opengl.model;

import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT4;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

import java.nio.FloatBuffer;

import javax.vecmath.Point3d;

public class Light5 extends ModelObject {


	public Light5() {

		setLocation(new Point3d(38, 89, -171)); // 38,1, 89,0, -171,1
	}

	@Override
	public String getName() {
		return "Light 5";
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public void display() {
	}

	private FloatBuffer wrapLocation() {
		return FloatBuffer.wrap(new float[] { (float) getLocation().x, (float) getLocation().y,
				(float) getLocation().z, 1f });
	}

	@Override
	public void init() {
		gl.glLightfv(GL_LIGHT4, GL_DIFFUSE, FloatBuffer.wrap(new float[] { 1f, 1f, 1f, 0.5f }));
		 gl.glLightfv(GL_LIGHT4, GL_SPECULAR, FloatBuffer.wrap(new float[]{ 1f,1f,1f,0.5f}) );
		 gl.glLightfv(GL_LIGHT4, GL_AMBIENT, FloatBuffer.wrap(new float[]{1f,1f,1f,0.5f}) );
		gl.glLightfv(GL_LIGHT4, GL_POSITION, wrapLocation());
		gl.glEnable(GL_LIGHT4);
	}

	@Override
	public void animate() {

	}

	@Override
	public boolean isInMiddle(Point3d p) {
		return false;
	}

}
