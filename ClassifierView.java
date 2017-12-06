package datamining.classifier;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Create by LinBin on 2017/11/11
 */

@SuppressWarnings("serial")
public class ClassifierView extends JFrame{

	ClassifierView classifierView;
	private Layout layout;
	private TextView tvDir,tvName;
	private EditView etCLength,etCWidth,etPLength,etPWidth;
	private Button btOpen,btAnaly;
	private Data data;
	private TipListener tipListener;
	public ClassifierView() {
		classifierView=this;
		setLayout(new BorderLayout());
		layout=new Layout();
		tvDir=new TextView();
		tvName=new TextView();
		etCLength=new EditView();
		etCWidth=new EditView();
		etPLength=new EditView();
		etPWidth=new EditView();
		layout.setLayout(new GridLayout(3, 1));
		layout.add(tvDir);
		layout.add(tvName);
		btOpen=new Button("选择数据源");
		btAnaly=new Button("预测");
		btOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileChooser chooser=new FileChooser("数据源(iris.data)","data");
				int returnValue=chooser.showOpenDialog(ClassifierView.this);
				if (returnValue==FileChooser.APPROVE_OPTION) {
					tvDir.setText("路径: "+chooser.getCurrentDirectory().toString());
					tvName.setText("文件名: "+chooser.getSelectedFile().getName());
					try {
						String dir=chooser.getSelectedFile().getAbsolutePath();
						data=ClassifierHelper.getData(dir);
						etCLength.setText("0");
						etCWidth.setText("0");
						etPLength.setText("0");
						etPWidth.setText("0");
						etCLength.setEditable(true);
						etCWidth.setEditable(true);
						etPLength.setEditable(true);
						etPWidth.setEditable(true);
						btAnaly.removeActionListener(tipListener);
						btAnaly.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								try {
									double calyxLength=Double.valueOf(etCLength.getText().toString());
									double calyxWidth=Double.valueOf(etCWidth.getText().toString());
									double petalLength=Double.valueOf(etPLength.getText().toString());
									double petalWidth=Double.valueOf(etPWidth.getText().toString());
									if (calyxLength<=0||calyxWidth<=0||petalLength<=0||petalWidth<=0) {
										JOptionPane.showMessageDialog(null, "输入的数值不能小于等于0,请重新输入");
									}else {
										String result=ClassifierHelper.classifier(
												data,calyxLength,calyxWidth,petalLength,petalWidth);
										JOptionPane.showMessageDialog(null, result);
									}
								}catch (Exception e) {
									JOptionPane.showMessageDialog(null, "请确认输入为数字");
								}	
							}
						});
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "文件打开失败");
						e1.printStackTrace();
					}
				}
				
				if (returnValue==FileChooser.CANCEL_OPTION) {
					
				}
			}
		});
		layout.add(btOpen);
		add(layout,BorderLayout.NORTH);	
		
		layout=new Layout();
		layout.setLayout(new GridLayout(4, 2));
		layout.add(new Label("花萼长度"));
		layout.add(etCLength);
		layout.add(new Label("花萼宽度"));
		layout.add(etCWidth);
		layout.add(new Label("花瓣长度"));
		layout.add(etPLength);
		layout.add(new Label("花瓣宽度"));
		layout.add(etPWidth);
		add(layout, BorderLayout.CENTER);

		layout=new Layout();
		tipListener=new TipListener();
		btAnaly.addActionListener(tipListener);
		layout.add(btAnaly);
		add(layout, BorderLayout.SOUTH);
	}
}

@SuppressWarnings("serial")
class Layout extends JPanel{
	public Layout() {
		super();
	}
}

@SuppressWarnings("serial")
class TextView extends JTextField{
	public TextView() {
		super();
		setEditable(false);
	}
}

@SuppressWarnings("serial")
class EditView extends JTextField{
	public EditView() {
		super();
		setEditable(false);
		setText("请先选择数据源");
	}
}

@SuppressWarnings("serial")
class Button extends JButton {
	public Button(String text) {
		super(text);
	}
}

@SuppressWarnings("serial")
class FileChooser extends JFileChooser{
	public FileChooser(String description,String ...strings) {
		super();
		setFileFilter(new FileNameExtensionFilter(description,strings));
	}
}

@SuppressWarnings("serial")
class Label extends JLabel{
	public Label(String text) {
		super(text);
	}
}

class TipListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "请先选择数据源");
	}
}


