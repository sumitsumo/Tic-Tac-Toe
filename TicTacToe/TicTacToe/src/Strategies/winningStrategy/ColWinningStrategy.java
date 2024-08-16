package Strategies.winningStrategy;

import Models.Board;
import Models.Move;
import Models.Symbol;

import java.util.Map;
import java.util.HashMap;

public class ColWinningStrategy implements WinningStrategy
{
    Map<Integer,Map<Symbol,Integer>> counts=new HashMap<>();

    @Override
    public boolean checkWinner(Board board,Move move)
    {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        if(!counts.containsKey(col)) {
            counts.put(col, new HashMap<>());
        }

        Map<Symbol, Integer> colMap = counts.get(col);

        if(!colMap.containsKey(symbol)) {
                colMap.put(symbol, 0);
        }

        colMap.put(symbol, colMap.get(symbol) + 1);

        if(colMap.get(symbol) == board.getSize()) {
            return true;
        }

        return false;
    }

    @Override 
    public void handleUndo(Board board,Move move)
    {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        Map<Symbol, Integer> colMap = counts.get(col);
        colMap.put(symbol, colMap.get(symbol) - 1);   
    }
}
