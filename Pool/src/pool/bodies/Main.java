package pool.bodies;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		PoolPanel poolPanel = new PoolPanel();
		JFrame jframe = new JFrame();
		jframe.add(poolPanel);
		jframe.setDefaultCloseOperation(3);
		jframe.pack();
		jframe.setSize(jframe.getSize().width + 80, jframe.getSize().height + 80);
		jframe.setResizable(false);
		jframe.setLayout(new GridBagLayout());
		jframe.setBackground(new Color(200, 200, 255));
		jframe.setVisible(true);
		Thread.sleep(1000);
		poolPanel.start();
	}
}
