import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import static javax.swing.JOptionPane.*;

public class Huvud extends JFrame {
	ArrayList<Person> startnrLista = new ArrayList<Person>();
	ArrayList<Person> namnLista = new ArrayList<Person>();
	ArrayList<Person> �lderLista = new ArrayList<Person>();
	ArrayList<Person> tidLista = new ArrayList<Person>();
	NamnListaCompare namnCom = new NamnListaCompare();
	�lderListaCompare �lderCom = new �lderListaCompare();
	TidListaCompare tidCom = new TidListaCompare();
	JTextArea textyta;
	JRadioButton startnr, namn, �lder, tid;
	JButton ny, visa, tidKnapp;
	int position = 1;

	Huvud() {
		super("Huvud");
		JPanel norr = new JPanel();
		add(norr, BorderLayout.NORTH);
		norr.add(new JLabel("DSV Kista Maraton"));
		JScrollPane scroll = new JScrollPane();
		add(scroll);

		JPanel center = new JPanel();
		add(center, BorderLayout.CENTER);
		textyta = new JTextArea(30, 19);
		center.add(textyta);
		center.add(new JScrollPane());

		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		add(east, BorderLayout.EAST);
		startnr = new JRadioButton("Startnr");
		east.add(startnr);
		namn = new JRadioButton("Namn");
		east.add(namn);
		�lder = new JRadioButton("�lder");
		east.add(�lder);
		tid = new JRadioButton("Tid");
		east.add(tid);

		ButtonGroup bg = new ButtonGroup();
		bg.add(startnr);
		startnr.addActionListener(new VisaLyss());
		bg.add(namn);
		namn.addActionListener(new VisaLyss());
		bg.add(�lder);
		�lder.addActionListener(new VisaLyss());
		bg.add(tid);
		tid.addActionListener(new VisaLyss());

		JPanel syd = new JPanel();
		add(syd, BorderLayout.SOUTH);

		JButton ny = new JButton("Ny");
		ny.addActionListener(new NyLyss()); // Lyssnar om ny-knappen blir tryckt
											// p�.
		syd.add(ny);

		JButton visa = new JButton("Visa");
		visa.addActionListener(new VisaLyss());
		syd.add(visa);

		JButton tidKnapp = new JButton("Tid");
		tidKnapp.addActionListener(new TidLyss());
		syd.add(tidKnapp);

		textyta.setEditable(false);
		setVisible(true);
		setSize(300, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	class NyKnapp extends JPanel { // Viktigt fel, extends JPanel och inte
									// JFrame.
		private JTextField namn2, land, �lder2;

		NyKnapp() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JLabel start = new JLabel("Startnr " + position);
			add(start);
			JPanel namnPan = new JPanel();
			namnPan.add(new JLabel("Namn: "));
			namn2 = new JTextField(15);
			namnPan.add(namn2);
			add(namnPan);

			JPanel landPan = new JPanel();
			add(landPan);
			landPan.add(new JLabel("Land: "));
			land = new JTextField(15);
			landPan.add(land);

			JPanel �lderPan = new JPanel();
			add(�lderPan);
			�lderPan.add(new JLabel("�lder: "));
			�lder2 = new JTextField(3);
			�lderPan.add(�lder2);

		}

		public String getNamn() {
			return namn2.getText();
		}

		public String getLand() {
			return land.getText();
		}

		public int get�lder() {
			return Integer.parseInt(�lder2.getText());
		}
	}

	class TidKnapp extends JPanel {
		private JTextField startnummer, tiden;

		TidKnapp() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel startPan = new JPanel();
			startPan.add(new JLabel("Startnr: "));
			startnummer = new JTextField(5);
			startPan.add(startnummer);
			add(startPan);

			JPanel tidPan = new JPanel();
			tidPan.add(new JLabel("Tid: "));
			tiden = new JTextField(5);
			tidPan.add(tiden);
			add(tidPan);
		}

		public int getStartnummer() {
			return Integer.parseInt(startnummer.getText());
		}

		public double getTiden() {
			return Double.parseDouble(tiden.getText());
		}

		public void setTiden(double tiden) {
		}
	}

	class NyLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			NyKnapp nyk = new NyKnapp();

			for (;;) {
				try {
					int svar = JOptionPane.showConfirmDialog(Huvud.this, nyk,
							null, JOptionPane.OK_CANCEL_OPTION);
					if (svar != OK_OPTION)
						return;
					String namn = nyk.getNamn();
					String land = nyk.getLand();
					if (namn.isEmpty() || land.isEmpty()) {
						throw new MissingFormatArgumentException(
								"Namn- eller landf�ltet �r ej ifyllt!");
					}
					int �lder = nyk.get�lder();
					if (�lder < 18) {
						throw new IllegalArgumentException();
					}
					Person l�pare = new Person(position, namn, land, �lder);
					binaryAdd(namnLista, l�pare, namnCom);
					binaryAdd(�lderLista, l�pare, �lderCom);
					startnrLista.add(l�pare);
					position++;
					return;
				} catch (NumberFormatException e) {
					showMessageDialog(Huvud.this, "Fel! �lder �r en siffra!");
				} catch (MissingFormatArgumentException e) {
					showMessageDialog(Huvud.this,
							"N�got av f�lten �r inte ifyllt!");
				} catch (IllegalArgumentException e) {
					showMessageDialog(Huvud.this,
							"T�vlande m�ste vara �ldre �n 18 �r!");
				}
			}
		}
	}

	class VisaLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			textyta.setText("");
			if (startnr.isSelected() == true) {
				for (Person l�pare : startnrLista) {
					textyta.append(l�pare.toString() + "\n");
				}
			} else if (namn.isSelected() == true) {
				for (Person namn : namnLista) {
					textyta.append(namn.toString() + "\n");
				}
			} else if (�lder.isSelected() == true) {
				for (Person �lder : �lderLista) {
					textyta.append(�lder.toString() + "\n");
				}
			} else if (tid.isSelected() == true) {
				for (Person tid : tidLista) {
					textyta.append(tid.toString() + "\n");
				}
			}
		}
	}

	class TidLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			TidKnapp tidk = new TidKnapp();

			for (;;) {
				try {
					int svar2 = JOptionPane.showConfirmDialog(Huvud.this, tidk,
							null, JOptionPane.OK_CANCEL_OPTION);
					if (svar2 != OK_OPTION)
						return;
					int startnummer = tidk.getStartnummer();
					if (startnummer < 1 || startnummer > startnrLista.size()) {
						throw new IllegalArgumentException();
					}
					double tiden = tidk.getTiden();
					for (Person l�pare : startnrLista) {
						position = l�pare.getPosition();
						boolean addTid = tidLista.contains(l�pare);
						if (startnummer == position) { 
							l�pare.setTiden(tiden);
							if (addTid) {
								Collections.sort(tidLista,
										new TidListaCompare());
							} else {
								binaryAdd(tidLista, l�pare, tidCom);
							}
						}
						position++;
					}
					return;
				} catch (NumberFormatException e) {
					showMessageDialog(Huvud.this, "Ange en siffra!");
				} catch (IllegalArgumentException e) {
					showMessageDialog(Huvud.this, "Startnumret st�mmer inte!");
				}
			}
		}
	}

	void binaryAdd(ArrayList<Person> platsLista, Person pers, Comparator com) {
		int placering = Collections.binarySearch(platsLista, pers, com);
		if (placering < 0)
			placering = -placering - 1;
		platsLista.add(placering, pers);

	}

	public static <tidLista> void sort(ArrayList<tidLista> list,
			Comparator<? super tidLista> c) {
	}

	class NamnListaCompare implements Comparator<Person> {
		public int compare(Person pers1, Person pers2) {
			String namn1 = pers1.getNamn();
			String namn2 = pers2.getNamn();
			return namn1.compareTo(namn2);
		}
	}

	class �lderListaCompare implements Comparator<Person> {
		public int compare(Person pers1, Person pers2) {
			return pers1.get�lder() - pers2.get�lder();
		}
	}

	class TidListaCompare implements Comparator<Person> {
		public int compare(Person pers1, Person pers2) {
			Double tid1 = pers1.getTiden();
			Double tid2 = pers2.getTiden();
			return tid1.compareTo(tid2);
		}
	}

	public static void main(String[] args) {
		new Huvud();
	}
}