package Networking;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JButton;

import ObjectClasses.Player;
import View.MagicRealmGUI;

/**
 * The main controller for the client side of the game. Incorporates networking 
 * for the client, sends/receives messages with the server and manipulates the view.
 * Original code accessed from: http://cs.lmu.edu/~ray/notes/javanetexamples/#chat
 */
public class MagicRealmClient implements Runnable {
    ObjectInputStream in;
    ObjectOutputStream out;
    MagicRealmGUI gui;
    Player player;
    String name;
    String character;
    Socket socket;
    ArrayList<String> playableCharacters;
    
    public MagicRealmClient() {
    	gui = new MagicRealmGUI();
    	setActionListeners();
    }

    private void setActionListeners() {
    	// Note that actionListeners are set here, so that the controller
    	// can change the view behaviour based on the actions performed in 
    	// the view, without the view knowing about the model.
    	gui.startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					out.writeObject("STARTGAME");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        });
	}

	/**
     * Connects to the server then enters the processing loop.
     */
    public void run(){
        // Make connection and initialize streams
        String serverAddress = gui.getServerAddress();
        try {
        	// If left blank, serverAddress will simply be "localhost"
        	socket = new Socket(serverAddress, config.PORT);
        	out = new ObjectOutputStream(socket.getOutputStream());
        	out.flush();
        	in = new ObjectInputStream(socket.getInputStream());
        }
    	catch (SocketException e){
    		
    	}
        catch (IOException ioe){
        	System.out.println("Failed to initialize I/O streams with socket!");
        }
        
        // Process all messages from server, according to the protocol.
        while (true) {
        	String line = "";
        	try {
        		line = (String) in.readObject();
        	}
        	catch (IOException ioe){
        		System.out.println("The server has been shut down unexpectedly! The game is now over.");
        		break;
        	} catch (ClassNotFoundException e) {
        		System.out.println("A string command was not passed to the client!");
        		break;
			}
        	catch (NullPointerException e){
        		System.out.println("Can't connect to server - may be full or unreachable!");
        		break;
        	}
            if (line.startsWith("SUBMITNAME")) {
            	name = gui.getName();
            	try {
					out.writeObject(name);
	            	name = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
            } else if (line.startsWith("CHOOSECHARACTER:")){
            	String[] charStr = line.substring(16).split(",");
            	playableCharacters = new ArrayList<String>();
            	for (String character : charStr){
            		playableCharacters.add(character);
            	}
            	while (character == null || character.equals("")){
                	character = gui.openChooseCharacterDialog(playableCharacters);
            	}
            	try {
					out.writeObject(character);
				} catch (IOException e) {
					e.printStackTrace();
				}
            	player = new Player(name, character);
            } else if (line.startsWith("INVALIDNAME")){
            } else if (line.startsWith("NAMEACCEPTED")) {
            } else if (line.startsWith("GAMECANSTART")) {
            	gui.startGameButton.setEnabled(true);
            } else if (line.startsWith("GAMECAN'TSTART")) {
            	gui.startGameButton.setEnabled(false);
            } else if (line.startsWith("GAMESTART")) {
            	System.out.println("The game is now starting!");
            } else if (line.startsWith("ROUNDSTART")) {
            } else if (line.startsWith("MESSAGE")){
            } else if (line.startsWith("Score:")){
            } else if (line.startsWith("Players Active:")){
            } else if (line.startsWith("DENIED")){
            } else if (line.startsWith("LOCKCATEGORY:")){
            } else if (line.startsWith("GAMEOVER")){
            } else if (line.startsWith("WINNER:")){
            } else if (line.startsWith("REMOVE:")){
            	
            }
        }
    }

	/**
     * Runs the client as an application with a closeable frame.
     */
    public static void main(String[] args) throws Exception {
        MagicRealmClient client = new MagicRealmClient();
        client.run();
    }
}