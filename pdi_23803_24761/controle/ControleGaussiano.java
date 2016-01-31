

/** Link -> https://code.google.com/p/som-based-clustering/source/browse/trunk/SelfOrganizingMaps/src/main/java/robbie/imageprocessing/GaussianBlur.java?r=22 **/


package pdi_23803_24761.controle;


import java.awt.image.*;
import java.awt.*;
import pdi_23803_24761.modelo.*;


public class ControleGaussiano  {
	
	/** Método responsável por realizar a
	 *  Construção da matriz de convolução,
	 *  no qual a matriz é calculada e normalizada **/
	//***************************************************************************************************	
	private FiltroGaussiano constroiMatriz(int tamMascara, int sigma, FiltroGaussiano g){
		
		FiltroGaussiano gauss = g;
		
		double [][] matriz = new double[tamMascara][tamMascara];
		
		double n = tamMascara/2;
		double soma = 0.0;
		
		/** Calculo dos valores da matriz **/
		for (int x = 0; x < tamMascara; ++x){ 
		    for (int y = 0; y < tamMascara; ++y) {
		    	matriz[x][y] = Math.exp(-0.5*(Math.pow((x-n)/sigma, 2.0) + Math.pow((y-n)/sigma,2.0)))/(2 * Math.PI * sigma * sigma);
		    	soma += matriz[x][y];
		    }
		}

		for (int x = 0; x < tamMascara; ++x) 
		    for (int y = 0; y < tamMascara; ++y)
		    	matriz[x][y] /= soma;
		
		gauss.setMascara(matriz);
		return gauss;
	}
	//***************************************************************************************************
	/** Aplicação da convolução na imagem a ser filtrada  **/
	private Color aplicaConvolucao(BufferedImage imgOriginal, Point centroMatriz, int tamMascara, double[][] matriz, int maxWidth, int maxHeight){
		double soma = 0.0;
		double[] cor = new double[] {0,0,0};
		Color tempCor;
		
		for (int i = 0; i < tamMascara; i++) {
			for (int j = 0; j < tamMascara; j++) {
				
				int x = centroMatriz.x-((tamMascara/2)-i);
				int y = centroMatriz.y-((tamMascara/2)-j);
				
				/** Neste trecho é realizada a convolução em cada banda de cada pixel **/
				if(x>0 && x<maxWidth && y>0 && y<maxHeight){
					tempCor = new Color(imgOriginal.getRGB(centroMatriz.x-((tamMascara/2)-i), centroMatriz.y-((tamMascara/2)-j)));
					cor[0] += tempCor.getRed()*matriz[i][j];
					cor[1] += tempCor.getGreen()*matriz[i][j];
					cor[2] += tempCor.getBlue()*matriz[i][j];

					soma += matriz[i][j];
				}
			}
		}
		
		for (int j = 0; j < cor.length; j++) {
			cor[j]/=soma;
		}

		tempCor = new Color((int)cor[0],(int)cor[1],(int)cor[2]);

		return tempCor;
	}
	//***************************************************************************************************
	public FiltroGaussiano FiltragemGaussiana(int tamMascara, int sigma, BufferedImage imgOriginal, FiltroGaussiano g){
		
		BufferedImage imgFiltrada = new BufferedImage(imgOriginal.getWidth(),imgOriginal.getHeight(),BufferedImage.TYPE_INT_RGB);
				
		FiltroGaussiano gauss = g;
		/** constrói a matriz **/ 
		gauss = constroiMatriz(tamMascara, sigma, gauss);
				
		/** Aplica o filtro pixel por pixel **/
		for (int i = 0; i < imgOriginal.getWidth(); i++) {
			for (int j = 0; j < imgOriginal.getHeight(); j++) {
				imgFiltrada.setRGB(i, j, this.aplicaConvolucao(imgOriginal, new Point(i,j), tamMascara, gauss.getMascara(), imgOriginal.getWidth(),imgOriginal.getHeight()).getRGB());
			}
		}
		
		gauss.setImagemFiltrada(imgFiltrada);
		return gauss;
	}
	
}
