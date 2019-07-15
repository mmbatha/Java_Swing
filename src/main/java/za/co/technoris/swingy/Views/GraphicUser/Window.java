/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:42:49 
 * @Last Modified by: mmbatha
 * @Last Modified time: 2019-07-04 11:15:19
 */
package za.co.technoris.swingy.Views.GraphicUser;

import static za.co.technoris.swingy.Helpers.GlobalHelper.artifact;
import static za.co.technoris.swingy.Helpers.GlobalHelper.encounterPhase;
import static za.co.technoris.swingy.Helpers.GlobalHelper.fightPhase;
import static za.co.technoris.swingy.Helpers.GlobalHelper.hero;
import static za.co.technoris.swingy.Helpers.GlobalHelper.heroNumber;
import static za.co.technoris.swingy.Helpers.GlobalHelper.isHero;
import static za.co.technoris.swingy.Helpers.GlobalHelper.jtaLog;
import static za.co.technoris.swingy.Helpers.GlobalHelper.lootOption;
import static za.co.technoris.swingy.Helpers.GlobalHelper.map;
import static za.co.technoris.swingy.Helpers.GlobalHelper.WELCOME_MSG;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import za.co.technoris.swingy.Controllers.CharacterFactory;
import za.co.technoris.swingy.Controllers.GameManager;
import za.co.technoris.swingy.Controllers.MapFactory;
import za.co.technoris.swingy.Database.DatabaseHandler;
import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import za.co.technoris.swingy.Helpers.HeroTypes;
import za.co.technoris.swingy.Helpers.ImageHelper;
import za.co.technoris.swingy.Helpers.LoggerHelper;
import za.co.technoris.swingy.Helpers.PrintHelper;
import za.co.technoris.swingy.Models.Characters.Character;
import za.co.technoris.swingy.Models.Characters.Hero;
import za.co.technoris.swingy.Views.CommandLine.CLI;

public class Window extends JFrame {

	private static final String IMAGE_SPRITE_ERROR = "Error: Image sprite not loaded!";
	private static final long serialVersionUID = 1L;

	private JComboBox<String> jcbCreate = new JComboBox<String>();
	private JComboBox<String> jcbSelect = new JComboBox<String>();
	private JLabel jlblCreate = new JLabel("Create your hero");
	private JLabel jlblSelect = new JLabel("Select your hero");
	private JLabel jlblAction = new JLabel("Action");
	private JLabel jlblTake = new JLabel("Take Artifact?");
	private JLabel jlblLog = new JLabel("Log");
	private JLabel jlblStats = new JLabel("Stats");
	private JLabel jlblPic;
	private JLabel jlblName = new JLabel("Name");
	private JTextField jtfInput = new JTextField();

	private JPanel jpContainer = new JPanel();
	private JPanel jpMenu = new JPanel();
	private JPanel jpMap = new JPanel();
	private JPanel jpLog = new JPanel();
	private JPanel jpCreateHero = new JPanel();
	private JPanel jpSelectHero = new JPanel();
	private JPanel jpGrid = new JPanel();
	private JPanel jpStats = new JPanel();
	private JPanel jpInput = new JPanel();

	private JButton jbtnCreate = new JButton("Create a new hero");
	private JButton jbtnSelect = new JButton("Select a hero");
	private JButton jbtnValidateCreation = new JButton("Create");
	private JButton jbtnDeleteHero = new JButton("Delete");
	private JButton jbtnRunGame = new JButton("Begin");
	private JButton jbtnCancelMain = new JButton("Cancel");
	private JButton jbtnCancel = new JButton("Cancel");
	private JScrollPane jScrollPane;

	private BufferedImage imgFarmer;
	private BufferedImage imgNerd;
	private BufferedImage imgVillain;
	private BufferedImage imgWeapon;
	private BufferedImage imgWolf;
	private BufferedImage imgZombie;
	private BufferedImage imgArmour;
	private BufferedImage imgHealth;
	private BufferedImage imgFight;
	private BufferedImage imgFoe;
	private Image scaledImg;

	private GridLayout gridLayout = new GridLayout();

	private static final String ASSETS_DIR = "src/main/java/za/co/technoris/swingy/Assets/";

	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuBtn = new JMenu("Switch Views");
	private JMenuItem switchMenuItem = new JMenuItem("Switch to Console...");

	Window() {
		setTitle("Swingy RPG");
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		jpContainer.setLayout(new BorderLayout());
		initComponents(this);
		LoggerHelper.print(WELCOME_MSG);
		startScreen(this);
		this.setVisible(true);
	}

	private void startScreen(Window window) {
		jpMenu.add(jbtnCreate);
		jpMenu.add(jbtnSelect);
		jpMenu.add(jpStats);
		jpMenu.add(jpCreateHero);
		jpMenu.add(jpSelectHero);
		jpLog.add(jlblLog);
		jpLog.add(jScrollPane);
		jpMap.add(jlblPic);
		jpMap.add(jpGrid);
		jpMenu.add(jbtnCancelMain);
		menuBar.add(menuBtn);
		menuBtn.add(switchMenuItem);

		jpContainer.add(menuBar, BorderLayout.PAGE_START);
		jpContainer.add(jpMenu, BorderLayout.LINE_START);
		jpContainer.add(jpMap, BorderLayout.CENTER);
		jpContainer.add(jpLog, BorderLayout.LINE_END);

		window.setContentPane(jpContainer);
	}

	private void initComponents(Window window) {
		jtaLog = new JTextArea("", 40, 25);
		jScrollPane = new JScrollPane(jtaLog);
		jtaLog.setEditable(false);
		jtaLog.setLineWrap(true);
		jtaLog.setWrapStyleWord(true);

		((FlowLayout) jpMap.getLayout()).setVgap(0);
		BufferedImage bgImage = ImageHelper.loadImage(ASSETS_DIR + "img.png");
		imgZombie = ImageHelper.loadImage(ASSETS_DIR + "zombie.png");
		imgVillain = ImageHelper.loadImage(ASSETS_DIR + "villain.png");
		imgFarmer = ImageHelper.loadImage(ASSETS_DIR + "farmer.png");
		imgNerd = ImageHelper.loadImage(ASSETS_DIR + "nerd.png");
		imgWeapon = ImageHelper.loadImage(ASSETS_DIR + "weapon.png");
		imgWolf = ImageHelper.loadImage(ASSETS_DIR + "wolf.png");
		imgArmour = ImageHelper.loadImage(ASSETS_DIR + "armour.png");
		imgHealth = ImageHelper.loadImage(ASSETS_DIR + "health.png");
		imgFight = ImageHelper.loadImage(ASSETS_DIR + "weapon2.png");
		imgFoe = ImageHelper.loadImage(ASSETS_DIR + "foe.png");
		scaledImg = bgImage;
		jlblPic = new JLabel(new ImageIcon(scaledImg));

		jpMenu.setPreferredSize(new Dimension(window.getWidth() / 4, window.getHeight()));
		jpMenu.setBackground(new Color(250, 250, 250));
		jpMap.setPreferredSize(new Dimension(window.getWidth() / 2, window.getHeight()));
		jpMap.setBackground(new Color(250, 250, 250));
		jpLog.setPreferredSize(new Dimension(window.getWidth() / 4, window.getHeight()));
		jpLog.setBackground(new Color(250, 250, 250));
		jpGrid.setPreferredSize(new Dimension(window.getWidth() / 2, window.getHeight()));
		jpGrid.setBackground(new Color(250, 250, 250));

		jcbCreate.addItem("Villain");
		jcbCreate.addItem("Farmer");
		jcbCreate.addItem("Nerd");
		jcbCreate.addActionListener(new ComboCreateAction());
		jcbCreate.setPreferredSize(new Dimension(200, 50));
		jcbSelect.setPreferredSize(new Dimension(200, 50));

		jbtnCreate.setPreferredSize(new Dimension(200, 50));
		jbtnCreate.addActionListener(new ButtonCreateListener());
		jbtnSelect.setPreferredSize(new Dimension(200, 50));
		jbtnSelect.addActionListener(new ButtonSelectListener());
		switchMenuItem.addActionListener(new ButtonHeroSwitchListener());
		jbtnValidateCreation.setPreferredSize(new Dimension(200, 40));
		jbtnValidateCreation.addActionListener(new ButtonHeroCreateListener());
		jbtnValidateCreation.setAlignmentX(Component.CENTER_ALIGNMENT);
		jbtnValidateCreation.setMaximumSize(getSize());
		jbtnRunGame.setPreferredSize(new Dimension(200, 40));
		jbtnRunGame.addActionListener(new ButtonRunListener());
		jbtnRunGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		jbtnRunGame.setMaximumSize(getSize());
		jbtnDeleteHero.setPreferredSize(new Dimension(200, 40));
		jbtnDeleteHero.addActionListener(new ButtonDeleteHeroListener());
		jbtnDeleteHero.setAlignmentX(Component.CENTER_ALIGNMENT);
		jbtnDeleteHero.setMaximumSize(getSize());
		jbtnDeleteHero.setForeground(new Color(255, 99, 71));
		jbtnCancel.setPreferredSize(new Dimension(200, 40));
		jbtnCancel.addActionListener(new ButtonCancelListener());
		jbtnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		jbtnCancel.setMaximumSize(getSize());
		jbtnCancelMain.setPreferredSize(new Dimension(200, 40));
		jbtnCancelMain.addActionListener(new ButtonCancelMainListener());
		jbtnCancelMain.setAlignmentX(Component.CENTER_ALIGNMENT);
		jbtnCancelMain.setMaximumSize(getSize());
		jlblCreate.setAlignmentX(Component.CENTER_ALIGNMENT);
		jlblSelect.setAlignmentX(Component.CENTER_ALIGNMENT);
		jlblAction.setAlignmentX(Component.CENTER_ALIGNMENT);
		jlblTake.setAlignmentX(Component.CENTER_ALIGNMENT);
		jtfInput.setPreferredSize(new Dimension(150, 40));

		Box verticalBox = Box.createVerticalBox();
		jpStats.setPreferredSize(new Dimension(300, 175));
		jlblStats.setAlignmentY(Component.CENTER_ALIGNMENT);
		verticalBox.add(jlblStats);
		jpStats.add(verticalBox);
		jpStats.setVisible(false);
		jpInput.add(jlblName);
		jpInput.add(jtfInput);

		jpCreateHero.setPreferredSize(new Dimension(300, 200));
		jpCreateHero.setLayout(new BoxLayout(jpCreateHero, BoxLayout.Y_AXIS));
		jpCreateHero.add(jlblCreate);
		jpCreateHero.add(jcbCreate);
		jpCreateHero.add(jpInput);
		jpCreateHero.add(jbtnValidateCreation);
		jpCreateHero.setVisible(false);
		jcbCreate.setAlignmentX(Component.CENTER_ALIGNMENT);

		jpSelectHero.setPreferredSize(new Dimension(300, 200));
		jpSelectHero.setLayout(new BoxLayout(jpSelectHero, BoxLayout.Y_AXIS));
		jpSelectHero.add(jlblSelect);
		jpSelectHero.add(jcbSelect);
		jpSelectHero.add(jbtnRunGame);
		jpSelectHero.add(jbtnDeleteHero);
		jpSelectHero.setVisible(false);
		jcbSelect.setAlignmentX(Component.CENTER_ALIGNMENT);

		jpGrid.setLayout(gridLayout);
		jpGrid.setVisible(false);

		jbtnCancelMain.setVisible(false);
	}

	private void mapHandler() {
		for (int i = 0; i < map.getMapSize(); i++) {
			for (int j = 0; j < map.getMapSize(); j++) {
				final int x = i;
				final int y = j;
				int mapValue = map.getMap()[i][j];
				final JPanel jpCell = new JPanel();
				((FlowLayout) jpCell.getLayout()).setVgap(0);
				((FlowLayout) jpCell.getLayout()).setHgap(0);
				jpCell.setBorder(BorderFactory.createLineBorder(new Color(211, 211, 211)));
				switch (mapValue) {
				case 1:
					jpCell.setBackground(new Color(0, 191, 255));
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							switch (hero.getType()) {
							case "Villain":
								scaledImg = imgVillain.getScaledInstance(jpCell.getWidth(), jpCell.getHeight(),
										Image.SCALE_DEFAULT);
								break;
							case "Farmer":
								scaledImg = imgFarmer.getScaledInstance(jpCell.getWidth(), jpCell.getHeight(),
										Image.SCALE_DEFAULT);
								break;
							case "Nerd":
								scaledImg = imgNerd.getScaledInstance(jpCell.getWidth(), jpCell.getHeight(),
										Image.SCALE_DEFAULT);
								break;
							}
							if (scaledImg != null) {
								JLabel jlblPic = new JLabel(new ImageIcon(scaledImg));
								jpCell.add(jlblPic);
								pack();
							} else {
								LoggerHelper.print(IMAGE_SPRITE_ERROR);
							}
						}
					});
					break;
				case 2:
					jpCell.setBackground(new Color(50, 49, 50));
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							scaledImg = imgWolf.getScaledInstance(jpCell.getWidth(), jpCell.getHeight(),
									Image.SCALE_DEFAULT);
							if (scaledImg != null) {
								JLabel jlblPic = new JLabel(new ImageIcon(scaledImg));
								jpCell.add(jlblPic);
								pack();
							} else {
								LoggerHelper.print(IMAGE_SPRITE_ERROR);
							}
						}
					});
					break;
				case 3:
					jpCell.setBackground(new Color(140, 90, 24));
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							scaledImg = imgZombie.getScaledInstance(jpCell.getWidth(), jpCell.getHeight(),
									Image.SCALE_DEFAULT);
							if (scaledImg != null) {
								JLabel jlblPic = new JLabel(new ImageIcon(scaledImg));
								jpCell.add(jlblPic);
								pack();
							} else {
								LoggerHelper.print(IMAGE_SPRITE_ERROR);
							}
						}
					});
					break;
				case 8:
					jpCell.setBackground(new Color(255, 153, 102));
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							// TODO - Set image width and height to ?50?
							scaledImg = imgWeapon.getScaledInstance(jpCell.getWidth(), jpCell.getHeight(),
									Image.SCALE_DEFAULT);
							if (scaledImg != null) {
								JLabel jlblPic = new JLabel(new ImageIcon(scaledImg));
								jpCell.add(jlblPic);
								pack();
							} else {
								LoggerHelper.print(IMAGE_SPRITE_ERROR);
							}
						}
					});
					break;
				default:
					jpCell.setBackground(new Color(255, 218, 185));
					break;
				}
				jpGrid.add(jpCell);
				jpCell.addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent e) {
						if (x + 1 < map.getMapSize() && map.getMap()[x + 1][y] == 1) {
							GameManager.move(8);
						} else if (y - 1 >= 0 && map.getMap()[x][y - 1] == 1) {
							GameManager.move(6);
						} else if (x - 1 >= 0 && map.getMap()[x - 1][y] == 1) {
							GameManager.move(2);
						} else if (y + 1 < map.getMapSize() && map.getMap()[x][y + 1] == 1) {
							GameManager.move(4);
						}
						if (encounterPhase) {
							encounterFoe();
							encounterPhase = false;
						} else {
							GameManager.winCondition();
						}
						jpGrid.removeAll();
						gridLayout.setRows(map.getMapSize());
						gridLayout.setColumns(map.getMapSize());
						gridLayout.setHgap(-1);
						gridLayout.setVgap(-1);
						jpGrid.setLayout(gridLayout);
						mapHandler();
						jpGrid.revalidate();
						jpGrid.repaint();
						pack();
					}

					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseReleased(MouseEvent e) {
					}

					@Override
					public void mouseEntered(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
					}
				});
			}
		}
	}

	private class ButtonCreateListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			jpSelectHero.remove(jbtnCancel);
			jpCreateHero.add(jbtnCancel);
			jbtnCreate.setVisible(false);
			jbtnSelect.setVisible(false);
			switchMenuItem.setVisible(false);
			jpCreateHero.setVisible(true);

			PrintHelper.printHeroDetail(1);
		}
	}

	private class ButtonSelectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DatabaseHandler.getInstance().printHeroesFromDB();
			if (!isHero) {
				jtaLog.setText(">> No such hero!\n");
			} else {
				jpCreateHero.remove(jbtnCancel);
				jpSelectHero.add(jbtnCancel);
				jbtnCreate.setVisible(false);
				jbtnSelect.setVisible(false);
				switchMenuItem.setVisible(false);
				jcbSelect.removeAllItems();
				List<Hero> heroes = DatabaseHandler.getInstance().getFromDB();
				for (Hero hero : heroes) {
					jcbSelect.addItem(hero.getName());
				}
				jpSelectHero.setVisible(true);
				isHero = false;
			}
		}
	}

	private class ButtonHeroCreateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int selectedIndex = jcbCreate.getSelectedIndex();
			Character selectedCharacter = null;
			switch (selectedIndex) {
			case 0:
				selectedCharacter = CharacterFactory.newHero(jtfInput.getText().trim(), HeroTypes.VILLAIN);
				break;
			case 1:
				selectedCharacter = CharacterFactory.newHero(jtfInput.getText().trim(), HeroTypes.FARMER);
				break;
			case 2:
				selectedCharacter = CharacterFactory.newHero(jtfInput.getText().trim(), HeroTypes.NERD);
				break;
			}
			if (selectedCharacter != null) {
				DatabaseHandler.getInstance().insertHero((Hero) selectedCharacter);
				jpCreateHero.setVisible(false);
				jbtnCreate.setVisible(true);
				jbtnSelect.setVisible(true);
				switchMenuItem.setVisible(true);
			}
		}
	}

	private class ButtonHeroSwitchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Window.this.dispose();
			CLI.run();
		}
	}

	public void encounterFoe() {
		String encounterOptions[] = { "Run", "Fight" };
		ImageIcon foeImage = new ImageIcon(imgFoe);
		// TODO - Add proper message
		int encounterResponse = JOptionPane.showOptionDialog(Window.this, "What do you do?", "Encounter",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, foeImage, encounterOptions, encounterOptions[1]);
		jbtnCancelMain.setVisible(false);
		if (encounterResponse == 1) {
			GameManager.fight(false);
		} else if (encounterResponse == 0) {
			GameManager.run();
		}
		GameManager.winCondition();
		jpGrid.removeAll();
		gridLayout.setRows(map.getMapSize());
		gridLayout.setColumns(map.getMapSize());
		gridLayout.setHgap(-1);
		gridLayout.setVgap(-1);
		jpGrid.setLayout(gridLayout);
		mapHandler();
		jpGrid.revalidate();
		jpGrid.repaint();
		if (lootOption) {
			pickupLoot();
			lootOption = false;
		} else {
			jbtnCancelMain.setVisible(true);
			jlblStats.setText("<html>Name: " + hero.getName() + "<br>" + "Type: " + hero.getType() + "<br>" + "Level: "
					+ hero.getLevel() + "<br>" + "Experience: " + hero.getXP() + "<br>" + "Attack: " + hero.getAttack()
					+ "<br>" + "Defense: " + hero.getDefense() + "<br>" + "Health: " + hero.getHP() + "<br>"
					+ "Weapon: " + hero.getWeapon().getName() + "<br>" + "Armor: " + hero.getArmor().getName() + "<br>"
					+ "Helm: " + hero.getHelm().getName() + "</html>");
		}
	}

	public void pickupLoot() {
		String lootOptions[] = {"No", "Yes"};
		ArtifactsHelper artifactType = artifact.getType();
		ImageIcon artifactImg = null;
		switch (artifactType) {
			case WEAPON:
				artifactImg = new ImageIcon(imgFight);
				break;
			case HELM:
				artifactImg = new ImageIcon(imgHealth);
				break;
			case ARMOR:
				artifactImg = new ImageIcon(imgArmour);
				break;
			default:
				artifactImg = new ImageIcon(imgArmour);
				break;
		}
		int takeLoot = JOptionPane.showOptionDialog(Window.this, "Take Artifact?", "Artifact",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, artifactImg, lootOptions, lootOptions[1]);
		if (takeLoot == 1) {
			hero.suitUp(artifact, artifact.getType());
			LoggerHelper.print(artifact.getName() + " taken");
		}
		jlblStats.setText("<html>Name: " + hero.getName() + "<br>" + "Type: " + hero.getType() + "<br>" + "Level: "
				+ hero.getLevel() + "<br>" + "Experience: " + hero.getXP() + "<br>" + "Attack: " + hero.getAttack()
				+ "<br>" + "Defense: " + hero.getDefense() + "<br>" + "Health: " + hero.getHP() + "<br>" + "Weapon: "
				+ hero.getWeapon().getName() + "<br>" + "Armor: " + hero.getArmor().getName() + "<br>" + "Helm: "
				+ hero.getHelm().getName() + "</html>");
		jbtnCancelMain.setVisible(true);
		fightPhase = false;
	}

	private class ButtonRunListener implements ActionListener {
		private static final String LANDING = " landed in quite a pickle!";

		public void actionPerformed(ActionEvent e) {
			jpSelectHero.setVisible(false);
			jlblPic.setVisible(false);
			hero = DatabaseHandler.getInstance().getHeroData(jcbSelect.getSelectedItem().toString());
			map = MapFactory.generateMap(hero);
			LoggerHelper.print(hero.getName() + LANDING);
			gridLayout.setRows(map.getMapSize());
			gridLayout.setColumns(map.getMapSize());
			gridLayout.setHgap(-1);
			gridLayout.setVgap(-1);
			jpGrid.setVisible(true);
			jlblPic.setVisible(false);
			jlblStats.setText("<html>Name: " + hero.getName() + "<br>" + "Type: " + hero.getType() + "<br>" + "Level: "
					+ hero.getLevel() + "<br>" + "Experience: " + hero.getXP() + "<br>" + "Attack: " + hero.getAttack()
					+ "<br>" + "Defense: " + hero.getDefense() + "<br>" + "Health: " + hero.getHP() + "<br>"
					+ "Weapon: " + hero.getWeapon().getName() + "<br>" + "Armor: " + hero.getArmor().getName() + "<br>"
					+ "Helm: " + hero.getHelm().getName() + "</html>");
			jpStats.setVisible(true);
			jbtnCancelMain.setVisible(true);
			mapHandler();
		}
	}

	private class ButtonDeleteHeroListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int dialogYesNo = JOptionPane.YES_NO_OPTION;
			int dialogAnswer = JOptionPane.showConfirmDialog(Window.this, "Confirm deletion?", "Delete", dialogYesNo);
			if (dialogAnswer == JOptionPane.YES_OPTION) {
				DatabaseHandler.getInstance().deleteHero(jcbSelect.getSelectedItem().toString());
				DatabaseHandler.getInstance().printHeroesFromDB();
				if (heroNumber == 0) {
					jpSelectHero.setVisible(false);
					jbtnCreate.setVisible(true);
					jbtnSelect.setVisible(true);
					switchMenuItem.setVisible(true);
					isHero = false;
				} else {
					jcbSelect.removeAllItems();
					List<Hero> heroes = DatabaseHandler.getInstance().getFromDB();
					for (Hero hero : heroes) {
						jcbSelect.addItem(hero.getName());
					}
				}
			}
		}
	}

	private class ButtonCancelMainListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			jpStats.setVisible(false);
			jpGrid.removeAll();
			jpGrid.setVisible(false);
			jlblPic.setVisible(true);
			jbtnCancelMain.setVisible(false);
			jbtnCreate.setVisible(true);
			jbtnSelect.setVisible(true);
			switchMenuItem.setVisible(true);
		}
	}

	private class ButtonCancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			jpCreateHero.setVisible(false);
			jpSelectHero.setVisible(false);
			jbtnCreate.setVisible(true);
			jbtnSelect.setVisible(true);
			switchMenuItem.setVisible(true);
		}
	}

	private class ComboCreateAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int selectedIndex = jcbCreate.getSelectedIndex();
			PrintHelper.printHeroDetail(selectedIndex + 1);
		}
	}
}
