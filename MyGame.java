package com.mygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener {  // Listener Implementation
    JLabel heading,clockLabel;
    Font font = new Font("",Font.BOLD,40); //Family ,Style,size font==BOLD
    JPanel mainPanel;
    JButton[]btns = new JButton[9]; // array to create 9 buttons for game

    // game instance variable
    int []gameChances = {2,2,2,2,2,2,2,2,2};
    int activePlayer=0;
    int wps[][] = {  //wining 2D array possible wayes
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6},
    };
    int winner=2;
    boolean gameOver = false;

    MyGame(){
        System.out.println("Creating instance of game");
        setTitle("My Tic Tac Toe Game...");
        setSize(850,850);
        ImageIcon icon = new ImageIcon("src/img/icon.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }
    private  void createGUI(){

        this.getContentPane().setBackground(Color.BLUE);
        this.setLayout(new BorderLayout());

        // north heading /....for heading ,I will declare a variable (Upper)
        heading = new JLabel("Tic Tac Toe");
        //heading.setIcon(new ImageIcon("src/img/icon.png"));
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER); // Heading into Center position
        heading.setForeground(Color.white);
        this.add(heading,BorderLayout.NORTH); // heading North section

        // Creating Object for clock JLabel for clock..
        clockLabel = new JLabel("Clock");
        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setForeground(Color.white);
        this.add(clockLabel,BorderLayout.SOUTH);

        Thread t = new Thread(){
            public void run(){
                try{
                    while (true){
                        String datetime = new Date().toLocaleString();

                        clockLabel.setText(datetime);

                        Thread.sleep(1000); // Milli second to second
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        t.start();

        //// Panel Section
        mainPanel = new JPanel(); // to create Panel object

        mainPanel.setLayout(new GridLayout(3,3)); // To set Grid Layout for Panel
        for(int i=1; i<=9; i++){
            JButton btn = new JButton();
        //    btn.setIcon(new ImageIcon("src/img/one.png"));
            btn.setBackground(Color.decode("#90caf9")); // for buttons background color
            btn.setFont(font);
            mainPanel.add(btn);
            btns[i-1] = btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i-1)); // every digit identity
        }
        this.add(mainPanel,BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Clicked");// checking every button worked or not
        JButton currentButton = (JButton) e.getSource();  // which current button clicked
        String nameStr = currentButton.getName();
        //System.out.println(nameStr); //checking current button
        int name  = Integer.parseInt(nameStr.trim());
        if(gameOver == true){
            JOptionPane.showMessageDialog(this,"Game Already Over.......");
            return;
        }

        if(gameChances[name] == 2){
            if(activePlayer == 1){
                currentButton.setIcon(new ImageIcon("src/img/one.png"));//icon set
                gameChances[name] = activePlayer; // this button don't accept click any_more
                activePlayer=0;
            }
            else{
                currentButton.setIcon(new ImageIcon("src/img/cross.png"));
                gameChances[name] = activePlayer; // // this button don't accept click any_more
                activePlayer=1;
            }
            // fine the winner ............
            for(int [] temp:wps){
                if((gameChances[temp[0]] == gameChances[temp[1]]) && (gameChances[temp[1]] == gameChances[temp[2]]) && gameChances[temp[2]] != 2) {
                    winner = gameChances[temp[0]];
                    gameOver = true;
                    JOptionPane.showMessageDialog(null, "Player " + winner + " has won the game...");
                    int i = JOptionPane.showConfirmDialog(this, "do yout want to play more ??");
                    if (i == 0) { // if player wants to play again ..
                        this.setVisible(false);
                        new MyGame();
                    } else if (i == 1) { // if player don't want to play again ...
                        System.exit(1234);
                    } else {
                        System.exit(1234);
                    }

                    System.out.println(i);
                    break;
                }
            }
            // ..........................
            // draw logic.........
            int c=0;
            for(int x:gameChances){
                if(x==2) {
                    c++;
                    break;
                }
            }
            if(c==0 && gameOver==false) {
                JOptionPane.showMessageDialog(null, "Its draw ...");
                int i=JOptionPane.showConfirmDialog(this,"Play More??");
                if (i == 0) { // if player wants to play again ..
                    this.setVisible(false);
                    new MyGame();
                } else if (i == 1) { // if player don't want to play again ...
                    System.exit(1234);
                } else {
                    System.exit(1234);
                }
                gameOver=true;
            }
        }
        else{
            JOptionPane.showMessageDialog(this,"Position already occupied...");
        }

    }
}
