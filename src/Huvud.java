import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import static javax.swing.JOptionPane.*;

public class Huvud extends JFrame {
	ArrayList<Person> startnrLista = new ArrayList<Person>();
	ArrayList<Person> namnLista = new ArrayList<Person>();
	ArrayList<Person> ålderLista = new ArrayList<Person>();
	ArrayList<Person> tidLista = new ArrayList<Person>();
	NamnListaCompare namnCom = new NamnListaCompare();
	ÅlderListaCompare ålderCom = new ÅlderListaCompare();
	TidListaCompare tidCom = new TidListaCompare();
	JTextArea textyta;
	JRadioButton startnr, namn, ålder, tid;
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
		ålder = new JRadioButton("Ålder");
		east.add(ålder);
		tid = new JRadioButton("Tid");
		east.add(tid);

		ButtonGroup bg = new ButtonGroup();
		bg.add(startnr);
		startnr.addActionListener(new VisaLyss());
		bg.add(namn);
		namn.addActionListener(new VisaLyss());
		bg.add(ålder);
		ålder.addActionListener(new VisaLyss());
		bg.add(tid);
		tid.addActionListener(new VisaLyss());

		JPanel syd = new JPanel();
		add(syd, BorderLayout.SOUTH);

		JButton ny = new JButton("Ny");
		ny.addActionListener(new NyLyss()); // Lyssnar om ny-knappen blir tryckt
											// på.
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
		private JTextField namn2, land, ålder2;

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

			JPanel ålderPan = new JPanel();
			add(ålderPan);
			ålderPan.add(new JLabel("Ålder: "));
			ålder2 = new JTextField(3);
			ålderPan.add(ålder2);

		}

		public String getNamn() {
			return namn2.getText();
		}

		public String getLand() {
			return land.getText();
		}

		public int getÅlder() {
			return Integer.parseInt(ålder2.getText());
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
								"Namn- eller landfältet är ej ifyllt!");
					}
					int ålder = nyk.getÅlder();
					if (ålder < 18) {
						throw new IllegalArgumentException();
					}
					Person löpare = new Person(position, namn, land, ålder);
					binaryAdd(namnLista, löpare, namnCom);
					binaryAdd(ålderLista, löpare, ålderCom);
					startnrLista.add(löpare);
					position++;
					return;
				} catch (NumberFormatException e) {
					showMessageDialog(Huvud.this, "Fel! Ålder är en siffra!");
				} catch (MissingFormatArgumentException e) {
					showMessageDialog(Huvud.this,
							"Något av fälten är inte ifyllt!");
				} catch (IllegalArgumentException e) {
					showMessageDialog(Huvud.this,
							"Tävlande måste vara äldre än 18 år!");
				}
			}
		}
	}

	class VisaLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			textyta.setText("");
			if (startnr.isSelected() == true) {
				for (Person löpare : startnrLista) {
					textyta.append(löpare.toString() + "\n");
				}
			} else if (namn.isSelected() == true) {
				for (Person namn : namnLista) {
					textyta.append(namn.toString() + "\n");
				}
			} else if (ålder.isSelected() == true) {
				for (Person ålder : ålderLista) {
					textyta.append(ålder.toString() + "\n");
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
					for (Person löpare : startnrLista) {
						position = löpare.getPosition();
						boolean addTid = tidLista.contains(löpare);
						if (startnummer == position) { 
							löpare.setTiden(tiden);
							if (addTid) {
								Collections.sort(tidLista,
										new TidListaCompare());
							} else {
								binaryAdd(tidLista, löpare, tidCom);
							}
						}
						position++;
					}
					return;
				} catch (NumberFormatException e) {
					showMessageDialog(Huvud.this, "Ange en siffra!");
				} catch (IllegalArgumentException e) {
					showMessageDialog(Huvud.this, "Startnumret stämmer inte!");
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

	class ÅlderListaCompare implements Comparator<Person> {
		public int compare(Person pers1, Person pers2) {
			return pers1.getÅlder() - pers2.getÅlder();
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