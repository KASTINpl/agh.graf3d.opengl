package opengl;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class GameVector extends Vector3d {

	private static final long serialVersionUID = 1L;
	
	public GameVector(Point3d location) {
		super.set(location);
	}

	public GameVector(Point3d location, Point3d target) {
		super.set(location);
		this.setTarget(target);
	}
	
	public void impingeY() {
		this.y = 0;
	}
	public void impingeX() {
		this.x = 0;
	}
	
	public void moveToX(Point3d direction, double step) {
		this.stepNormalize(step);
		
		if ( direction.x != 0 ) this.rotateY((direction.x > 0) ? 90.0 : 270.0);
	}

	public void moveToZ(Point3d direction, double step) {
		this.stepNormalize(step);
		
		if ( direction.z < 0 ) this.negate();
	}
	
	private void stepNormalize(double step) {
		this.normalize();
		this.impingeY(); // rzutuj na Y
		this.scale(step);
	}
	
	/**
	 * obecne położenie wektora traktuje jako punkt początku wektora, po dodaniu punktu końca wektora przesuwam poczatek układu współrzędnych do punktu początku wektora
	 * @param target
	 */
	public void setTarget(Point3d target) {
		this.x = target.x - this.x;
		this.y = target.y - this.y;
		this.z = target.z - this.z;
	}
	
	public void rotate(Double x, Double y, Double z) {
		if ( x != 0 )
			this.rotateX(x);
		if ( y != 0 )
			this.rotateY(y);
		if ( z != 0 )
			this.rotateZ(z);
	}

	public void rotateY(double degrees) {
		Transform3D xRot = new Transform3D();
		xRot.rotY( -Math.toRadians( degrees )  );
		xRot.transform(this);
	}

	public void rotateX(double degrees) {
		Transform3D xRot = new Transform3D();
		xRot.rotX( -Math.toRadians( degrees )  );
		xRot.transform(this);
	}

	public void rotateZ(double degrees) {
		Transform3D xRot = new Transform3D();
		xRot.rotZ( -Math.toRadians( degrees )  );
		xRot.transform(this);
	}
	
}
