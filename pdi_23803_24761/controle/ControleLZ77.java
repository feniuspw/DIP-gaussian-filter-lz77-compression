package pdi_23803_24761.controle;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import pdi_23803_24761.modelo.LZ77;
import pdi_23803_24761.modelo.Tupla;



public class ControleLZ77 {
private String frase;
private BufferedImage imDescomp=null;
private StringBuffer decomp= new StringBuffer();
int largura, altura;
//***************************************************************************************************	
	public ArrayList<Tupla> Compressao(BufferedImage imagem, int buff, int jan) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		
		String nomeArquivo = "img"+imagem.getHeight()+"x"+imagem.getWidth()+".LZ77";
		StringBuffer compressao = new StringBuffer();
		
	
		try{
			for(int x = 0; x < imagem.getHeight(); x++){
				for(int y = 0; y < imagem.getWidth(); y++){
					decomp.append(String.valueOf(imagem.getRGB(y, x)));
					decomp.append(" ");
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
		
		JFileChooser save = new JFileChooser();
		save.setFileSelectionMode(JFileChooser.FILES_ONLY);
		save.setSelectedFile(new File(nomeArquivo));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("LZ77", "LZ77");
		
		save.setFileFilter(filter);
		
		if (save.showSaveDialog(null) == JFileChooser.CANCEL_OPTION) {
            System.exit(0);
        }
        File arq = save.getSelectedFile();
        String caminho = arq.getPath();

        FileWriter fw = new FileWriter(caminho);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(String.valueOf(imagem.getWidth()) + " " + String.valueOf(imagem.getHeight()));
        bw.newLine();
		
        
		LZ77 lz = new LZ77(frase, buff,jan);
		String literal;
		String janela = "";
		String buffer = "";
		//StringBuffer ret = new StringBuffer();
		ArrayList<Tupla> ret = new ArrayList<Tupla>();	
		int i = 0;
		
		frase = decomp.toString();
		
		while(i < frase.length()){
			int janela_inicio = i-lz.getJanelaTam();
			if(janela_inicio < 0) janela_inicio = 0;
			
			if(i > frase.length() || janela_inicio > i) janela = "";
			else janela = frase.substring(janela_inicio, i);
			
			//arrumando os bounds do buffer
			if(i+lz.getBufferTam()>frase.length()) buffer = frase.substring(i, frase.length());
			else buffer = frase.substring(i, i+lz.getBufferTam());
			Tupla p = new Tupla(0,0,""+frase.charAt(i));
			for(int k = buffer.length();k>0;k--){
				int indice = janela.lastIndexOf(buffer.substring(0, k));
				if(indice >=0){
					literal = ""; // \0 = fim de arquivo
					if((i+k)<frase.length()) literal = ""+frase.charAt(i+k);
					p = new Tupla(janela.length()-indice-1,k, literal);
					break;
				}
			}
			i = i+ p.getTam() + 1;
			bw.write(p.getPos() + "," + p.getTam() + "," + p.getLiteral() + ",");
			ret.add(p);
		}
        bw.close();
        fw.close();
		return ret;
	}
	//***************************************************************************************************
	public BufferedImage descompressaoArquivo() throws FileNotFoundException, IOException {

        JFileChooser fc = new JFileChooser();//Escolhe uma imagem navegando pelos diretórios
        String linha, vetLinha[] = null, valorEntrada;

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Compressao LZ77", "LZ77");
        fc.setFileFilter(filter);

        if (fc.showDialog(null, "Abrir") == JFileChooser.APPROVE_OPTION) {

            File arqEntrada = fc.getSelectedFile();

            FileReader fr = new FileReader(arqEntrada);//Cria um arquivo de leitura
            BufferedReader br = new BufferedReader(fr);//Cria um buffer de leitura

            //Lê a primeira linha que contém a dimensão da imagem
            linha = br.readLine();
            vetLinha = linha.split(" ");
            this.largura = Integer.parseInt(vetLinha[0]);
            this.altura = Integer.parseInt(vetLinha[1]);

            linha = br.readLine();
            vetLinha = linha.split(",");

            br.close();//Fecha o buffer de leitura
            fr.close();//Fecha o arquivo de leitura
        }
        int tam = vetLinha.length;
        Tupla tup;
        StringBuffer ret = new StringBuffer("");
        int pos = 0;
        
        for (int i = 0; i < tam - 2; i += 3) {
            tup = new Tupla(Integer.parseInt(vetLinha[i]), Integer.parseInt(vetLinha[i + 1]), vetLinha[i + 2]);

            pos = ret.length() - tup.getPos() - 1;
            if (pos < 0) {
                pos = 0;
            }
            ret.append(ret.substring(pos, pos + tup.getTam()) + tup.getLiteral());
        }
        tup = new Tupla(Integer.parseInt(vetLinha[tam - 2]), Integer.parseInt(vetLinha[tam - 1]), "");
        ret.append(ret.substring(pos, pos + tup.getTam()) + tup.getLiteral());

        vetLinha = null;
        vetLinha = ret.toString().split(" ");
        int cont = 0;

        this.imDescomp = new BufferedImage(this.largura, this.altura, 1);

        for (int i = 0; i < this.altura; i++) {
            for (int j = 0; j < this.largura; j++) {
                int aux = Integer.parseInt(vetLinha[cont]);
               
                imDescomp.setRGB(j, i, aux);
                cont++;
            }
        }
        return imDescomp;
    }
	//***************************************************************************************************
	public StringBuffer tuplasToString(ArrayList<Tupla> t){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<t.size(); i++){
			sb.append(t.get(i).getPos());
			sb.append(",");
			sb.append(t.get(i).getTam());
			sb.append(",");
			sb.append(t.get(i).getLiteral());
			sb.append(",");
		}
		return sb;
	}
	//***************************************************************************************************	
	public double taxaCompressao(int imgNormal, int imgComprimida){	
		double result = 1.0 - ((double)imgComprimida/(double)imgNormal);
		return result;
	}
	//***************************************************************************************************
	public String getFrase() {
		return frase;
	}
	//***************************************************************************************************
	public void setFrase(String frase) {
		this.frase = frase;
	}
	//***************************************************************************************************
	public StringBuffer getDecomp() {
		return decomp;
	}
	//***************************************************************************************************
	public void setDecomp(StringBuffer f) {
		this.decomp = f;
	}
	
}
