package Models;

import Strategies.botPlayingStrategy.BotPlayingStrategyFactory;

public class Bot extends Player {
    private BotDifficulty botDifficultyLevel;
    private Strategies.botPlayingStrategy.BotPlayingStrategy botPlayingStrategy;

    public Bot(Long id, String name, Symbol symbol, BotDifficulty difficultyLevel) {
        super(name,symbol, id, PlayerType.BOT);
        this.botDifficultyLevel = difficultyLevel;
        this.botPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategy(botDifficultyLevel);
    }

    @Override
    public Move makeMove(Board board) {
        Move move = botPlayingStrategy.makeMove(board);
        move.setPlayer(this);

        return move;
    }
}