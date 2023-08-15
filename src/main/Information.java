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

public class Information 
{
	JFrame infoFrame;
	Image museLogo, imageLogo, imageClose;
	ImageIcon museIcon, iconLogo, iconClose;
	JButton btnLogo, btnClose;
	
	MoveMouseListener mmlInfoObj;
	
	public void displayInfoFrame() //Info
	{
		infoFrame=new JFrame("Info");
		infoFrame.setSize(500,300);
		infoFrame.setUndecorated(true);
		infoFrame.setOpacity(0.9f);
		infoFrame.setLocationRelativeTo(null);
		infoFrame.setLayout(null);
		infoFrame.getContentPane().setBackground(new Color(211, 238, 241));
		
		//To set app icon
		museIcon  = new ImageIcon("src/assets/musicplay.png");
		museLogo = museIcon.getImage();
		infoFrame.setIconImage(museLogo);
		
		JPanel pnlInfoHdr=new JPanel();
		pnlInfoHdr.setBackground(Color.black);
		pnlInfoHdr.setLayout(null);
		pnlInfoHdr.setBounds(0, 0, 700,60);
		infoFrame.getContentPane().add(pnlInfoHdr);
		
		JLabel lblInfo=new JLabel("About Muse.ic",SwingConstants.CENTER);
		lblInfo.setBounds(10,5,450,40);
		lblInfo.setForeground(Color.WHITE);
		lblInfo.setFont(new Font("Times New Roman",Font.BOLD,24));
		pnlInfoHdr.add(lblInfo);
		
		//To set scaled image for icon logo
		iconLogo = new ImageIcon("src/assets/musicplay.png");
		imageLogo = iconLogo.getImage().getScaledInstance(39,39, Image.SCALE_SMOOTH);
		iconLogo.setImage(imageLogo);
		
		//To create an icon as a button (with no action)
		btnLogo = new JButton(iconLogo);
		btnLogo.setBounds(5, 5, 40, 40);
		btnLogo.setFocusPainted(false);
		btnLogo.setBorderPainted(false);
		btnLogo.setContentAreaFilled(false);
		btnLogo.setBackground(Color.black);
		btnLogo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {}          
		});
		
		pnlInfoHdr.add(btnLogo); //Add the button icon to the panel
		
		//Create a scaled version of the close icon for the button
		iconClose  = new ImageIcon("src/assets/PNGClose.png");
		imageClose = iconClose.getImage().getScaledInstance(39,39, Image.SCALE_SMOOTH);
		iconClose.setImage(imageClose);
		
		//Create a button to close the window
		btnClose = new JButton(iconClose);
		btnClose.setBounds(450, 10, 40, 40);
		btnClose.setFocusPainted(false);
		btnClose.setBorderPainted(false);
		btnClose.setContentAreaFilled(false);
		btnClose.setToolTipText("Close");
		btnClose.setBackground(Color.black);
		btnClose.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
		    {
					infoFrame.dispose();
		    }          
		});
		pnlInfoHdr.add(btnClose); //Add button to panel
		
		
		//To display information about the music player		
		JLabel lblInfo1=new JLabel();
		lblInfo1.setBounds(100,0,450,300);
		String labelText = "<html><div style='text-align: center; line-height:1.75em;'>"
                + "Muse.ic is a simple music player application,<br>"
                + "designed by Sourabh Bhat and Sushma Jayaram."
                + "</div></html>";
		lblInfo1.setText(labelText);
		lblInfo1.setFont(new Font("Times New Roman",Font.BOLD,14));
		infoFrame.getContentPane().add(lblInfo1);
		
		//Allow dragging of the information panel
		mmlInfoObj = new MoveMouseListener(pnlInfoHdr);
	    pnlInfoHdr.addMouseListener(mmlInfoObj);
	    pnlInfoHdr.addMouseMotionListener(mmlInfoObj);
	    
	    infoFrame.setVisible(true);
		
	}//info frame method closed
}
