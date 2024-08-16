package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import Exceptions.DuplicateSymbolException;
import Exceptions.MoreThanOneBotException;
import Exceptions.PlayerCountMismatch;
import Strategies.winningStrategy.*;
// import winningStrategy.WinningStrategy;

public class Game 
{
    private List<Player> players;
    private Board board;
    private int nextMovePlayerIndex;
    private List<Move> moves;
    private GameState gameState;
    private Player Winner;
    private List<WinningStrategy> winningStrategies;

    public Game(int dimension, List<WinningStrategy> winningStrategies, List<Player> players)
    {
        this.winningStrategies=winningStrategies;
        this.players=players;
        this.board=new Board(dimension);
        this.moves=new ArrayList<>();
        this.gameState=GameState.IN_PROGRESS;
        this.nextMovePlayerIndex=0;
    }
    public static Builder getBuilder()
    {
        return new Builder();
    }
    public static class Builder
    {
        private List<Player> players;
        private int dimension;
        private List<WinningStrategy> winningStrategies;

        public Builder setPlayers(List<Player> players)
        {
            this.players=players;
            return this;
        }
        public Builder setDimension(int dimension)
        {
            this.dimension=dimension;
            return this;
        }
        public Builder setWinnerStrategies(List<WinningStrategy> winningStrategies)
        {
            this.winningStrategies=winningStrategies;
            return this;
        }
        public Builder addPlayer(Player player)
        {
            this.players.add(player);
            return this;
        }
        public Builder addWinningStrategy(WinningStrategy winningStrategy)
        {
            this.winningStrategies.add(winningStrategy);
            return this;
        }

        private void validateBotCount() throws MoreThanOneBotException
        {
            int bots=0;
            for(Player player:players)
                if(player.getPlayerType().equals(PlayerType.BOT))
                    bots++;
            if(bots>1)
                throw new MoreThanOneBotException();
        }

        private void validatePlayerCount() throws PlayerCountMismatch
        {
            if(players.size()!=dimension-1)
            {
                throw new PlayerCountMismatch();
            }
        }

        private void validateUniqueSymbolForPlayers() throws DuplicateSymbolException
        {
            HashSet<Character> set=new HashSet<>();
            for(Player player:players)
            {
                if(set.contains(player.getSymbol().getChar()))
                    throw new DuplicateSymbolException();
                set.add(player.getSymbol().getChar());
            }
        }
        private void validate() throws DuplicateSymbolException, PlayerCountMismatch, MoreThanOneBotException 
        {
            validateBotCount();;
            validatePlayerCount();
            validateUniqueSymbolForPlayers();
        }
        public Game build() throws DuplicateSymbolException, PlayerCountMismatch, MoreThanOneBotException 
        {
            validate();
            return new Game(dimension,winningStrategies,players);
        }
    }


    //getters and setters
    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }
    public void setNextMovePlayerIndex(int nextMovePlayerIndex) {
        this.nextMovePlayerIndex = nextMovePlayerIndex;
    }
    public List<Move> getMoves() {
        return moves;
    }
    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
    public GameState getGameState() {
        return gameState;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public Player getWinner() {
        return Winner;
    }
    public void setWinner(Player winner) {
        Winner = winner;
    }
    public List<WinningStrategy> getWinningStrategy() {
        return winningStrategies;
    }
    public void setWinningStrategy(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    private boolean validate(Move move)
    {
        int row=move.getCell().getRow();
        int col=move.getCell().getCol();
        
        if(row>=board.getSize() || row<0 || col>=board.getSize()||col<0)
            return false;

        if(board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY))
            return true;

        return false;
    }
    private boolean checkWinner(Board board, Move move) {
        for(WinningStrategy winningStrategy: winningStrategies) {
            if(winningStrategy.checkWinner(board, move)) {
                return true;
            }
        }

        return false;
    }

    public void makeMove() {
        Player currentMovePlayer = players.get(nextMovePlayerIndex);
        System.out.println("It is " + currentMovePlayer.getName() + "'s move. Please make your move");

        Move move = currentMovePlayer.makeMove(board);

        System.out.println(currentMovePlayer.getName() + " has made a move at row: " +
                move.getCell().getRow() + " and at col: " + move.getCell().getRow());

        if(!validate(move)) {
            System.out.println("Invalid Move. Please try again");
            return;
        }

        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Cell cellToChange = board.getBoard().get(row).get(col);
        cellToChange.setCellState(CellState.FILLED);
        cellToChange.setPlayer(currentMovePlayer);

        Move finalMove = new Move(currentMovePlayer, cellToChange);
        moves.add(finalMove);

        nextMovePlayerIndex += 1;
        nextMovePlayerIndex %= players.size();

        if(checkWinner(board, finalMove)) {
            Winner = currentMovePlayer;
            gameState = GameState.WIN;
        } else if(moves.size() == board.getSize() * board.getSize()) {
            gameState = GameState.DRAW;
        }

    }

    public void undo() {
        if(moves.size() == 0) {
            System.out.println("No moves to undo");
            return;
        }

        Move lastMove = moves.get(moves.size() - 1);
        moves.remove(lastMove);

        Cell cell = lastMove.getCell();
        cell.setCellState(CellState.EMPTY);
        cell.setPlayer(null);

        for(WinningStrategy winningStrategy: winningStrategies) {
            winningStrategy.handleUndo(board, lastMove);
        }

        nextMovePlayerIndex -= 1;
        nextMovePlayerIndex = (nextMovePlayerIndex + players.size()) % players.size();
    }

    public void printBoard() {
        board.printBoard();
    }
}
