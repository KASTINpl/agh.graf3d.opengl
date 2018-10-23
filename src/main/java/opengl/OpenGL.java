package opengl;

import java.nio.FloatBuffer;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

import static javax.media.opengl.GL.GL_FRONT;
import static javax.media.opengl.GL2.*;
import static javax.media.opengl.GL2ES1.GL_LIGHT_MODEL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;

public class OpenGL {

	private GL2 gl;
	private GLU glu;
	private GLUT glut;
	private Integer width;
	private Integer height;

	private Config c = Config.getInstance();

	float gray[] = { 0.75f, 0.75f, 0.75f, 1.0f };

	public OpenGL() {

	}

	public void init() {
		/**
		 * ==================== SCENA =====================
		 */
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
		gl.glClearDepth(1.0f); // set clear depth value to farthest
		gl.glEnable(GL_DEPTH_TEST); // enables depth testing
		gl.glDepthFunc(GL_LEQUAL); // the type of depth test to do
		
		/**
		 * ==================== RENDERING =====================
		 */
		gl.glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		gl.glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
		gl.glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
		gl.glHint(GL_GENERATE_MIPMAP_HINT, GL_NICEST);
		gl.glHint(GL_FRAGMENT_SHADER_DERIVATIVE_HINT, GL_NICEST);
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); 

		/**
		 * ==================== ALPHA & SHADING =====================
		 */
		gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting
		gl.glDepthFunc(GL_LESS);
//		gl.glEnable(GL_BLEND);
		gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		/**
		 * ==================== TEXTUROWANIE =====================
		 */
		gl.glEnable(GL_TEXTURE_2D);

		/**
		 * ==================== ŚWIATŁA =====================
		 */
        gl.glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, FloatBuffer.wrap(new float[]{1.0f, 1.0f, 1.0f, 0.1f}));
		gl.glEnable(GL_LIGHTING);
//		gl.glEnable(GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL_FRONT, GL_AMBIENT);
		gl.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(new float[]{1.f, 1.0f, 1.0f, 0.8f}));

//		gl.glEnable(GL_LIGHT0);

	}
	
	public void display() {
		
	}


	public void clean() {
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		// Enable the model-view transform
	}

	/**
	 * ==================================================================================== czeluścia kodu...
	 * 
	 * @param gl
	 * @return
	 */

	public OpenGL setGl(GL2 gl) {
		this.gl = gl;
		return this;
	}

	public OpenGL setGlu(GLU glu) {
		this.glu = glu;
		return this;
	}

	public OpenGL setWidth(Integer width) {
		this.width = width;
		return this;
	}

	public OpenGL setHeight(Integer height) {
		this.height = height;
		return this;
	}

	public OpenGL setGlut(GLUT glut) {
		this.glut = glut;
		return this;
	}


}
