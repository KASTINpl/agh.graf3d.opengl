package opengl.model;

import javax.vecmath.Point3d;

public class SimpleTexture extends TextureObject {
	
	private String url;
	
	public SimpleTexture() {
		super();
		url = "";
		setFilter(Filter.MIPMAP);
	}
	
	public SimpleTexture(String _url) {
		this();
		url = _url;
		load();
	}

	@Override
	public void animate() {
		
	}

	@Override
	public boolean isInMiddle(Point3d p) {
		return false;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void init() {
	}

}
