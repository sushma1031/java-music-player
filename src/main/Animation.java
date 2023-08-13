package main;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Animation 
{
	JFrame frmAnimate;
	ImageIcon animateIcon, introIcon;
	JLabel introLabel;
	
	int width = 350;
	int height = 250;
	
	public void animate()
	{
		
		frmAnimate = new JFrame();
		frmAnimate.setSize(width, height);
		frmAnimate.setUndecorated(true);
		frmAnimate.setLayout(null);
		frmAnimate.setLocationRelativeTo(null);
		
		animateIcon  = new ImageIcon("src/assets/musicplay.png");
		Image animateImage = animateIcon.getImage();
		animateIcon.setImage(animateImage);
		frmAnimate.setIconImage(animateImage);
		
		introIcon = new ImageIcon("src/assets/musicplay.png");
		Image imgAnimateIntro = introIcon.getImage();
		imgAnimateIntro = imgAnimateIntro.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		introIcon.setImage(imgAnimateIntro);
		
		introLabel = new JLabel(introIcon);
		introLabel.setBounds(0, 0, width, height);
		introLabel.setLayout(null);
		frmAnimate.getContentPane().add(introLabel);
		frmAnimate.setBackground(Color.black);
		
		frmAnimate.setVisible(true);
		
		new Thread()
		{
			public void run()
			{
				try
				{
					MusicPlayer obj = new MusicPlayer();
			
					sleep(1500);
					obj.init();
					frmAnimate.dispose();
				}
				catch (Exception e) {}
			}
		}.start();
	}//function animate() closed here
}
