package Strategies.botPlayingStrategy;

import Models.Board;
import Models.Cell;
import Models.CellState;
import Models.Move;

import java.util.List;
public class EasyBotPlayingStrategy implements BotPlayingStrategy
{
    @Override
    public Move makeMove(Board board)
    {
        for(List<Cell> row:board.getBoard())
        {
            for(Cell cell:row)
            {
                if(cell.getCellState().equals(CellState.EMPTY))
                    return new Move(null,cell);
            }
        }
        return null;
    }
}
