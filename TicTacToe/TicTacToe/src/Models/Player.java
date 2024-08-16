package Models;

import java.util.Scanner;

public class Player 
{
    private Long id;
    private String name;
    private Symbol symbol;
    private PlayerType playerType;
    private Scanner scanner;

    public Player(String name,Symbol symbol,Long id,PlayerType playerType)
    {
        this.id=id;
        this.symbol=symbol;
        this.playerType=playerType;
        this.name=name;
        this.scanner =new Scanner(System.in);
    }
    
    public Move makeMove(Board board)
    {
        System.out.println("Enter the row to move");
        int row=scanner.nextInt();

        System.out.println("Enter the column to move");
        int col=scanner.nextInt();

        return new Move(this,new Cell(row,col));
    }
    //getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
    
}
