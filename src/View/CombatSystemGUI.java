package View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import ObjectClasses.ActionChit;
import ObjectClasses.Character;
import ObjectClasses.Chit;

public class CombatSystemGUI{
	
	private JFrame window;
	private ImageLookup lookup;
	private JPanel universalPanel;
	private JPanel combatPanel;
	private JPanel optionPanel;
	private GridBagConstraints constraints;
	private ArrayList<JLabel> protagonistLabels;
	private ArrayList<JLabel> enemyLabels;
	public JButton fleeButton;
	public JButton fightButton;
	
	public CombatSystemGUI() {
		lookup = new ImageLookup();
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill   = GridBagConstraints.BOTH;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		buildWindow();
	}
	
	public static void main(String[] args) {
		CombatSystemGUI combatSystemGui = new CombatSystemGUI();
	}
	
	public void buildWindow() {
		window = new JFrame("Combat");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(640, 480);
		window.setVisible(true);
		window.setResizable(true);
		window.setLayout(new GridBagLayout());
		combatPanel = new JPanel();
		combatPanel.setSize(640, 360);
		combatPanel.setBackground(Color.WHITE);
		combatPanel.setOpaque(true);
		optionPanel = new JPanel();
		optionPanel.setSize(640, 120);
		combatPanel.setLayout(new GridBagLayout());
		optionPanel.setLayout(new GridBagLayout());
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.gridheight = 1;
		window.add(combatPanel, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 3;
		constraints.gridheight = 1;
		window.add(optionPanel, constraints);
	}

	public void addCharacters(ArrayList<Chit> side1, ArrayList<Chit> side2) {
		int characterCount = 0;
		for (Chit chit : side1){
			ImageIcon imageIcon = 
					new ImageIcon(getClass().getResource(lookup.getValue(chit.getName())));
			JLabel label = new JLabel(imageIcon);
			label.setOpaque(true);
			label.setName(chit.getName());
			constraints.gridx = characterCount;
			characterCount++;
			constraints.gridy = 1;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			combatPanel.add(label, constraints);
			combatPanel.repaint();
		}
		characterCount = 0;
		for (Chit chit : side2){
			ImageIcon imageIcon = 
					new ImageIcon(getClass().getResource(lookup.getValue(chit.getName())));
			JLabel label = new JLabel(imageIcon);
			label.setOpaque(true);
			label.setName(chit.getName());
			constraints.gridx = characterCount + 1;
			characterCount++;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			combatPanel.add(label, constraints);
			combatPanel.repaint();
		}
	}
	
	public void setupOptions(ArrayList<Chit> side2) {
		JLabel infoText = new JLabel();
		String enemies = "A hostile encounter: ";
		for (Chit enemy : side2){
			enemies += enemy.getName() + ", ";
		}
		enemies += " appeared!";
		infoText.setText(enemies);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.gridheight = 1;
		optionPanel.add(infoText, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		fightButton = new JButton("Fight!");
		optionPanel.add(fightButton, constraints);
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		fleeButton = new JButton("Flee!");
		optionPanel.add(fleeButton, constraints);
		window.pack();
	}

	public void flee() {
		JOptionPane.showMessageDialog(null, "You successfully managed to flee!");
	}

	public void close() {
		window.dispose();
	}

	public int getTarget(ArrayList<Chit> enemies) {
		ArrayList<String> temp = new ArrayList<String>();
		for (Chit chit : enemies){
			temp.add(chit.getName());
		}
		Object[] enemyList = temp.toArray();
		
		Object selectedEnemy = JOptionPane.showInputDialog(window, 
		        "Select the enemy to attack:",
		        "Target",
		        JOptionPane.QUESTION_MESSAGE, 
		        null, 
		        enemyList, 
		        enemyList[0]);
		
		for(int i = 0; i < enemyList.length; i++){
			if (enemyList[i].equals(selectedEnemy)){
				return i;
			}
		}
		return 0;
	}

	public Object getEncounterAction() {
		Object[] options = {"Play a Fight Chit", "Activate/Deactivate Belongings", "Abandon belongings"};
		return JOptionPane.showInputDialog(window, 
		        "Perform an action:",
		        "Action Turn",
		        JOptionPane.QUESTION_MESSAGE, 
		        null, 
		        options, 
		        options[0]);
	}

	public void activateDeactivateItems(Character playerCharacter) {
		// TODO Auto-generated method stub
		
	}

	public void abandonItems(Character playerCharacter) {
		// TODO Auto-generated method stub
		
	}

	public Chit getFightChit(Character playerCharacter, ArrayList<Chit> enemies) {
		int 
		ArrayList<String> fightChits = new ArrayList<String>();
		for (ActionChit chit : playerCharacter.activeActionChits){
			
		}
		
		return null;
	}

	public String[] getDirections(Character playerCharacter) {
		String[] directions = new String[3];
		Object[] options = {"Thrust Ahead", "Swing to Side", "Smash Down"};
		directions[0] =  (String) JOptionPane.showInputDialog(window, 
		        "Select an attack direction:",
		        "Weapon Direction",
		        JOptionPane.QUESTION_MESSAGE, 
		        null, 
		        options, 
		        options[0]);
		options = new Object[]{"Charge Ahead", "Dodge To Side", "Duck Down"};
		directions[1] =  (String) JOptionPane.showInputDialog(window, 
		        "Select a maneuver direction:",
		        "Maneuver Direction",
		        JOptionPane.QUESTION_MESSAGE, 
		        null, 
		        options, 
		        options[0]);
		for (Chit item : playerCharacter.getInventory()){
			if (item.getName().contains("Shield")){
				options = new Object[]{"Thrust", "Swing", "Smash"};
				directions[2] =  (String) JOptionPane.showInputDialog(window, 
				        "Select a shield direction (Protect against):",
				        "Shield Direction",
				        JOptionPane.QUESTION_MESSAGE, 
				        null, 
				        options, 
				        options[0]);
			}
		}
		return directions;
	}
}
