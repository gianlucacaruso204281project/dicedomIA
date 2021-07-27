package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Dices {

    private static Dices instance = null;

    /* init dice array ImageIcons */
    private static ImageIcon[] dices = new ImageIcon[7];

    private Dices() {
        for (int i = 0; i < dices.length; i++) {
            try {
                Image image = ImageIO.read(new File("src/dices/dice-" + (i + 1) + ".png"))
                        .getScaledInstance(ImagesConfig.Dice.WIDTH, ImagesConfig.Dice.HEIGHT, ImagesConfig.Dice.METHOD);
                ImageIcon imageIcon = new ImageIcon(image);
                dices[i] = imageIcon;
            } catch (IOException e) {
            }
        }
    }

    /* Singleton */
    public static Dices getInstance() {

        if (instance == null)
            instance = new Dices();

        return instance;
    }

    public ImageIcon getDiceIcon(int value) {
    	if (value > 0 && value <= 7)
    		return dices[value - 1];
    	return null;

    }

}
