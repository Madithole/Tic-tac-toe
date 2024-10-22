/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tictactoe;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


/**
 *
 * @author MAX
 */
public class XO implements ActionListener {
    
    private static final int BOARD_SIZE = 3;
    private static final int SQUARE_SIZE = 125;
    private static final Color PLAYER_1_COLOR = Color.RED;
    private static final Color PLAYER_2_COLOR = Color.BLUE;

    private JFrame frame;
    private JPanel boardPanel;
    private JButton[] squares;
    private JButton restButton;
    private JLabel statusLabel;
    private boolean player1Turn;
    private Random random;

    public XO() {
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE);

        boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        squares = new JButton[BOARD_SIZE * BOARD_SIZE];
        for (int i = 0; i < squares.length; i++) {
            squares[i] = new JButton();
            squares[i].setFont(new Font("Arial", Font.BOLD, 36));
            squares[i].addActionListener(this);
            boardPanel.add(squares[i]);
        }

        statusLabel = new JLabel("Player 1's turn");

        restButton = new JButton("Rest");
        restButton.addActionListener(this);
        
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);
        frame.add(restButton, BorderLayout.NORTH);

        random = new Random();
        player1Turn = random.nextBoolean();

        frame.setVisible(true);
    }
    
     @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== restButton){
            restGame();
        }
        else{
        JButton square = (JButton) e.getSource();
        if (square.getText().isEmpty()) {
            if (player1Turn) {
                square.setText("X");
                square.setForeground(PLAYER_1_COLOR);
                player1Turn = false;
                statusLabel.setText("Player 2's turn");
            } else {
                square.setText("O");
                square.setForeground(PLAYER_2_COLOR);
                player1Turn = true;
                statusLabel.setText("Player 1's turn");
            }
            checkForWin();
        }
        }
    }
    
    private void checkForWin() {
       
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (squares[i * BOARD_SIZE].getText().equals(squares[i * BOARD_SIZE + 1].getText())
                    && squares[i * BOARD_SIZE].getText().equals(squares[i * BOARD_SIZE + 2].getText())
                    && !squares[i * BOARD_SIZE].getText().isEmpty()) {
                win(squares[i * BOARD_SIZE].getText());
                return;
            }
            if (squares[i].getText().equals(squares[i + BOARD_SIZE].getText())
                    && squares[i].getText().equals(squares[i + 2 * BOARD_SIZE].getText())
                    && !squares[i].getText().isEmpty()) {
                win(squares[i].getText());
                return;
            }
            if (squares[0].getText().equals(squares[4].getText()) && squares[0].getText().equals(squares[8].getText())
                 && !squares[0].getText().isEmpty()) {
            win(squares[0].getText());
            return;
            }
              if (squares[2].getText().equals(squares[4].getText()) && squares[2].getText().equals(squares[6].getText())
                && !squares[2].getText().isEmpty()) {
            win(squares[2].getText());
            return;
            }
             boolean draw = true;
        for (JButton square : squares) {
            if (square.getText().isEmpty()) {
                draw = false;
                break;
            }
        }
        if (draw) {
            statusLabel.setText("It's a draw!");
            disableSquares();
        }  
     } 
   }
    
    private void win(String winner) {
        statusLabel.setText("Player " + winner + " wins!");
        disableSquares();
    }

    private void disableSquares() {
        for (JButton square : squares) {
            square.setEnabled(false);
        }
    }
    
    private void restGame(){
        player1Turn = random.nextBoolean();
        statusLabel.setText("Player" + (player1Turn ? "1" : "2") + " 's turn");
        for(JButton square: squares){
            square.setText ("");
            square.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToe();
            }
        });
    }
    
    
}
