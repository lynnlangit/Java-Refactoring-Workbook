import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Table extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new Table();
	}

	private JComponent body;
	private Card selection;
	private JLabel summary;
	int velocity = 1;
	int budget = 4;
	JTabbedPane tabs;

	public Table() {
		super("Planning Game Simulation");
		JComponent buttons = makeButtons();
		this.getContentPane().add(buttons, "West");

		body = new Background(1000, 1000, 190);
		body.addContainerListener(new ContainerListener() {
			// Listen for container changes so we know when
			// to update selection highlight
			public void componentAdded(ContainerEvent e) {
				resetSelection();
			}

			public void componentRemoved(ContainerEvent e) {
				resetSelection();
			}

			private void resetSelection() {
				if (selection != null)
					selection.setBorder(BorderFactory.createLineBorder(Color.blue, 6));

				if (body.getComponentCount() == 0) {
					selection = null;
				} else {
					selection = (Card) body.getComponent(0);
					selection.setBorder(BorderFactory.createLineBorder(Color.red, 6));
				}
			}
		});
		JScrollPane scroll = new JScrollPane(body);
		scroll.setPreferredSize(new Dimension(100, 100));
		this.getContentPane().add(scroll, "Center");

		summary = new JLabel("", SwingConstants.CENTER);
		summary.setText(summary());
		this.getContentPane().add(summary, "South");

		this.pack();
		this.setSize(800, 600);
		this.setVisible(true);
	}

	private JComponent makeButtons() {
		JPanel panel = new JPanel(new GridLayout(0, 1));

		panel.add(new JLabel("Customer"));
		makeCustomerButtons(panel);

		panel.add(new JLabel(" "));

		panel.add(new JLabel("Programmer"));
		makeProgrammerButtons(panel);

		JPanel outer = new JPanel(new BorderLayout());
		outer.add(panel, "North");
		outer.add(new JLabel(""), "Center");
		return outer;
	}

	private void makeCustomerButtons(JPanel panel) {
		JButton button;
		button = new JButton("New");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Card card = new Card();
				body.add(card, 0);
				selection = card;
				updateCost();
				repaint();
			}
		});

		button = new JButton("Split");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selection == null)
					return;
				Card card = new Card(selection);
				body.add(card, 0);
				selection = card;
				updateCost();
				body.repaint();
			}
		});

		button = new JButton("Delete");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (body.getComponentCount() == 0)
					return;

				body.remove(0);
				selection = null;
				if (body.getComponentCount() != 0)
					selection = (Card) body.getComponent(0);
				updateCost();
				body.repaint();
			}
		});

		button = new JButton("Plan");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuffer report = new StringBuffer();
				// Check for cards that need est. or splitting
				for (int i = 0; i < body.getComponentCount(); i++) {
					Card card = (Card) body.getComponent(i);
					if (card.needsEstimate())
						report.append("Needs estimate: " + card.title() + "\n");
					else if (card.needsSplit())
						report.append("Needs to be split: " + card.title() + "\n");
				}

				if (report.length() == 0)
					JOptionPane.showMessageDialog(body,
							"Plan OK; no cards need estimates or splitting", "Issues in plan",
							JOptionPane.OK_OPTION);
				else
					JOptionPane.showMessageDialog(body, report.toString(), "Issues in plan",
							JOptionPane.OK_OPTION);
			}
		});
	}

	private void makeProgrammerButtons(JPanel panel) {
		JButton button;

		button = new JButton("Cost");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selection == null)
					return;
				selection.rotateCost();
				updateCost();
			}
		});

		button = new JButton("Velocity");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				velocity++;
				if (velocity >= 10)
					velocity = 1;
				updateCost();

			}
		});
	}

	private void updateCost() {
		summary.setText(summary());
	}

	private String summary() {
		StringBuffer result = new StringBuffer();
		result.append("Est. Velocity (points/iteration): " + velocity + ".   ");
		result.append("Total cost (points): " + cost() + ".   ");
		result.append("#Cards: " + body.getComponentCount() + ".   ");
		result.append("Est. #iterations: " + (cost() + velocity - 1) / velocity + ".   ");
		return result.toString();
	}

	// Total cost of the set of cards
	private int cost() {
		int total = 0;
		for (int i = 0; i < body.getComponentCount(); i++) {
			Card card = (Card) body.getComponent(i);
			total += card.cost();
		}
		return total;
	}
}
