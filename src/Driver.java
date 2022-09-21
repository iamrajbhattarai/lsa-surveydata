

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import javax.swing.*;

public class Driver {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Applying Least Squares for Survey Data");
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				if(JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?","Please confirm",JOptionPane.YES_NO_CANCEL_OPTION)==JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		
		MainPanel panel = new MainPanel();
		frame.setJMenuBar(panel.setupMenu());
		JPanel name = new JPanel();
		GridLayout layout = new GridLayout(9,1);
		name.setLayout(layout);
		name.setPreferredSize(new Dimension(70,300));
		name.add(new JLabel("Name"));
		name.add(new JLabel("A"));
		name.add(new JLabel("B"));
		name.add(new JLabel("C"));
		name.add(new JLabel("A+B"));
		name.add(new JLabel("B+C"));
		name.add(new JLabel("A+C"));
		name.add(new JLabel("A+B+C"));
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.X_AXIS));
		frame.getContentPane().add(name);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);

	}

}
