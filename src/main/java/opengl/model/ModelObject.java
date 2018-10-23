package opengl.model;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import lombok.Getter;
import lombok.Setter;

public abstract class ModelObject extends GraphicObject {
	
	public static class COLOR {
		public final static float[] wall = {0.95f, 0.90f, 0.85f, 1};
	}
	
	@Getter
	@Setter
	private List<TextureObject> texture;

	public ModelObject() {
		super();
		texture = new ArrayList<TextureObject>();
	}

	public abstract String getName();
	
	public abstract boolean isActive();

	public int addTexture(TextureObject netTexture) {
		if ( !(netTexture instanceof TextureObject)) return 0;
		texture.add(netTexture);
		return texture.size()-1;
	}

	public TextureObject getTexture(int index) {
		if (index >= 0 && index <= texture.size() - 1)
			return texture.get(index);
		return new SimpleTexture();
	}

	/**
	 * wyświetlaj zgodnie z Config.FPS
	 */
	public abstract void display();


	public void setColor(float[] c, float A) {
		gl.glColor4f(c[0], c[1], c[2], A); //
	}
	
	public void setColor(float[] c) {
		gl.glColor4f(c[0], c[1], c[2], c[3]); //
	}

	public void setColor(double R, double G, double B, double A) {
		gl.glColor4d(R, G, B, A); //
	}

	public void setColor(double R, double G, double B) {
		gl.glColor4d(R, G, B, 1); //
	}
	
	public void setLights(double R, double G, double B) {
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, new float[] {(float)R, (float)G, (float)B}, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE,  new float[] {(float)R, (float)G, (float)B}, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR,  new float[] {(float)R, (float)G, (float)B}, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 128);
	}
}
