package constants;

import java.awt.Color;

public class GameConstants {
    public static int cellWidth = 20;

    public static Color gameBgColor = Color.WHITE;
    public static Color snakeHeadColor = Color.BLACK;
    public static Color snakeBodyColor = Color.GRAY;
    public static Color foodColor = Color.BLUE;
    public static Color wallColor = Color.RED;

    public static int screenHeight = 520;
    public static int screenWidth = 520;

    public static int wallTopX = 60;
    public static int wallTopY = 60;
    public static int wallBottomX = screenHeight - 60 - wallTopX;
    public static int wallBottomY = screenWidth - 60 - wallTopY;

    public static int feildWidth = wallBottomX - wallTopX;
    public static int feildHeight = wallBottomY - wallTopY;

    public static int feildHeightPoints = feildHeight / cellWidth - 2;
    public static int feildWidthPoints = feildWidth / cellWidth - 2;

    public static int foodScore = 50;

    public static int gameRefreshMills = 500;
}
