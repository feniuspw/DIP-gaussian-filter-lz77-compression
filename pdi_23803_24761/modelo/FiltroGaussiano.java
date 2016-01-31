package pdi_23803_24761.modelo;

import java.awt.image.BufferedImage;

public class FiltroGaussiano {
	
	private double mascara[][];
	private double desvioPadrao;
	private BufferedImage imagemFiltrada;
	
	
	public double [][] getMascara() {
		return mascara;
	}
	public void setMascara(double [][] mascara) {
		this.mascara = mascara;
	}
	public double getDesvioPadrao() {
		return desvioPadrao;
	}
	public void setDesvioPadrao(double desvioPadrao) {
		this.desvioPadrao = desvioPadrao;
	}
	public BufferedImage getImagemFiltrada() {
		return imagemFiltrada;
	}
	public void setImagemFiltrada(BufferedImage imagemFiltrada) {
		this.imagemFiltrada = imagemFiltrada;
	}
}
