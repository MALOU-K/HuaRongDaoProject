import model.MapModel;
import view.game.GameFrame;
import view.homepage.HomeFrame;

import javax.swing.*;

public class Mainn {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeFrame home = new HomeFrame(600, 500);
            home.setVisible(true);
            String currentUser = "靓仔";
            MapModel mapModel = new MapModel(new int[][]{
                    {4, 3, 3, 7},
                    {4, 3, 3, 7},
                    {5, 0, 0, 6},
                    {5, 0, 0, 6},
                    {1, 0, 0, 1}
            });
            GameFrame gameFrame = new GameFrame(600, 500, mapModel,null,currentUser);
            gameFrame.setVisible(false);
            home.setGameFrame(gameFrame);

//靓仔
        });

    }
}
