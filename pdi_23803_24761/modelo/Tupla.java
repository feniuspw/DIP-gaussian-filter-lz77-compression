package pdi_23803_24761.modelo;

public class Tupla {
		private int pos;
		private int tam;
		private String literal;
		
		//construtor
		public Tupla(int p,int t,String lit){
			setPos(p);
			setTam(t);
			setLiteral(lit);
		}
		public int getPos(){
			return this.pos;
		}
		public int getTam(){
			return this.tam;
		}
		public String getLiteral(){
			return this.literal;
		}
		public void setPos(int p){
			this.pos = p;
		}
		public void setTam(int t){
			this.tam = t;
		}
		public void setLiteral(String lit){
			this.literal = lit;
		}
}
