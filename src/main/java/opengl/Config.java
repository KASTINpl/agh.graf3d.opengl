package opengl;

import java.awt.event.KeyEvent;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.vecmath.Point3d;

import lombok.Getter;
import lombok.Setter;
import opengl.model.GraphicObject;
import opengl.model.ModelObject;

import org.reflections.Reflections;

public class Config {
	@Setter @Getter
	private OpenGlCanvas glCanvas;

	double app_size = 0.8;
	int FPS = 60;
	int width;
	int height;

	List<GraphicObject> objects = new ArrayList<GraphicObject>(); // wszystkie obiekty na planszy
	ModelObject currentModel = null;

	Camera camera;
	boolean forceUpdate = true; // true if setup() need be induced or on init
	int obraz = 0; // 0 - mono, 1 - stereo
	int skalowanie = 0; // Sztywny
	private int tryb = 0; // Wypełnienie
	int danger = 0; // nie mozna kontunuowac ruchu jesli >0

	// ============================== MOUSE & KEYBOARD ==============================

	int mouse_x = 0;
	int mouse_y = 0;
	boolean mouse_pressed = false;
	int mouse_wheel = 0; // 1 - up, -1 down

	// KEY PRESSED
	public final Set<Integer> pressed = new HashSet<Integer>();

	// ============================== FUNCTIONS =========================
	
	/**
	 * do animate() on all object, prepare camera run by OpenGLCanvas.display()
	 */
	public void prepareDisplay() {
		if ( danger > 0 ) { --danger; } // poltora sekundy niebezpieczenstwa
		
		for (GraphicObject go : objects) {
			if (go.isAnimate())
				go.animate();
		}

		// + wykrywanie kolizji collisionHandling()
		camera.prepareDirection(pressed.contains(KeyEvent.VK_W), pressed.contains(KeyEvent.VK_S),
				pressed.contains(KeyEvent.VK_A), pressed.contains(KeyEvent.VK_D),
				pressed.contains(KeyEvent.VK_R), pressed.contains(KeyEvent.VK_F));
		camera.prepareTarget(pressed.contains(KeyEvent.VK_UP), pressed.contains(KeyEvent.VK_DOWN),
				pressed.contains(KeyEvent.VK_LEFT), pressed.contains(KeyEvent.VK_RIGHT));

		if (currentModel instanceof ModelObject) { // wybrano jakis model
			if ( pressed.contains(KeyEvent.VK_X) ) {
				currentModel.setLocation_direction(new Point3d(1,0,0));
				currentModel.setRotation_direction(new Point3d(1,0,0));
				currentModel.setRotation(0);
			}
			if ( pressed.contains(KeyEvent.VK_Y) ) {
				currentModel.setLocation_direction(new Point3d(0,1,0));
				currentModel.setRotation_direction(new Point3d(0,1,0));
				currentModel.setRotation(0);
			}
			if ( pressed.contains(KeyEvent.VK_Z) ) {
				currentModel.setLocation_direction(new Point3d(0,0,1));
				currentModel.setRotation_direction(new Point3d(0,0,1));
				currentModel.setRotation(0);
			}
			if ( pressed.contains(KeyEvent.VK_T) ) {
				currentModel.setRotation(currentModel.getRotation()+1);
			}
		}
		
		camera.prepare();
	}
	
	// ============================== SETTERS & GETTERS =========================


	public List<ModelObject> getModelObject() {
		List<ModelObject> list = new ArrayList<ModelObject>();
		for (GraphicObject go : objects) {
			if ( go instanceof ModelObject ) {
				ModelObject mo = (ModelObject) go ;
				if ( mo.isActive() )
					list.add( mo );
			}
		}
		return list;
	}

	public int getTryb() {
		return tryb;
	}

	public void setTryb(int tryb) {
		this.tryb = tryb;
		forceUpdate = true;
	}

	public void setCameraAngle(int angle) {
		this.camera.setAngle(angle);
		forceUpdate = true;
	}

	public void setObraz(int o) {
		obraz = o;
		forceUpdate = true;
	}
	
	public int getDanger() {
		return danger;
	}
	
	// ============================== CONFIG SINGLETON =========================

	Config() {
		// odczytaj wszystkie dzieci klasy "ModelObject"
		Reflections reflections = new Reflections("opengl");
		Set<Class<? extends GraphicObject>> objectsAll = reflections.getSubTypesOf(GraphicObject.class);
		for (Class<?> c : objectsAll) {
			try {
				if ( ! Modifier.isAbstract(c.getModifiers()) ) // not abstract
				objects.add((GraphicObject) Class.forName(c.getCanonicalName()).getConstructor().newInstance());
//				System.out.printf("Inicjacja %s;\n", c.getCanonicalName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.printf("SUMA: %d obiektów GraphicObject odczytano;\n", objects.size());

		// wybierz kamere z listy obiektów
		for (GraphicObject go : objects) {
			if (go instanceof Camera)
				camera = (Camera) go;
		}

	}
	
	public Point3d getCameraLocation() {
		return camera.getLocation();
	}

	public static Config singleton = null;

	public static Config getInstance() {
		if (!(singleton instanceof Config)) {
			singleton = new Config();
		}

		return singleton;
	}

}
