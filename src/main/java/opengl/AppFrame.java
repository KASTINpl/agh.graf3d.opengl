package opengl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

public class AppFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	Config c = Config.getInstance();
	
	//private boolean fullScreen = false; // full-screen or windowed mode
	//private GraphicsDevice device;

	public AppFrame() {

//	      device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	      
		
		setTitle("App Frame");

	    setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Map<String, Integer> appSize = this.appSize();

		setBounds(appSize.get("left"), appSize.get("top"), appSize.get("width"), appSize.get("height"));
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		GLProfile profile = GLProfile.getDefault();
	    GLCapabilities capabilities = new GLCapabilities(profile);

capabilities.setSampleBuffers(true);
capabilities.setNumSamples(4);
//capabilities.setStereo(true);

		GLCanvas canvas = new OpenGlCanvas(capabilities);

		final FPSAnimator animator = new FPSAnimator(canvas, c.FPS, true);

		getContentPane().add(canvas, BorderLayout.CENTER);

		//TopPanel top_panel = new TopPanel();
		//top_panel.setGLCanvas(canvas);
		//getContentPane().add(top_panel, BorderLayout.NORTH);

//		BottomPanel bottom_panel = new BottomPanel();
//		getContentPane().add(bottom_panel, BorderLayout.SOUTH);

		/*
		 * addComponentListener(new ComponentAdapter() {
		 * 
		 * @Override public void componentResized(ComponentEvent e) { // resize SWING window action } });
		 */

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Use a dedicate thread to run the stop() to ensure that the
				// animator stops before program exits.
				new Thread() {
					@Override
					public void run() {
						if (animator.isStarted())
							animator.stop();
						System.exit(0);
					}
				}.start();
			}
		});
		
		animator.start();
	}

	/**
	 * wygeneruj rozmiary aplikacji i jej ułożenie na ekranie na podstawie zmiennej "app_size"
	 * 
	 * @return map
	 */
	private Map<String, Integer> appSize() {
		Map<String, Integer> map = new HashMap<String, Integer>();

		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Integer s_width = screenSize.width;
		Integer s_height = screenSize.height;

		map.put("width", (int) (s_width * c.app_size));
		map.put("height", (int) (s_height * c.app_size));
		map.put("top", (int) (s_height * (1 - c.app_size) / 2));
		map.put("left", (int) (s_width * (1 - c.app_size) / 2));

		return map;
	}
}
