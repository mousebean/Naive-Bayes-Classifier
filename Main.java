package datamining.classifier;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Create by LinBin on 2017/11/11
 */

public class Main {

	private static ClassifierView view;
	
	public static void main(String[] args) throws Exception{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		view=new ClassifierView();
		run(view);
	}
	
	public static void run(final ClassifierView f) {
		run(f, 300, 300,"贝叶斯选择器");
	}
	
	/**
	 * 在UI线程启动界面
	 * @param f 界面
	 * @param width 界面宽
	 * @param height 界面高
	 * @param title 界面标题
	 */
	public static void run(final ClassifierView f,final int width,final int height,final String title) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
				
				f.setTitle(title);
				f.setDefaultCloseOperation(ClassifierView.EXIT_ON_CLOSE);
				f.setSize(width, height);
				f.setLocation(new Point((screenSize.width-width)/2, (screenSize.height-height)/2));
				f.setVisible(true);
			}
		});
	}
}
