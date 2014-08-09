import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginForm extends JComponent {
	private JTextField name;
	private JLabel label;

	public LoginForm() {
		setFrame();
	}

	private void setFrame() {
		label = new JLabel("Input your name and press enter");
		name = new JTextField(20);
		setBackground(Color.GRAY);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		add(label, c);
		c.gridy = 2;
		add(name, c);
	}

	public JTextField getInput() {
		return name;
	}

	public String getText() {
		return name.getText();
	}
}
