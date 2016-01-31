package pdi_23803_24761.view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import pdi_23803_24761.controle.ControleGaussiano;
import pdi_23803_24761.controle.ControleImagem;
import pdi_23803_24761.controle.ControleLZ77;
import pdi_23803_24761.modelo.FiltroGaussiano;
import pdi_23803_24761.modelo.Tupla;



public class InterfacePrincipal extends JFrame implements ActionListener, ChangeListener, KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int cont=0;
	
	/**Instancias de Controles*/
	private ControleGaussiano ctrGauss = new ControleGaussiano();
	private ControleImagem ctrImg = new ControleImagem();
	private ControleLZ77 ctrLZ77 = new ControleLZ77();

	/**Instancias das imagens*/
	private BufferedImage imagemOriginal;
	
	/**Variaveis de parametrizacao do Filtro*/
	int mascara;
	int sigma;
	
	/**Variaveis da interface*/
	private JPanel principal, filtragemConfig, meio, filtragemComprime, imgOriginalPanel, imgFiltradaPanel;
	private File fileImagem;
	private JSlider slideMascara, slideDesvio;
	private JButton abreImagem, botaoComprime, botaoDescomprime;
	private JTextField campoBuffer, campoJanela, campoTaxa;
	private JLabel taxaCompressao;
	private JScrollPane jScrollPane, jScrollPaneRes;
	private FiltroGaussiano fg;
	

	
	//CONSTRUTOR DA INTERFACE ************************************************************************
	public InterfacePrincipal(){
		super("Aplicacao do Filtro Gaussiano em imagens digitais");
		inicializaInterface();
		configuraInterface();	
				
	}
	
	public void inicializaInterface(){
		/**Inicializando a interface*/
		/** Valores default*/
		mascara=3;
		sigma=1;
		
		/** Inicializando Panels*/
		principal = new JPanel();
		filtragemConfig = new JPanel();
		meio = new JPanel();
		filtragemComprime = new JPanel();
		imgOriginalPanel = new JPanel(new BorderLayout());
		imgFiltradaPanel  = new JPanel(new BorderLayout());
		
		/** Inicializando JSliders*/
		slideMascara = new JSlider(JSlider.HORIZONTAL,3,15,3);
		slideDesvio = new JSlider(JSlider.HORIZONTAL,0,50,1);
		
		/** Inicializando JButtons*/
		abreImagem = new JButton("Abrir Imagem");
		botaoDescomprime = new JButton("Descomprimir");
		botaoComprime = new JButton("Comprimir");
		
		/** Inicializando JTextFields*/
		campoBuffer = new JTextField(5);
		campoJanela = new JTextField(5);
		campoTaxa = new JTextField(5);
		
		/** Inicializando JLabels*/
		taxaCompressao = new JLabel("Taxa de Compressao");
		
		/** Inicializando JScrollPane*/
		jScrollPane = new JScrollPane();
		jScrollPaneRes = new JScrollPane();
	}
	
	public void configuraInterface(){
		
		
		principal.setLayout(new BorderLayout());
		principal.setBackground(new Color(173,173,173));
		filtragemConfig.setBackground(new Color(42,77,193));
		meio.setBackground(new Color(100,100,200));
		meio.setLayout(new GridLayout(1,2));
		filtragemComprime.setBackground(new Color(160,160,160));
		
		//norte da tela (opcoes de filtro)
		slideMascara.setMajorTickSpacing(2);
		slideMascara.setMinorTickSpacing(2);
		slideMascara.setPaintTicks(true);
		slideMascara.setPaintLabels(true);
		slideMascara.setSnapToTicks(true);
		slideMascara.setEnabled(false);
		
		slideDesvio.setMajorTickSpacing(10);
		slideDesvio.setMinorTickSpacing(5);
		slideDesvio.setPaintTicks(true);
		slideDesvio.setPaintLabels(true);
		slideDesvio.setEnabled(false);
		
		
		filtragemConfig.add(abreImagem);
		filtragemConfig.add(new JLabel("Mascara"));
		filtragemConfig.add(slideMascara);
		filtragemConfig.add(new JLabel("   Desvio Padrao"));
		filtragemConfig.add(slideDesvio);
		
		//sul da tela (opcoes de compressao)
		campoBuffer.setText("20");
		campoJanela.setText("100");
		campoBuffer.setEnabled(false);
		campoJanela.setEnabled(false);
		campoTaxa.setEditable(false);
		campoTaxa.setText(" -- %");
		botaoComprime.setEnabled(false);
		filtragemComprime.add(new JLabel("Tamanho do Buffer"));
		filtragemComprime.add(campoBuffer);
		filtragemComprime.add(new JLabel("Tamanho da Janela"));
		filtragemComprime.add(campoJanela);
		filtragemComprime.add(taxaCompressao);
		filtragemComprime.add(campoTaxa);
		filtragemComprime.add(botaoComprime);
		filtragemComprime.add(botaoDescomprime);
		
		//TESTE   
	    jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    jScrollPaneRes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    jScrollPaneRes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	    
		//*******
		
		imgOriginalPanel.add(jScrollPane);
		imgFiltradaPanel.add(jScrollPaneRes);
		imgOriginalPanel.setBackground(new Color(210,210,210));
		imgFiltradaPanel.setBackground(new Color(200,100,200));
		meio.add(imgOriginalPanel);
		meio.add(imgFiltradaPanel);
		principal.add(filtragemConfig,BorderLayout.NORTH);
		principal.add(meio,BorderLayout.CENTER);
		principal.add(filtragemComprime, BorderLayout.SOUTH);

		/**Configura Listener*/
		abreImagem.addActionListener(this);
		botaoComprime.addActionListener(this);
		botaoDescomprime.addActionListener(this);
		slideDesvio.addChangeListener(this);
		slideMascara.addChangeListener(this);
		campoBuffer.addKeyListener(this);
		campoJanela.addKeyListener(this);
		
		/**Seta os valores default*/
		mascara = slideMascara.getValue();
		sigma = slideDesvio.getValue();
		
		/**Instancia FiltroGaussiano*/
		fg = new FiltroGaussiano();
		
		//adiciona o painel principal no frame e ajusta alguns parâmetros
		this.add(principal);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1024,720);
		this.setMinimumSize(new Dimension(1024,720));
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getSource());
		if(arg0.getSource()==abreImagem){
			campoJanela.setEnabled(true);
			campoBuffer.setEnabled(true);
			botaoDescomprime.setEnabled(true);
			campoBuffer.setEnabled(true);
			campoJanela.setEnabled(true);
			JFileChooser fc = new JFileChooser();
			int opcao = fc.showOpenDialog(this);
			if (opcao == JFileChooser.APPROVE_OPTION) {
				fileImagem = fc.getSelectedFile();
				imagemOriginal = ctrImg.lerImagem(fc.getSelectedFile());
				jScrollPane.setViewportView(new JLabel(new ImageIcon(fileImagem.getPath(),BorderLayout.CENTER)));
				jScrollPaneRes.setViewportView(new JLabel(new ImageIcon(fileImagem.getPath(),BorderLayout.CENTER)));
				slideDesvio.setEnabled(true);
				slideMascara.setEnabled(true);
				
				/** Refaz a filtragem! */
				fg = ctrGauss.FiltragemGaussiana(mascara, sigma, imagemOriginal, fg);
				jScrollPaneRes.setViewportView(new JLabel(new ImageIcon(fg.getImagemFiltrada())));
			}
		}
		else if(arg0.getSource()==botaoComprime){
			ArrayList<Tupla> tuplas;
			try {
				tuplas = ctrLZ77.Compressao(fg.getImagemFiltrada(), Integer.parseInt(campoBuffer.getText()) , Integer.parseInt(campoJanela.getText()));
				double taxa = ctrLZ77.taxaCompressao(ctrLZ77.getFrase().length(), tuplas.size());
				taxa *=100;
				DecimalFormat df = new DecimalFormat("#,###0.00");
				campoTaxa.setText("" + df.format(taxa)+"%");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		else if(arg0.getSource()==botaoDescomprime){
			try {
				BufferedImage descompressao = ctrLZ77.descompressaoArquivo();
				jScrollPaneRes.setViewportView(new JLabel(new ImageIcon(descompressao)));
				repaint();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()== slideDesvio){
			/** Ativa o botao de Comprimir*/
			botaoComprime.setEnabled(true);
			
			/** Capta o novo valor referente ao Slider do desvio padrao */
			sigma = slideDesvio.getValue();
			
			/** Refaz a filtragem! */
			fg = ctrGauss.FiltragemGaussiana(mascara, sigma, imagemOriginal, fg);
			jScrollPaneRes.setViewportView(new JLabel(new ImageIcon(fg.getImagemFiltrada())));
		}
		else if(arg0.getSource()== slideMascara){
			/** Ativa o botao de Comprimir*/
			botaoComprime.setEnabled(true);
			
			/** Capta o novo valor referente ao Slider da Mascara*/
			mascara = slideMascara.getValue();
			
			/** Correcao, caso o valor pego pela mascara seja par*/
			if(mascara%2==0) mascara++;
			
			/** Refaz a filtragem! */
			fg = ctrGauss.FiltragemGaussiana(mascara, sigma, imagemOriginal, fg);
			jScrollPaneRes.setViewportView(new JLabel(new ImageIcon(fg.getImagemFiltrada())));
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==campoJanela){
			if(campoJanela.getText().equals("")){
				botaoComprime.setEnabled(false);
			}else{
				botaoComprime.setEnabled(true);
			}
		}
		else if(arg0.getSource()==campoBuffer){
			if(campoBuffer.getText().equals("")){
				botaoComprime.setEnabled(false);
			}else{
				botaoComprime.setEnabled(true);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}