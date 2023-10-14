package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicPlayer {
	JFrame frmPlayer;
	JPanel pnlHeader, pnlHeaderTrack, pnlBody, pnlBodyList;
	JLabel lblPlayer, lblLogo, lblAni;
	JButton btnClose, btnLogo, btnStop, btnMPrev, btnMPP, btnMNext, btnLoop;
	ImageIcon appIcon, iconClose, iconLogo, iconStop, iconPrev, iconPlay, iconPause, iconStatic, iconNext,
			iconLoopEnabled, iconLoopDisabled;
	Image imageClose, mainImage, imageLogo, imageStop, imagePrev, imagePlay, imagePause, imageLoopEnabled,
			imageLoopDisabled, imageStatic, imageNext;

	DefaultListModel<String> listModel;
	JList<String> list;
	JScrollPane scrollPane;

	long pauseLoc, songLength;
	int playStatus = 0, filepathresponse, trackNo = 0;
	// Play Status: {0: stop, 1: playing, 2: paused, 3: next/previous}
	public Player player;
	FileInputStream fis1;
	File[] selectedFiles;
	BufferedInputStream bis1;
	JFileChooser fcPath = new JFileChooser();
	String strPath = "", strPathNew;
	FileNameExtensionFilter filter;

	MoveMouseListener mml1, mml2, mml3, mml4;
	ExitFrame exitFrameObj;

	JLabel lblCurrentSong;
	JSlider seekBar;

	int width = 600, height = 410;

	Timer timer = new Timer(1000, null);
	int currentSeekPosition;
	boolean loopEnabled = true;

	public MusicPlayer() {

		frmPlayer = new JFrame();
		frmPlayer.setSize(width, height);
		frmPlayer.setLocationRelativeTo(null);
		frmPlayer.setUndecorated(true);
		frmPlayer.setLayout(null);
		frmPlayer.setOpacity(1);

		// To set app icon
		appIcon = new ImageIcon("src/assets/musicplay.png");
		mainImage = appIcon.getImage();
		frmPlayer.setIconImage(mainImage);

		// To create panel
		pnlHeader = new JPanel();
		pnlHeader.setBackground(Color.black);
		pnlHeader.setBounds(0, 0, width, 50);
		pnlHeader.setLayout(null);
		frmPlayer.add(pnlHeader);

		// To create scaled icon image
		iconLogo = new ImageIcon("src/assets/musicplay.png");
		imageLogo = iconLogo.getImage().getScaledInstance(39, 39, Image.SCALE_SMOOTH);
		iconLogo.setImage(imageLogo);

		// To create a button to display information (top left corner)
		btnLogo = new JButton(iconLogo); // setting button icon as app icon
		btnLogo.setBounds(5, 5, 40, 40);
		btnLogo.setFont(new Font("Times New Roman", Font.BOLD, 9));
		btnLogo.setFocusPainted(false);
		btnLogo.setBorderPainted(false);
		btnLogo.setContentAreaFilled(false);
		btnLogo.setBackground(Color.black);
		btnLogo.setToolTipText("More information");
		btnLogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Information objInformation = new Information();
				objInformation.displayInfoFrame();
			}
		});

		pnlHeader.add(btnLogo);

		lblPlayer = new JLabel("Muse.ic");
		lblPlayer.setBounds(50, 5, width - 50, 40);
		lblPlayer.setHorizontalAlignment(SwingConstants.LEFT);
		lblPlayer.setForeground(Color.ORANGE);
		lblPlayer.setFont(new Font("Times New Roman", Font.BOLD, 28));
		pnlHeader.add(lblPlayer);

		// Allow the player to be moved by dragging the header panel
		mml1 = new MoveMouseListener(pnlHeader);
		pnlHeader.addMouseListener(mml1);
		pnlHeader.addMouseMotionListener(mml1);

		// Create scaled icon image for close icon
		iconClose = new ImageIcon("src/assets/PNGClose.png");
		imageClose = iconClose.getImage().getScaledInstance(39, 39, Image.SCALE_SMOOTH);
		iconClose.setImage(imageClose);

		// Create button to close the player
		btnClose = new JButton(iconClose);
		btnClose.setBounds(width - 45, 5, 40, 40);
		btnClose.setFocusPainted(false);
		btnClose.setBorderPainted(false);
		btnClose.setContentAreaFilled(false);
		btnClose.setToolTipText("Close");
		btnClose.setBackground(Color.black);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitFrameObj = new ExitFrame();
				exitFrameObj.exitframe();
			}
		});
		pnlHeader.add(btnClose);

		pnlHeaderTrack = new JPanel();
		pnlHeaderTrack.setBackground(Color.black);
		pnlHeaderTrack.setBounds(0, 52, width, 30);
		pnlHeaderTrack.setLayout(null);
		frmPlayer.add(pnlHeaderTrack);

		// Allow player to be dragged from track display panel
		mml4 = new MoveMouseListener(pnlHeaderTrack);
		pnlHeaderTrack.addMouseListener(mml4);
		pnlHeaderTrack.addMouseMotionListener(mml4);

		frmPlayer.setFocusable(false);

		decoratePlayer();

	}// end of constructor

	public void init() {
		frmPlayer.setVisible(true);
	}// end of init()

	public void decoratePlayer() {
		pnlBody = new JPanel();
		pnlBody.setBackground(Color.black);
		pnlBody.setBounds(0, 84, width - 250, height - 30);
		pnlBody.setLayout(null);
		frmPlayer.add(pnlBody);

		mml2 = new MoveMouseListener(pnlBody);
		pnlBody.addMouseListener(mml2);
		pnlBody.addMouseMotionListener(mml2);

		// Add label to display current song
		lblCurrentSong = new JLabel("Hit the PLAY button", SwingConstants.CENTER);
		lblCurrentSong.setBounds(0, 5, width, 20);
		lblCurrentSong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblCurrentSong.setForeground(Color.WHITE);
		pnlHeaderTrack.add(lblCurrentSong);

		// Add seek bar component
		seekBar = new JSlider(JSlider.HORIZONTAL, 0, 300, 0);
		seekBar.setBounds(25, 240, width - 300, 15);
		seekBar.setPaintTicks(false);
		seekBar.setPaintLabels(false);
		seekBar.setSnapToTicks(true);
		seekBar.setOpaque(false);
		seekBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (playStatus == 3) {
					playStatus = 1;
					return;
				}
				if (playStatus == 0) {
					timer.stop();
					return;
				}
				if (!seekBar.getValueIsAdjusting()) {
					int position = (int) seekBar.getValue();
					float percentage = (float) (300 - position) / 300;
					pauseLoc = (long) (percentage * songLength);
					try {
						timer.stop();
						player.close();
						playStatus = 2;
						playTrack(strPathNew);
					} catch (Exception e3) {
						e3.printStackTrace();
					}
				}
			}
		});
		pnlBody.add(seekBar);
		
		// Add button to stop playing
		iconStop = new ImageIcon("src/assets/PNGStop.png");
		imageStop = iconStop.getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH);
		iconStop.setImage(imageStop);

		btnStop = new JButton(iconStop);
		btnStop.setBounds(65, 267, 30, 30);
		btnStop.setFocusPainted(false);
		btnStop.setBorderPainted(false);
		btnStop.setContentAreaFilled(false);
		btnStop.setToolTipText("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playStatus = 0;
				stopPlayer();
			}

		});
		pnlBody.add(btnStop);

		// Add button to go to previous track in queue
		iconPrev = new ImageIcon("src/assets/PNGPrevious.png");
		imagePrev = iconPrev.getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH);
		iconPrev.setImage(imagePrev);

		btnMPrev = new JButton(iconPrev);
		btnMPrev.setBounds(110, 267, 30, 30);
		btnMPrev.setFocusPainted(false);
		btnMPrev.setBorderPainted(false);
		btnMPrev.setContentAreaFilled(false);
		btnMPrev.setToolTipText("Previous");
		btnMPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prevTrack();
				seekBar.setValue(0);
			}

		});
		pnlBody.add(btnMPrev);

		// Create icon play track
		iconPlay = new ImageIcon("src/assets/PNGPlay.png");
		imagePlay = iconPlay.getImage().getScaledInstance(39, 39, Image.SCALE_SMOOTH);
		iconPlay.setImage(imagePlay);

		// Create icon to pause track
		iconPause = new ImageIcon("src/assets/PNGPause.png");
		imagePause = iconPause.getImage();
		imagePause = imagePause.getScaledInstance(39, 39, Image.SCALE_SMOOTH);
		iconPause.setImage(imagePause);

		// Add button to play/pause track
		btnMPP = new JButton(iconPlay);
		btnMPP.setBounds(155, 260, 42, 42);
		btnMPP.setFocusPainted(false);
		btnMPP.setBorderPainted(false);
		btnMPP.setContentAreaFilled(false);

		btnMPP.setToolTipText("Select MP3 files");
		btnMPP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playPauseTrack();
			}
		});
		pnlBody.add(btnMPP);

		// Add button to go to next track in queue
		iconNext = new ImageIcon("src/assets/PNGNext.png");
		imageNext = iconNext.getImage();
		imageNext = imageNext.getScaledInstance(27, 27, Image.SCALE_SMOOTH);
		iconNext.setImage(imageNext);

		btnMNext = new JButton(iconNext);
		btnMNext.setBounds(210, 267, 30, 30);
		btnMNext.setFocusPainted(false);
		btnMNext.setBorderPainted(false);
		btnMNext.setContentAreaFilled(false);
		btnMNext.setToolTipText("Next");
		btnMNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextTrack();
				seekBar.setValue(0);
			}
		});
		pnlBody.add(btnMNext);

		// Add button to control track looping
		iconLoopEnabled = new ImageIcon("src/assets/PNGLoopEnabled.png");
		imageLoopEnabled = iconLoopEnabled.getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH);
		iconLoopEnabled.setImage(imageLoopEnabled);

		iconLoopDisabled = new ImageIcon("src/assets/PNGLoopDisabled.png");
		imageLoopDisabled = iconLoopDisabled.getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH);
		iconLoopDisabled.setImage(imageLoopDisabled);

		btnLoop = new JButton(iconLoopEnabled);
		btnLoop.setBounds(255, 267, 30, 30);
		btnLoop.setFocusPainted(false);
		btnLoop.setBorderPainted(false);
		btnLoop.setContentAreaFilled(false);
		btnLoop.setToolTipText("Disable looping");
		btnLoop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLoopEnabled(!loopEnabled);
				if (loopEnabled) {
					btnLoop.setIcon(iconLoopEnabled);
					btnLoop.setToolTipText("Disable looping");
				} else {
					btnLoop.setIcon(iconLoopDisabled);
					btnLoop.setToolTipText("Enable looping");
				}
			}
		});
		pnlBody.add(btnLoop);
		

		iconStatic = new ImageIcon("src/assets/musicplay.png");
		imageStatic = iconStatic.getImage().getScaledInstance(330, 200, Image.SCALE_DEFAULT);
		iconStatic.setImage(imageStatic);

		lblAni = new JLabel(iconStatic);
		lblAni.setBounds(0, 0, 350, 260);
		pnlBody.add(lblAni);

		// Track List
		pnlBodyList = new JPanel();
		pnlBodyList.setBounds(353, 84, 247, height - 84);
		pnlBodyList.setBackground(Color.black);

		TitledBorder listTitle = new TitledBorder("Tracks");
		listTitle.setTitleColor(Color.ORANGE);
		listTitle.setTitleJustification(TitledBorder.CENTER);
		listTitle.setTitlePosition(TitledBorder.TOP);
		pnlBodyList.setBorder(listTitle);

		// Allow player to be moved by dragging track list panel
		mml3 = new MoveMouseListener(pnlBodyList);
		pnlBodyList.addMouseListener(mml3);
		pnlBodyList.addMouseMotionListener(mml3);

		frmPlayer.add(pnlBodyList);

		displayTrackList();

		filter = new FileNameExtensionFilter("MP3 File", "mp3");

	}// end of decoratePlayer()

	void displayTrackList() {
		listModel = new DefaultListModel<>();

		list = new JList<>(listModel);
		list.setSize(195, height - 55);
		list.setBackground(Color.black);

		ListSelectionListener listSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				if (!listSelectionEvent.getValueIsAdjusting()) { // This line prevents double events
					jumpTrack(list.getSelectedIndex());
					list.setSelectionBackground(new Color(255, 128, 0));
				}

			}
		};
		list.addListSelectionListener(listSelectionListener);

		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportView(list);
		scrollPane.setPreferredSize(new Dimension(240, height - 110));
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));

		scrollPane.getViewport().getView().setBackground(Color.BLACK);
		scrollPane.getViewport().getView().setForeground(Color.ORANGE);

		pnlBodyList.add(scrollPane);
	}
	
	public void stopPlayer() {
		try {
			player.close();
			strPath = "";
			lblCurrentSong.setText("Hit the PLAY button");
			btnMPP.setIcon(iconPlay);
			lblAni.setIcon(iconStatic);
			trackNo = 0;
			btnMPP.setToolTipText("Select MP3 files");
			listModel.clear();
		} catch (Exception e) {
		}
	}

	public void playTrack(String path) {
		try {
			fis1 = new FileInputStream(path);
			bis1 = new BufferedInputStream(fis1);
			player = new Player(bis1);
			songLength = fis1.available();
			if (playStatus == 2) {
				fis1.skip(songLength - pauseLoc);
			} else {
				currentSeekPosition = 0;
			}
			playStatus = 1;
			btnMPP.setIcon(iconPause);
			lblCurrentSong.setText(selectedFiles[trackNo].getName());
			strPathNew = path + "";
			btnMPP.setToolTipText("Pause");

		} catch (FileNotFoundException | JavaLayerException ex) {
			playStatus = 0;
			btnMPP.setIcon(iconPlay);
			lblAni.setIcon(iconLogo);
			lblCurrentSong.setText("Help");
			btnMPP.setToolTipText("Select MP3 files");

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		new Thread() {
			public void run() {
				try {
					player.play();
					if (player.isComplete()) {
						playStatus = 3;
						btnMNext.doClick();
					}
				} catch (JavaLayerException e) {
					strPath = "";
					playStatus = 0;
					lblCurrentSong.setText("");
					btnMPP.setIcon(iconPlay);
					lblAni.setIcon(iconLogo);
				}
			}
		}.start();
		ActionListener updateSeekBarPosition = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					if (playStatus != 1) {
						timer.stop();
						return;
					}

					currentSeekPosition = (int) ((float) (songLength - fis1.available()) / songLength * 300);
					seekBar.setValueIsAdjusting(true);
					seekBar.setValue(currentSeekPosition);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		timer.addActionListener(updateSeekBarPosition);
		if (timer.isRunning())
			timer.restart();
		else
			timer.start();
	}// end of playTrack()

	public void pauseTrack() {
		if (player != null) {
			try {
				pauseLoc = fis1.available(); // approximate number of bytes remaining
				player.close();
				playStatus = 2;
				btnMPP.setToolTipText("Resume");
			} catch (IOException e) {
			}
		}
	}// end of pauseTrack()

	public void resumeTrack() {
		playTrack(strPathNew);

	}// end of resumeTrack()

	public void prevTrack() {
		if (trackNo > 0)
			trackNo--;
		else if (loopEnabled)
			trackNo = selectedFiles.length - 1;

		if (trackNo == selectedFiles.length - 1 && selectedFiles.length - 1 == 0) {// if there is only one track
			jumpTrack(0);
		} else {
			try {
				list.setSelectedIndex(trackNo);
			} catch (Exception e) {
			}
		}
	}// end of prevTrack()

	public void playPauseTrack() {
		if (playStatus == 0) {
			fcPath.setFileFilter(filter);
			fcPath.setMultiSelectionEnabled(true);
			filepathresponse = fcPath.showOpenDialog(pnlBody);
			if (filepathresponse == JFileChooser.APPROVE_OPTION) {
				// user selects files
				selectedFiles = fcPath.getSelectedFiles();
				strPath = selectedFiles[0].getAbsolutePath();
				trackNo = 0;
				strPath = strPath.replace("\\", "\\\\");// replacing the escape slashes in file path with literal
														// slashes

				for (int i = 0; i < selectedFiles.length; i++) {
					listModel.addElement(selectedFiles[i].getName());
				}

				playStatus = 1;
				list.setSelectedIndex(0);

			}
		}

		else if (playStatus == 1) {
			btnMPP.setIcon(iconPlay);
			playStatus = 2;
			pauseTrack();
		}

		else {
			resumeTrack();
		}
	}// end of playPauseTrack()

	public void nextTrack() {
		if (trackNo < selectedFiles.length - 1) {
			trackNo++;
		} else if (loopEnabled)
			trackNo = 0;
		else {
			try {
				player.close();
				pauseLoc = songLength;
				playStatus = 2;
				seekBar.setValue(0);
				btnMPP.setIcon(iconPlay);
				btnMPP.setToolTipText("Resume");
				return;
			} catch (Exception exp) {

			}
		}

		if (selectedFiles.length == 1) {// if there's no change in trackNo
			jumpTrack(0);
		} else {
			try {
				list.setSelectedIndex(trackNo);
			} catch (Exception e) {
			}
		}
	}// end of endTrack()

	public void jumpTrack(int index) {
		try {
			seekBar.setValue(0);
			if (player != null)
				player.close();
			trackNo = index;
			strPath = selectedFiles[trackNo].getAbsolutePath();
			strPath = strPath.replace("\\", "\\\\");
		} catch (Exception e2) {
		}
		if (filepathresponse == JFileChooser.APPROVE_OPTION && playStatus != 0)
			playTrack(strPath);
	}// end of jumpTrack()

	public void setLoopEnabled(boolean value) {
		loopEnabled = value;
	}

}
