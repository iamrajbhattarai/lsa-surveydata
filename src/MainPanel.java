
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class MainPanel extends JPanel {

	JLabel textHeader, textBody;
	ArrayList<EquationPanel> eqPanelList;

	JFileChooser fileChooser;
	LSA lsaParser;

	public MainPanel() {
		fileChooser = new JFileChooser();
//		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setPreferredSize(new Dimension(550, 300)); //size of MainPanel

		GridLayout layout = new GridLayout(0, 1);
		this.setLayout(layout);

		JPanel indexPanel = new JPanel();
		GridLayout panelLayout = new GridLayout(1, 7);
		indexPanel.setLayout(panelLayout);
		JLabel indexLabel = new JLabel("A");
		indexPanel.add(indexLabel);

		indexLabel = new JLabel("B");
		indexPanel.add(indexLabel);

		indexLabel = new JLabel("C");
		indexPanel.add(indexLabel);

		indexLabel = new JLabel("Deg");
		indexPanel.add(indexLabel);

		indexLabel = new JLabel("Min");
		indexPanel.add(indexLabel);

		indexLabel = new JLabel("Sec");
		indexPanel.add(indexLabel);

		indexLabel = new JLabel("Weight");
		indexPanel.add(indexLabel);

		this.add(indexPanel); //adding A-sec panel to Mainpanel
		eqPanelList = new ArrayList<EquationPanel>();
		for (int i = 0; i < 7; i++) {
			eqPanelList.add(new EquationPanel());
			this.add(eqPanelList.get(i));
		}

		JPanel btnPanel = new JPanel();
		JButton runProgramBtn = new JButton("Run");
		btnPanel.add(runProgramBtn);
		this.add(btnPanel);
		runProgramBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				lsaParser = new LSA();
				for (EquationPanel eq : eqPanelList) {
					int x=0;
					int y=0;
					int z=0;
					float deg=0;
					float min=0;
					float sec=0;
					int wt=0;
					try {
						x = Integer.parseInt(eq.x.getText().trim());
						y = Integer.parseInt(eq.y.getText().trim());
						z = Integer.parseInt(eq.z.getText().trim());

						deg = Float.parseFloat(eq.deg.getText().trim());
						min = Float.parseFloat(eq.min.getText().trim());
						sec = Float.parseFloat(eq.sec.getText().trim());

						wt = Integer.parseInt(eq.wt.getText().trim());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Equation reqEquation = new Equation(x, y, z, deg, min, sec, wt);
//					System.out.println(reqEquation);
					lsaParser.eqList.add(reqEquation);
				}
				for (Equation eq : lsaParser.eqList) {
					System.out.println(eq);
				}
				lsaParser.calculate();
				String finalOutput = new String("");
				finalOutput += ("Normal Equation For A \n" + lsaParser.nEL[0] + "\n");
				finalOutput += ("Normal Equation For B \n" + lsaParser.nEL[1] + "\n");
				finalOutput += ("Normal Equation For C \n" + lsaParser.nEL[2] + "\n");

				finalOutput += ("Final Adjusted Values\n");
				for (String otpt : lsaParser.output) {
					finalOutput += (otpt + "\n");
				}
				JOptionPane.showMessageDialog(null, finalOutput, "Output", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		// TODO Auto-generated method stub
//		Equation[] eqList = new Equation[5];
//		eqList[0] = new Equation(1, 0, 0, 32, 2, 41.0f, 1);
//		eqList[1] = new Equation(0, 1, 0, 24, 21, 15.0f, 1);
//		eqList[2] = new Equation(1, 1, 0, 56, 23, 54.0f, 2);
//		eqList[3] = new Equation(0, 0, 1, 47, 12, 31.0f, 1);
//		eqList[4] = new Equation(0, 1, 1, 71, 33, 49.0f, 2);

	}

	public class EquationPanel extends JPanel {
		JTextField x, y, z, deg, min, sec, wt;

		public EquationPanel() {
			GridLayout layout = new GridLayout(1, 7);
			this.setLayout(layout);
			x = new JTextField(5);
			y = new JTextField(5);
			z = new JTextField(5);
			deg = new JTextField(5);
			min = new JTextField(5);
			sec = new JTextField(5);
			wt = new JTextField(5);

//			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//			this.setAlignmentX(LEFT_ALIGNMENT);
			this.add(x);
			this.add(y);
			this.add(z);
			this.add(deg);
			this.add(min);
			this.add(sec);
			this.add(wt);
		}
	}

	JMenuBar setupMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");

		JMenu helpMenu = new JMenu("Help");

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		JMenuItem loadFromCsv = new JMenuItem("Load From CSV");
		fileMenu.add(loadFromCsv);

		loadFromCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadCsvFile();

			}
		});

		JMenuItem aboutOption = new JMenuItem("About");
		helpMenu.add(aboutOption);

		aboutOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JOptionPane.showMessageDialog(null, "This program is able to apply least squares principle based normal euations method to adjust survey data.\n\nUser can directly input observation equations onto the GUI component shown.\n\nUser can import CSV file containing observation data and determine precise value.");
			}
		});

//		loadFromCsv.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//				JOptionPane.showMessageDialog(null, "Test About2");
//			}
//		});
		return menuBar;
	}

	private File choose_file(int selectionMode) {
		int file_result;
		fileChooser.setFileSelectionMode(selectionMode);

		file_result = fileChooser.showOpenDialog(this);
		if (file_result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			return selectedFile;
		} else {
			return null;
		}
	}

	private void loadCsvFile() {
		File selectedCsvFile = choose_file(JFileChooser.FILES_ONLY);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(selectedCsvFile));
			String row = new String("");
			int i = 0;
			while ((row = reader.readLine()) != null) { //reads and returns the current line and references the next line

				System.out.println(row);
				String[] data = row.split(","); //split rows by comma, is function of String Object
				EquationPanel eq = eqPanelList.get(i);
				eq.x.setText(data[0]);
				eq.y.setText(data[1]);
				eq.z.setText(data[2]);
				eq.deg.setText(data[3]);
				eq.min.setText(data[4]);
				eq.sec.setText(data[5]);
				eq.wt.setText(data[6]);
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
