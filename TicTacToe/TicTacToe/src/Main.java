import Controllers.GameController;
import Exceptions.*;
import Models.*;
import Strategies.winningStrategy.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws PlayerCountMismatch,DuplicateSymbolException, MoreThanOneBotException {
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);

        int dimensions = 3;
        List<Player> players = new ArrayList<>();
        players.add(
                new Player("Sumit",new Symbol('X'), 1L, PlayerType.HUMAN)
        );

        players.add(
                new Bot(1L, "Vikash", new Symbol('X'), BotDifficulty.EASY)
        );

        List<WinningStrategy> winningStrategies = List.of(
                new ColWinningStrategy(),
                new RowWinningStrategy(),
                new DiagWinningStrategy()
        );

        Game game = gameController.startGame(
                dimensions,
                players,
                winningStrategies
        );

        while(gameController.checkState(game).equals(GameState.IN_PROGRESS)) {
            /*
            * 1. print board
            * 2. x's turn
            * 3. ask to make move
            * */

            gameController.printBoard(game);

            System.out.println("Undo? (y/n)");
            String ans = scanner.next();
            if(ans.equalsIgnoreCase("y")) {
                gameController.undo(game);
                continue;
            }

            gameController.makeMove(game);
        }

        System.out.println("Game has ended");
        GameState gameState = gameController.checkState(game);

        if(gameState.equals(GameState.WIN)) {
            System.out.println("Winner is: " + gameController.getWinner(game).getName());
        } else {
            System.out.println("It is a draw");
        }

    }
}