package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ExitFrame {
	JFrame exitFrame;
	ImageIcon iconShakey, iconClose, iconOK, iconCancel;
	Image imageClose, imageOK, imageCancel;
	JPanel pnlexitHdr;
	JButton btnCloseExit, btnOK, btnCancel;
	JLabel lblConfirm;

	public ExitFrame() {
		// Create close button icon
		iconClose = new ImageIcon("src/assets/PNGClose.png");
		imageClose = iconClose.getImage().getScaledInstance(39, 39, Image.SCALE_SMOOTH);
		iconClose.setImage(imageClose);

	}

	public void exitframe() {
		exitFrame = new JFrame("Exit Muse.ic");
		exitFrame.setSize(300, 150);
		exitFrame.setUndecorated(true);
		exitFrame.setOpacity(0.9f);
		exitFrame.setLocationRelativeTo(null);
		exitFrame.setLayout(null);
		exitFrame.getContentPane().setBackground(new Color(38, 38, 38));

		exitFrame.setVisible(true);

		pnlexitHdr = new JPanel();
		pnlexitHdr.setBackground(Color.black);
		pnlexitHdr.setLayout(null);
		pnlexitHdr.setBounds(0, 0, 300, 50);

		btnCloseExit = new JButton(iconClose);
		btnCloseExit.setBounds(255, 5, 40, 40);
		btnCloseExit.setFocusPainted(false);
		btnCloseExit.setBorderPainted(false);
		btnCloseExit.setContentAreaFilled(false);
		btnCloseExit.setToolTipText("Cancel");
		btnCloseExit.setBackground(Color.black);
		btnCloseExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitFrame.dispose();
			}
		});

		lblConfirm = new JLabel("Confirm Exit");
		lblConfirm.setBounds(15, 5, 235, 40);
		lblConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirm.setForeground(Color.WHITE);
		lblConfirm.setFont(new Font("Times New Roman", Font.BOLD, 18));
		pnlexitHdr.add(lblConfirm);

		iconOK = new ImageIcon("src/assets/btnOK.png");
		imageOK = iconOK.getImage().getScaledInstance(98, 38, Image.SCALE_SMOOTH);
		iconOK.setImage(imageOK);

		btnOK = new JButton(iconOK);
		btnOK.setBounds(30, 80, 100, 40);
		btnOK.setBackground(new Color(38, 38, 38));
		btnOK.setFocusPainted(false);
		btnOK.setBorderPainted(false);
		btnOK.setContentAreaFilled(false);
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		iconCancel = new ImageIcon("src/assets/btnCancel.png");
		imageCancel = iconCancel.getImage().getScaledInstance(98, 38, Image.SCALE_SMOOTH);
		iconCancel.setImage(imageCancel);

		btnCancel = new JButton(iconCancel);
		btnCancel.setBounds(170, 80, 100, 40);
		btnCancel.setBackground(new Color(38, 38, 38));
		btnCancel.setFocusPainted(false);
		btnCancel.setBorderPainted(false);
		btnCancel.setContentAreaFilled(false);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitFrame.dispose();
			}

		});
		
		pnlexitHdr.add(btnCloseExit);
		exitFrame.add(btnOK);
		exitFrame.add(btnCancel);
		exitFrame.getContentPane().add(pnlexitHdr);
		exitFrame.repaint();

		MoveMouseListener mml2 = new MoveMouseListener(pnlexitHdr);
		pnlexitHdr.addMouseListener(mml2);
		pnlexitHdr.addMouseMotionListener(mml2);
	}
}
