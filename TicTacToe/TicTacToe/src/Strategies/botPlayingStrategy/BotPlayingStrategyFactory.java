package Strategies.botPlayingStrategy;

import Models.BotDifficulty;

public class BotPlayingStrategyFactory 
{
    public static BotPlayingStrategy getBotPlayingStrategy(BotDifficulty botDifficulty)
    {
        return new EasyBotPlayingStrategy();
    }
}
