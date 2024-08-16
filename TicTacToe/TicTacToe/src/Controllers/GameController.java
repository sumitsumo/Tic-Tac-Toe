package Controllers;

import Models.Game;
import Models.GameState;
import Models.Player;
import Strategies.winningStrategy.WinningStrategy;

import java.util.List;

import Exceptions.DuplicateSymbolException;
import Exceptions.MoreThanOneBotException;
import Exceptions.PlayerCountMismatch;
public class GameController 
{
    public Game startGame(int dimnesion,List<Player> playerList,List<WinningStrategy> winningStrategies) 
        throws PlayerCountMismatch,MoreThanOneBotException,DuplicateSymbolException
    {
        return Game
            .getBuilder()
            .setDimension(dimnesion)
            .setPlayers(playerList)
            .setWinnerStrategies(winningStrategies)
            .build();
    }
    public void makeMove(Game game)
    {
        game.makeMove();
    }
    public GameState checkState(Game game)
    {
        return game.getGameState();
    }
    public Player getWinner(Game game) {
        return game.getWinner();
    }

    public void printBoard(Game game) {
        game.printBoard();
    }

    public void undo(Game game) {
        game.undo();
    }
}
