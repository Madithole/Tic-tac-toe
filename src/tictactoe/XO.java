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
    private int xScore = 0;
    private int oScore = 0;
    private int drawScore = 0;
    private static final Color PLAYER_1_COLOR = Color.RED;
    private static final Color PLAYER_2_COLOR = Color.BLUE;

    private JFrame frame;
    private JPanel boardPanel;
    private JButton[] square;
    private JButton resetButton;
    private JLabel statusLabel;
    private boolean player1Turn;
    private Random random;
    private JLabel titleLabel;
    private JLabel scoreLabel;
    private final int MATCH_WINS = 2;

    public XO() {
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450,550);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        titleLabel = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        scoreLabel = new JLabel("X: 0 | 0:0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(Color.BLACK);
        updateScoreboard();
        
        JPanel scorePanel = new JPanel(new BorderLayout());
        scorePanel.add(scoreLabel, BorderLayout.CENTER);
        
        boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        square = new JButton[BOARD_SIZE * BOARD_SIZE];
       
        for (int i = 0; i < square.length; i++) {
            square[i] = new JButton();
            
            square[i].setBackground(Color.WHITE);
            square[i].setFocusPainted(false);
            square[i].setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
            square[i].setFont(new Font("Arial", Font.BOLD, 36));
            square[i].addActionListener(this);
            boardPanel.add(square[i]);
        }

        statusLabel = new JLabel("",SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial",Font.BOLD,20));
        statusLabel.setForeground(Color.darkGray);
        
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scorePanel, BorderLayout.BEFORE_FIRST_LINE);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(resetButton, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusLabel,BorderLayout.CENTER);
        bottomPanel.add(resetButton,BorderLayout.EAST);

        frame.add(bottomPanel,BorderLayout.SOUTH);
        
        random = new Random();
        player1Turn = random.nextBoolean();
        
        statusLabel.setText(player1Turn ?
                "Player X Starts" :
                "Player O Starts");
        frame.setVisible(true);
    }
    
     @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== resetButton){
            resetGame();
        }
        else{
        JButton clickedButton = (JButton) e.getSource();
        if (clickedButton .getText().isEmpty()) {
            if (player1Turn) {
                clickedButton .setText("X");
                clickedButton .setForeground(PLAYER_1_COLOR);
                player1Turn = false;
                statusLabel.setText("Player O's turn");
            } else {
                clickedButton .setText("O");
                clickedButton .setForeground(PLAYER_2_COLOR);
                player1Turn = true;
                statusLabel.setText("Player X's turn");
            }
            checkForWin();
        }
        }
    }
    
    private void checkForWin() {
       
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (square[i * BOARD_SIZE].getText().equals(square[i * BOARD_SIZE + 1].getText())
                    && square[i * BOARD_SIZE].getText().equals(square[i * BOARD_SIZE + 2].getText())
                    && !square[i * BOARD_SIZE].getText().isEmpty()) {
                win(square[i * BOARD_SIZE].getText(),
                    i * BOARD_SIZE,
                    i * BOARD_SIZE + 1,
                    i * BOARD_SIZE + 2);
                return;
            }
            if (square[i].getText().equals(square[i + BOARD_SIZE].getText())
                    && square[i].getText().equals(square[i + 2 * BOARD_SIZE].getText())
                    && !square[i].getText().isEmpty()) {
                win(square[i].getText(),
                     i,
                     i + BOARD_SIZE,
                     i + 2 * BOARD_SIZE);
                return;
            }
            if (square[0].getText().equals(square[4].getText()) && square[0].getText().equals(square[8].getText())
                 && !square[0].getText().isEmpty()) {
            win(square[0].getText(), 0, 4, 8);
            return;
            }
              if (square[2].getText().equals(square[4].getText()) && square[2].getText().equals(square[6].getText())
                && !square[2].getText().isEmpty()) {
            win(square[2].getText(), 2, 4, 6);
            return;
            }
             boolean draw = true;
        for (JButton btn: square) {
            if (btn.getText().isEmpty()) {
                draw = false;
                break;
            }
        }
        if (draw) {
            statusLabel.setText("It's a draw!");
            disableSquares();
            
            drawScore++;
            updateScoreboard();
        }  
     } 
   }
    
    private void win(String winner, int a , int b, int c) {
        
        statusLabel.setText("Player " + winner + " wins!");
        disableSquares();
        square[a].setBackground(Color.GREEN);
        square[b].setBackground(Color.GREEN);
        square[c].setBackground(Color.GREEN);
        
        if(winner.equals("X")){
        xScore++;
        } else if(winner.equals("O")){
        oScore++;}
        
        updateScoreboard();
        checkMatchWinner();
        
    }

    private void updateScoreboard(){
        scoreLabel.setText(
        "X: " + xScore +
        " |  O: " + oScore +
        "  Draws: " + drawScore);
    }
    
    private void resetMatch() {

        xScore = 0;
        oScore = 0;
        drawScore = 0;

        updateScoreboard();

        resetGame();
}
    
    private void checkMatchWinner() {
         if(xScore == MATCH_WINS){
        JOptionPane.showMessageDialog(
        frame,
        " Player X wins the best of the Three Match!"
                );
        resetMatch();
        } 
    else if(oScore == MATCH_WINS){
        
        JOptionPane.showMessageDialog(frame, 
            " Player O wins the best of Three Match!");
    }
        }
    
    private void disableSquares() {
        for (JButton btn : square) {
            btn.setEnabled(false);
        }
    }
    
    private void resetGame(){
        player1Turn = random.nextBoolean();
        statusLabel.setText("Player" + (player1Turn ? "1" : "2") + " 's turn");
        for(JButton btn: square){
            btn.setBackground(Color.WHITE);
            btn.setText ("");
            btn.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new XO();
            }
        });
    }

}