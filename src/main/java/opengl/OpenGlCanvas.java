package opengl;

import java.awt.Point;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL2.*;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.vecmath.Point3d;

import lombok.Getter;
import opengl.model.GraphicObject;
import opengl.model.ModelObject;
import opengl.model.ObjModel;

import com.jogamp.opengl.util.gl2.GLUT;

public class OpenGlCanvas extends GLCanvas implements GLEventListener, MouseListener, KeyListener,
		MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;

	@Getter
	private GL2 gl;
	@Getter
	private GLU glu; // for the GL Utility
	@Getter
	private GLUT glut;
	@Getter
	OpenGL openGL;

	private JPopupMenu popupMenu = new JPopupMenu();
	private List<Point> mouseHistory = new ArrayList<Point>();

	Config c = Config.getInstance();
	
	public OpenGlCanvas(GLCapabilities capabilities) {
		super(capabilities);
		_construct();
	}

	public OpenGlCanvas() {
		super();
		_construct();
	}
	
	/** Constructor to setup the GUI for this Component */
	public void _construct() {
		c.setGlCanvas(this);
		this.addGLEventListener(this);
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);

		/**
		 * =============== MENU - PRAWY PRZYCISK MYSZY =================
		 */

		/**
		 * =============== MENU - WIDOK =================
		 */

		JMenu widok = new JMenu("Widok");
		JRadioButtonMenuItem widokWypelnienie = new JRadioButtonMenuItem("Wypełnienie");
		widokWypelnienie.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				c.setTryb(0);
			}
		});
		widokWypelnienie.setSelected(true);
		JRadioButtonMenuItem widokPunkty = new JRadioButtonMenuItem("Punkty");
		widokPunkty.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				c.setTryb(1);
			}
		});
		JRadioButtonMenuItem widokLinie = new JRadioButtonMenuItem("Linie");
		widokLinie.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				c.setTryb(2);
			}
		});

		ButtonGroup widokGroup = new ButtonGroup();
		widokGroup.add(widokWypelnienie);
		widok.add(widokWypelnienie);
		widokGroup.add(widokPunkty);
		widok.add(widokPunkty);
		widokGroup.add(widokLinie);
		widok.add(widokLinie);
		this.popupMenu.add(widok);

		/**
		 * =============== MENU - MODELE =================
		 */
		this.popupMenu.addSeparator();
		JMenuItem modeleDisabled = new JMenuItem("Wybierz model");
		modeleDisabled.setEnabled(false);
		this.popupMenu.add(modeleDisabled);

		ButtonGroup modelGroup = new ButtonGroup();

		for (ModelObject go : c.getModelObject()) {
			JRadioButtonMenuItem goItemTmp = new JRadioButtonMenuItem(go.getName());

			// zapisz do Config.currentModel wybrany model
			goItemTmp.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					JMenuItem mItem = (JMenuItem) e.getSource();
					for (ModelObject go : c.getModelObject())
						if (go.getName().equals(mItem.getText()))
							c.currentModel = go;

				}
			});

			modelGroup.add(goItemTmp);
			this.popupMenu.add(goItemTmp);
		}

		/**
		 * =============== MENU - KAMERA =================
		 */
		this.popupMenu.addSeparator();
		JMenu kamera = new JMenu("Kamera");

		JMenuItem kameraObraz = new JMenuItem("Obraz");
		kameraObraz.setEnabled(false);
		kamera.add(kameraObraz);

		JRadioButtonMenuItem kameraObrazMono = new JRadioButtonMenuItem("Mono");
		kameraObrazMono.setSelected(true);
		kameraObrazMono.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				c.setObraz(0);
			}
		});

		JRadioButtonMenuItem kameraObrazStareo = new JRadioButtonMenuItem("Stareo");
		kameraObrazStareo.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				c.setObraz(1);
			}
		});
		
		ButtonGroup kameraObrazGroup = new ButtonGroup();
		kameraObrazGroup.add(kameraObrazMono);
		kamera.add(kameraObrazMono);
		kameraObrazGroup.add(kameraObrazStareo);
		kamera.add(kameraObrazStareo);
		kamera.addSeparator();
		
		JMenuItem kameraKat = new JMenuItem("Kąt paerspektywy");
		kameraKat.setEnabled(false);
		kamera.add(kameraKat);

		JRadioButtonMenuItem kameraKat30 = new JRadioButtonMenuItem("30");
		kameraKat30.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				c.setCameraAngle(30);
			}
		});
		
		JRadioButtonMenuItem kameraKat45 = new JRadioButtonMenuItem("45");
		kameraKat45.setSelected(true);
		kameraKat45.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				c.setCameraAngle(45);
			}
		});
		
		JRadioButtonMenuItem kameraKat90 = new JRadioButtonMenuItem("90");
		kameraKat90.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				c.setCameraAngle(90);
			}
		});
		
		JRadioButtonMenuItem kameraKat120 = new JRadioButtonMenuItem("120");
		kameraKat120.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				c.setCameraAngle(120);
			}
		});

		ButtonGroup kameraKatGroup = new ButtonGroup();
		kameraKatGroup.add(kameraKat30);
		kamera.add(kameraKat30);
		kameraKatGroup.add(kameraKat45);
		kamera.add(kameraKat45);
		kameraKatGroup.add(kameraKat90);
		kamera.add(kameraKat90);
		kameraKatGroup.add(kameraKat120);
		kamera.add(kameraKat120);
		
		this.popupMenu.add(kamera);
	}

	// ------ Implement methods declared in GLEventListener ------

	/**
	 * Called back immediately after the OpenGL context is initialized. Can be used to perform one-time
	 * initialization. Run only once.
	 */
	public void init(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2(); // get the OpenGL graphics context
		glu = new GLU(); // get GL Utilities
		glut = new GLUT();

		GraphicObject.setC(c);
		GraphicObject.setGl(gl);
		GraphicObject.setGlu(glu);
		GraphicObject.setGlut(glut);

		openGL = new OpenGL();
		openGL.setGl(gl).setGlu(glu).setGlut(glut).setWidth(drawable.getSurfaceWidth())
				.setHeight(drawable.getSurfaceHeight());

		gl.glViewport(0, 0, drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
 
		// ----- Your OpenGL initialization code here -----
		openGL.init();
		
		// inicjacja wszystkich obiektow
		for (GraphicObject go : c.objects)
			if ( (go instanceof ModelObject) ) {
				if ( ((ModelObject)go).isActive() ) go.init();
			} else
			go.init();
	}

	/**
	 * Call-back handler for window re-size event. Also called when the drawable is first set to visible.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context

		gl.glViewport(0, 0, width, height);

		c.width = width;
		c.height = height;

		if ( c.obraz == 0 )
		c.camera.setup(0);
	}

	/**
	 * Called back by the animator to perform rendering.
	 */
	public void display(GLAutoDrawable drawable) {
		c.prepareDisplay();

		if ( c.obraz == 0 ) { // mono

			openGL.clean();
			gl.glViewport(0, 0, drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
			
		if (c.camera.needSetup() || c.forceUpdate)
			c.camera.setup(0);
		
			display_frame();
		} else { //stereo
			openGL.clean();
			gl.glDrawBuffer(GL_BACK_LEFT);
			gl.glViewport(0, 0, drawable.getSurfaceWidth()/2, drawable.getSurfaceHeight());
			c.camera.setup(1); // left eye
			display_frame();

			gl.glDrawBuffer(GL_BACK_RIGHT); 
			gl.glViewport(drawable.getSurfaceWidth()/2, 0, drawable.getSurfaceWidth()/2, drawable.getSurfaceHeight());
			c.camera.setup(2); // right eye
			display_frame();
		} //stereo
		

		
		drawable.getGL().getGL2().glFinish();
	
	}
	
	private void display_frame() {
		
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
		openGL.display();

		// wyswietl wszystkie modele
		for (ModelObject mo : c.getModelObject())
			if (mo.isVisable())
				mo.display();
	}

	/**
	 * Called back before the OpenGL context is destroyed. Release resource such as buffers.
	 */
	public void dispose(GLAutoDrawable drawable) {
	}

	/**
	 * ================================== KLAWIATURA =================================
	 */

	public synchronized void keyPressed(KeyEvent e) {
		c.pressed.add(e.getKeyCode());
	}

	private void keyLauncher(Set<Integer> pressed) {

		if ( pressed.contains(KeyEvent.VK_V) ) {
			c.currentModel.toggleVisable();
		}
		if ( pressed.contains(KeyEvent.VK_SPACE) ) {
			c.currentModel.toggleAnimate();
		}
		if ( pressed.contains(KeyEvent.VK_P) ) {
			c.camera.speedUp();
		}
		if ( pressed.contains(KeyEvent.VK_L) ) {
			c.camera.speedDown();
		}
		if (pressed.contains(KeyEvent.VK_ALT) && pressed.contains(KeyEvent.VK_ENTER)) { // / ALT + ENTER

			// not implemented!
		}
	}

	public synchronized void keyReleased(KeyEvent e) {
		keyLauncher(c.pressed);
		c.pressed.remove(e.getKeyCode());
	}

	public void keyTyped(KeyEvent e) {
	}

	/**
	 * ================================== MYSZ =================================
	 */

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if ((e.isPopupTrigger()) && (SwingUtilities.isRightMouseButton(e)))
			showMenu(e);
	}

	public void mouseReleased(MouseEvent e) {
//		c.mouse_pressed = false;
		
		mouseHistoryClear();
		
		if (e.isPopupTrigger())
			showMenu(e);
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
		c.mouse_x = 0;
		c.mouse_y = 0;
	}

	public void mouseDragged(MouseEvent e) {
		mouseHistoryAdd(e.getX(), e.getY());
		mouseModelMoveLauncher();
	}

	private void showMenu(MouseEvent e) {
		this.popupMenu.show(e.getComponent(), e.getX(), e.getY());
	}

	/**
	 * update Config.mouseX i Config.mouseY
	 */
	public void mouseMoved(MouseEvent paramMouseEvent) {
		c.mouse_x = paramMouseEvent.getX();
		c.mouse_y = paramMouseEvent.getY();
	}

	private void mouseModelMoveLauncher() {
		if (c.currentModel instanceof ModelObject) { // wybrano jakis model
			Point dist = mouseHistoryDistance();
			c.currentModel.updateLocation(dist);
//			System.out.printf("x(%d); y(%d);\n", dist.x, dist.y);
		}
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		c.mouse_wheel = e.getWheelRotation();
		if (c.mouse_wheel == -1)
			c.mouse_wheel = 1;
		else if (c.mouse_wheel == 1)
			c.mouse_wheel = -1;
		
		mouseModelScaleLauncher();
	}

	private void mouseModelScaleLauncher() {
		if (c.currentModel instanceof ModelObject) { // wybrano jakis model
			float scale = c.currentModel.getScale();
			float scale_e = (c.mouse_wheel>0)? 1.02f : 0.98f ;
			
			scale *= scale_e;
			c.currentModel.setScale(scale);
		}
	}
	
	/**
	 * =========== HISTORIA RUCHÓW MYSZKI ================
	 */
	private void mouseHistoryClear() {
		mouseHistory.clear();
	}
	
	private void mouseHistoryAdd(int x, int y) {
		mouseHistory.add(new Point(x,y));
	}
	
	/**
	 * zmiana pozycji myszki X i Y
	 */
	private Point mouseHistoryDistance() {
		Point last = new Point(0,0);
		Point before = new Point(0,0);
		
		int s = mouseHistory.size();
		if ( s>=2 ) {
			last = mouseHistory.get(s-1);
			before = mouseHistory.get(s-2);
		}
		
		return new Point(last.x - before.x, last.y - before.y);
	}
}
