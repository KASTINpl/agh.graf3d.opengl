package opengl;

import javax.media.opengl.GL2;
import javax.vecmath.Point3d;

import opengl.model.GraphicObject;
import opengl.model.ModelObject;
import opengl.model.ObjModel;
import lombok.Getter;
import lombok.Setter;

public class Camera extends GraphicObject {
	@Getter
	private Point3d target;

	@Getter
	@Setter
	private int angle = 45;

	@Getter
	private double step = 0.5;
	@Getter
	private Point3d direction = new Point3d(0, 0, 0);
	private Point3d directionTarget = new Point3d(0, 0, 0);
	
	static double IOD = 0.2;

	public Camera() {
		super();
		setLocation(new Point3d(0, 0, 0));
		target = new Point3d(0, 0, -500);
	}

	/**
	 * czy kamera potrzebuje wykonania fnukcji setup()
	 * 
	 * @return
	 */
	public boolean needSetup() {
		return !(direction.x == 0 && direction.y == 0 && direction.z == 0 && directionTarget.x == 0
				&& directionTarget.y == 0 && directionTarget.z == 0);
	}

	/**
	 * init or reshape or change position
	 */
	public void setup(int eye) {
		c.forceUpdate = false;

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL2.GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix

		double deep = getLocation().distance(target);
		// float aspect = (float) c.width / (float) c.height;

		double aspect = (double) (c.width) / (double) (c.height);
		if ( c.obraz == 1 ) { // stereo
			aspect = (double) (c.width)/2.0 / (double) (c.height);
		}
		this.glPerspective((double) angle, aspect, 3.0, deep, eye);

	    double atX = getLocation().x;
	    if ( eye==1 ) atX -= IOD/2.0;
	    else atX += IOD/2.0;
//	    System.out.printf("eye(%d) -> frustumshift(%.5f);\n", eye, frustumshift);
		glu.gluLookAt(atX, getLocation().y, getLocation().z, // eye
				target.x, target.y, target.z, // at
				0, 1, 0 // up
		);

//		System.out.printf("location = %.1f, %.1f, %.1f\n", getLocation().x, getLocation().y, getLocation().z);
		this.setupType(c.getTryb());
	}

	/**
	 * use gluPerspective or glFrustum
	 * 
	 * @param angle
	 *            - Field of vision in degrees in the y direction
	 * @param aspect
	 *            - Aspect ratio of the viewport
	 * @param zNear
	 *            - The near clipping distance
	 * @param zFar
	 *            - The far clipping distance
	 */
	private void glPerspective(double angle, double aspect, double zNear, double zFar, int eye) {

		switch (c.obraz) {
		default:
			glu.gluPerspective(angle, aspect, zNear, zFar);
			break;
		case 1:
			double fH = Math.tan(angle / 360 * Math.PI) * zNear;
			double fW = fH * aspect;
			
			double DTR = 0.0174532925;

		    double top = zNear*Math.tan(DTR*angle/2);                    //sets top of frustum based on fovy and near clipping plane
		    double right = aspect*top;                             //sets right of frustum based on aspect ratio
		    double frustumshift = 0.02;  // (IOD/2.0) * NEAR_DIST/SCREEN_DIST;

			switch (eye) {
			default:
				gl.glFrustum(-fW, fW, -fH, fH, zNear, zFar);
				break;
			case 1: // left
				gl.glFrustum(-right - frustumshift, right - frustumshift, -top, top, zNear, zFar);
				//gl.glTranslatef((float)IOD/2, 0.0f, 0.0f);
				break;
			case 2: // right
				gl.glFrustum(-right + frustumshift, right + frustumshift, -top, top, zNear, zFar);
				//gl.glTranslatef((float)-IOD/2, 0.0f, 0.0f);
			}
			break;
		}
	}

	public void setupType(int polygon_mode) {

		int mode = 0;
		switch (polygon_mode) {
		default:
			mode = GL2.GL_FILL;
			break;
		case 1:
			mode = GL2.GL_POINT;
			break;
		case 2:
			mode = GL2.GL_LINE;
			break;
		}

		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
	}

	/**
	 * użyj direction i directionTarget do ustalenia wspolrzednych location i target; wykonywane przez
	 * Config.prepareDisplay()
	 */
	public void prepare() {
		Point3d currLocation = new Point3d(getLocation());

		if (direction.x != 0) {
			GameVector gV = new GameVector(getLocation(), target);
			gV.moveToX(direction, step);

			getLocation().add(gV);
			target.add(gV);
		}
		if (direction.y != 0) {
			getLocation().y += step * direction.y;
			target.y += step * direction.y;
		}
		if (direction.z != 0) {
			GameVector gV = new GameVector(getLocation(), target);
			gV.moveToZ(direction, step);

			getLocation().add(gV);
			target.add(gV);
		}
		if (directionTarget.x != 0) {
			double deep = getLocation().distance(target);

			GameVector gV = new GameVector(getLocation(), target);
			// gV.impingeY(); // rzutuj na Y

			gV.rotateY((double) directionTarget.x);

			gV.normalize();
			gV.scale(deep);
			target.set(getLocation());
			target.add(gV);
		}
		if (directionTarget.y != 0) {
			GameVector gV = new GameVector(getLocation(), target);
			if (!isVertical()) {
				// gV.impingeX(); // rzutuj na Y

				double deep = getLocation().distance(target);

				gV.y += directionTarget.y * 15;

				gV.normalize();
				gV.scale(deep);
				target.set(getLocation());
				target.add(gV);
			} else {
				c.danger = 100;
			}
		}

		// jesli znaleziony konflikt lokalizacji - zostan w obecnym miejscu
		if (!getLocation().equals(currLocation)) {
			if (collisionHandling(getLocation())) {
				setLocation(currLocation);
			}
		}

	}
	
	public void speedUp() {
		step += 0.1;
	}
	public void speedDown() {
		step -= 0.1;
	}

	/**
	 * wykryj kolizje z dowolnym obiektem i w przypadku takowej ustaw danger=100 i wykonaj
	 * Camera.clearDirection()
	 * 
	 * @param location
	 *            polezenie kamery
	 */
	private boolean collisionHandling(Point3d location) {

		for (GraphicObject go : c.objects) 
			if ( go instanceof ModelObject && ((ModelObject)go).isActive() && go.isVisable() && go.isInMiddle(location) )  {
				System.out.printf("Kolizja z <%s> w lokalizacji [%s]!\n", go.getClass().getName(),
						location.toString());
				c.danger = 100;
				return true;
			} 
		return false;
	}

	/**
	 * czy wektor jest bliski pionu
	 * 
	 * @return
	 */
	public boolean isVertical() {
		if (Math.abs(getLocation().x - target.x) < 0.01 && Math.abs(getLocation().z - target.z) < 0.01)
			return true;
		return false;
	}

	public void clearDirection() {
		direction.set(0, 0, 0);
	}

	public void prepareDirection(boolean W, boolean S, boolean A, boolean D, boolean R, boolean F) {
		direction.x = (A) ? -1 : ((D) ? 1 : 0);
		if (A && D)
			direction.x = 0;

		direction.y = (R) ? 1 : ((F) ? -1 : 0);
		if (R && F)
			direction.y = 0;

		direction.z = (W) ? 1 : ((S) ? -1 : 0);
		if (W && S)
			direction.z = 0;

	}

	public void prepareTarget(boolean UP, boolean DOWN, boolean LEFT, boolean RIGHT) {
		directionTarget.x = (LEFT) ? -1 : ((RIGHT) ? 1 : 0);
		if (LEFT && RIGHT)
			directionTarget.x = 0;

		directionTarget.y = (UP) ? 1 : ((DOWN) ? -1 : 0);
		if (UP && DOWN)
			directionTarget.y = 0;
	}

	@Override
	public void animate() {
	}

	@Override
	public boolean isInMiddle(Point3d p) {
		return false;
	}

	@Override
	public void init() {
	}

}
