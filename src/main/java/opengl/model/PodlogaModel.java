package opengl.model;

import static javax.media.opengl.GL2GL3.GL_QUADS;

import java.io.File;

import javax.vecmath.Point3d;

public class PodlogaModel extends ModelObject {
	
	float size = 400;
	private int parkietTextureId = -1;
	
	double x_L;
	double x_R;
	double z_T;
	double z_B;

	public PodlogaModel() {
		super();
		setLocation(new Point3d(0, -4.5, 0));
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
		parkietTextureId = addTexture(new SimpleTexture("images"+File.separator+"parkiet.jpg")); // getParkietTexture()
	}
	
	private SimpleTexture getParkietTexture() {
		return (SimpleTexture) getTexture(parkietTextureId);
	}
	
	@Override
	public void display() {

		gl.glLoadIdentity(); 

		gl.glPushMatrix();

		gl.glTranslated(getLocation().x, getLocation().y, getLocation().z);
		gl.glRotated(getRotation(), getRotation_direction().x, getRotation_direction().y, getRotation_direction().z); 
		gl.glScaled(getScale(), getScale(), getScale());


		/**
		 * ========== efects ===========
		 *
        gl.glColorMask(false, false, false, false);  // Set Color Mask
        
        // Enable Stencil Buffer For "marking" The Floor
        gl.glEnable(GL2.GL_STENCIL_TEST);  
        
        // Always Passes, 1 Bit Plane, 1 As Mask
        gl.glStencilFunc(GL2.GL_ALWAYS, 1, 1);  

        gl.glStencilOp(GL2.GL_KEEP, GL2.GL_KEEP, GL2.GL_REPLACE);      
        // Keep If Test Fails, Keep If Test Passes But Buffer Test Fails
        // Replace If Test Passes
        gl.glDisable(GL2.GL_DEPTH_TEST);    // Disable Depth Testing

		**
		 * ========== efects ===========
		 */
        
		getParkietTexture().on();
		
		setColor(1, 0.7, 0.1);
		setLights(1, 0.7, 0.1);
		
		gl.glNormal3f(0.0f, 1.0f, 0.0f);
		
		gl.glBegin(GL_QUADS); // of the color cube

		getParkietTexture().setScale(25.0f);
		gl.glTexCoord2f(getParkietTexture().getRight(), getParkietTexture().getTop());
		gl.glVertex3d(x_L, getLocation().y, z_T);
		gl.glTexCoord2f(getParkietTexture().getLeft(), getParkietTexture().getTop());
		gl.glVertex3d(x_L, getLocation().y, z_B);
		gl.glTexCoord2f(getParkietTexture().getLeft(), getParkietTexture().getBottom());
		gl.glVertex3d(x_R, getLocation().y, z_B);
		gl.glTexCoord2f(getParkietTexture().getRight(), getParkietTexture().getBottom());
		gl.glVertex3d(x_R, getLocation().y, z_T);
		
		gl.glEnd(); // of the color cube
		
		getParkietTexture().off();
		
		/**
		 * ========== efects ===========
		 *
		gl.glEnable(GL2.GL_DEPTH_TEST);  // Enable Depth Testing
        gl.glColorMask(true, true, true, true);  // Set Color Mask to TRUE, TRUE, TRUE, TRUE
        gl.glStencilFunc(GL2.GL_EQUAL, 1, 1);  // We Draw Only Where The Stencil Is 1
        // (I.E. Where The Floor Was Drawn)
        // Don't Change The Stencil Buffer
        gl.glStencilOp(GL2.GL_KEEP, GL2.GL_KEEP, GL2.GL_KEEP);  
        gl.glEnable(GL2.GL_CLIP_PLANE0);  // Enable Clip Plane For Removing Artifacts
        // (When The Object Crosses The Floor)
        gl.glClipPlane(GL2.GL_CLIP_PLANE0, new double[] {0.0f, -1.0f, 0.0f, 0.0f}, 0);  // Equation For Reflected Objects
		**
		 * ========== efects ===========
		 */

		gl.glPopMatrix();
	}
	

	@Override
	public void animate() {

	}

	@Override
	public boolean isInMiddle(Point3d p) {
		if ( p.y < getLocation().y ) return true;
		return false;
	}

	@Override
	public String getName() {
		return "Podłoga";
	}

	@Override
	public boolean isActive() {
		return true;
	}

}
