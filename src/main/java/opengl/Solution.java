package opengl;

import javax.swing.UIManager;

public class Solution {

	public static void main(String[] args) {
		System.setProperty("sun.java2d.noddraw", "true");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			Thread t = new Thread(new Runnable() {
				public void run() {
					AppFrame frame = new AppFrame();
					frame.setVisible(true);
				}
			});
			t.run();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
