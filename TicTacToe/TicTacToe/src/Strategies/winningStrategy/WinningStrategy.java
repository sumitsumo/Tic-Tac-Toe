package Strategies.winningStrategy;

import Models.Board;
import Models.Move;

public interface WinningStrategy 
{
    public void handleUndo(Board board,Move move);
    public boolean checkWinner(Board board, Move move);
}
