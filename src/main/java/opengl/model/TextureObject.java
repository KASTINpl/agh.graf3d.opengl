package opengl.model;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;

import lombok.Setter;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public abstract class TextureObject extends GraphicObject {

	public static class Filter {
		public static final int NEAREST = 1;
		public static final int LINEAR = 2;
		public static final int MIPMAP = 3;
	}

	@Setter
	private Texture texture;

	private int filter;
	@Setter
	private float top;
	@Setter
	private float bottom;
	@Setter
	private float left;
	@Setter
	private float right;

	public TextureObject() {
		super();

		setScale(1.0f);
		filter = Filter.MIPMAP;
		top = 1;
		bottom = 0;
		left = 0;
		right = 1;
	}
	
	public abstract String getUrl();

	public void on() {
		int target = getTarget();
		if (target > 0) {
			gl.glEnable(target);
			gl.glBindTexture(target, getTextureObject());
//			System.out.printf("Uruchamian teksture %d;\n", target);
		}
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);

	}

	public void off() {
		int target = getTarget();
		if (target > 0) {
			gl.glDisable(target);
//			System.out.printf("Zamykam teksture %d;\n", target);
		}
	}

	public int getTarget() {
		if (texture instanceof Texture)
			return texture.getTarget();
		return -1;
	}

	public int getTextureObject() {
		if (texture instanceof Texture)
			return texture.getTextureObject();
		return -1;
	}

	public void load() {
		if (getUrl().isEmpty())
			return;
		// Load textures from image
		try {
			// Use URL so that can read from JAR and disk file.
			// Filename relative to the project root.
			File imageFile = new File(getUrl());
			if (!imageFile.isFile()) {
				System.out.printf("Nie znaleziono pliku \"%s\" (param: %s)!\n", imageFile.getAbsolutePath(), getUrl());
				return;
			}
			BufferedImage image = ImageIO.read(imageFile);
			// System.out.printf("Wczytuje teksture \"%s\"!\n", imageFile.getAbsolutePath());

			switch (filter) {
			case Filter.NEAREST:
				texture = AWTTextureIO.newTexture(GLProfile.getDefault(), image, false);
				// Nearest filter is least compute-intensive
				// Use nearer filter if image is larger than the original texture
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
				// Use nearer filter if image is smaller than the original texture
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
				break;
			case Filter.LINEAR:
				texture = AWTTextureIO.newTexture(GLProfile.getDefault(), image, false);
				// Linear filter is more compute-intensive
				// Use linear filter if image is larger than the original texture
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
				// Use linear filter if image is smaller than the original texture
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
				break;
			case Filter.MIPMAP:
				texture = AWTTextureIO.newTexture(GLProfile.getDefault(), image, true); // mipmap is true
				// Use mipmap filter is the image is smaller than the texture
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_NEAREST);

				break;
			}

			// Get the top and bottom coordinates of the textures. Image flips vertically.
			TextureCoords textureCoords = texture.getImageTexCoords();
			top = textureCoords.top();
			bottom = textureCoords.bottom();
			left = textureCoords.left();
			right = textureCoords.right();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getFilter() {
		return filter;
	}

	public void setFilter(int filter) {
		boolean diff = (filter != this.filter);
		this.filter = filter;
		if (!getUrl().isEmpty() && diff)
			load();
	}

	public float getTop() {
		return top * getScale();
	}

	public float getBottom() {
		return bottom * getScale();
	}

	public float getLeft() {
		return left * getScale();
	}

	public float getRight() {
		return right * getScale();
	}
	
	

}
