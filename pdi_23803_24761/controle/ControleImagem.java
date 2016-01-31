package pdi_23803_24761.controle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ControleImagem {
//***************************************************************************************************
public BufferedImage lerImagem(File nome) {
		
		BufferedImage img = null;
		
		
		try {
            img = ImageIO.read(nome);
        } 
		catch (IOException e) {
			System.out.println("Image not read");
        }
        
      
       return img; 
	}
//***************************************************************************************************

}
