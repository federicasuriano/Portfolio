import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MostraNotifica extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static String s;
	/**
	 * Launch the application.
	 */
	public static void main(String str) {
		s=str;
		try {
			MostraNotifica dialog = new MostraNotifica();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MostraNotifica() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel_2 = new JLabel(s);
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 67));
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("!");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 67));
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel = new JLabel("");
			contentPanel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
