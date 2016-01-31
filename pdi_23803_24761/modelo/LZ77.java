package pdi_23803_24761.modelo;

	public class LZ77 {
		private String texto;
		private int buffer_tam;
		private int janela_tam;
		
		public LZ77(String img, int tBuffer, int tJanela){
			setTexto(img);
			setBufferTam(tBuffer);
			setJanelaTam(tJanela);
		}
		
		public String getTexto(){
			return texto;
		}
		public void setTexto(String t){
			texto = t;
		}
		public void setJanelaTam(int j){
			this.janela_tam = j;
		}
		public void setBufferTam(int b){
			this.buffer_tam = b;
		}
		public int getJanelaTam(){
			return this.janela_tam;
		}
		public int getBufferTam(){
			return this.buffer_tam;
		}

	}

