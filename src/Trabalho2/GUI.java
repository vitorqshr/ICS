package Trabalho2;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import sintese.*;

public class GUI {
	
	//private Tocador tocador;
	private Thread refresh;
	private JFrame frame;
	private JProgressBar progressBar;
	private JMenuBar menuBar;
	private JMenu mEscolherInstrumento;
	private JMenu mEscolherMelodia;
	private JMenuItem mntmArquivoMidi;
	private JMenuItem mntmIstrumento1;
	private JMenuItem mntmIstrumento2;
	private JMenuItem mntmIstrumento3;
	private JMenuItem mMelodia1;
	private JMenuItem mMelodia2;
	private JMenuItem mMelodia3;
	private JMenuItem mMelodia4;
	private JMenuItem mMelodia5;
	private JMenuItem mMelodia6;
	private JMenuItem mMelodia7;
	private JButton playBtn;
	private JLabel lblFileName;
	private JButton pauseBtn;
	private JButton stopBtn;
	private File arquivoMidi;
	private int progresso;
	private int volume;
	private String nomeMelodia;
	private enum Estado {
		TOCANDO, PAUSADO, PARADO, INICIAL
	}
	Estado estado = Estado.INICIAL;
	private JSlider sliderVolume;
	private int instrumentoSelecionado;
	private Instrumento instrumento;
	private int melodiaSelecionada;
	
	/**
	 * Inicia o programa.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//Getters e setters
	
	public int getProgresso() {
		return progresso;
	}

	public File getArquivoMidi() {
		return arquivoMidi;
	}

	public int getVolume() {
		return volume;
	}

	
	public void setProgresso(int progresso) {
		this.progresso = progresso;
	}

	/**
	 * Cria a aplicação.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Inicializa os conteudos do frame.
	 */
	private void initialize() {
		instrumentoSelecionado = 0;
		melodiaSelecionada = 0;
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/Trabalho1/imagens/windowIcon.png")));
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		
		
		mEscolherInstrumento = new JMenu("Escolher instrumento");
		menuBar.add(mEscolherInstrumento);
		
		mntmIstrumento1  = new JMenuItem("Instrumento 1");
		mntmIstrumento1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instrumentoSelecionado = 1;
				carregaInstrumento();
			}
		});
		
		mEscolherInstrumento.add(mntmIstrumento1);
		
		mntmIstrumento2  = new JMenuItem("Instrumento 2");
		mntmIstrumento2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instrumentoSelecionado = 2;
				carregaInstrumento();
			}
		});
		
		mEscolherInstrumento.add(mntmIstrumento2);
		
		mntmIstrumento3  = new JMenuItem("Instrumento 3");
		mntmIstrumento3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instrumentoSelecionado = 3;
				carregaInstrumento();
			}
		});
		
		mEscolherInstrumento.add(mntmIstrumento3);		
		
		mEscolherMelodia = new JMenu("Escolher melodia");
		menuBar.add(mEscolherMelodia);
		
		mMelodia1  = new JMenuItem("drawing_quintet_flauta");
		mMelodia1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				melodiaSelecionada = 1;
				nomeMelodia = "drawing_quintet_flauta";
				carregaInstrumento();
			}
		});
		
		mEscolherMelodia.add(mMelodia1);
		
		mMelodia2  = new JMenuItem("fuga");
		mMelodia2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeMelodia = "fuga";
				melodiaSelecionada = 2;
				carregaInstrumento();
			}
		});
		
		mEscolherMelodia.add(mMelodia2);
		
		mMelodia3  = new JMenuItem("invencao14_direita");
		mMelodia3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeMelodia = "invencao14_direita";
				melodiaSelecionada = 3;
				carregaInstrumento();
			}
		});
		
		mEscolherMelodia.add(mMelodia3);
		
		mMelodia4  = new JMenuItem("invencao4_direita");
		mMelodia4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeMelodia = "invencao4_direita";
				melodiaSelecionada = 4;
				carregaInstrumento();
			}
		});
		
		mEscolherMelodia.add(mMelodia4);
		
		mMelodia5  = new JMenuItem("invencao4_esquerda");
		mMelodia5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeMelodia = "invencao4_esquerda";
				melodiaSelecionada = 5;
				carregaInstrumento();
			}
		});
		
		mEscolherMelodia.add(mMelodia5);
		
		mMelodia6  = new JMenuItem("bwv988goldberg_v03_eq");
		mMelodia6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeMelodia = "bwv988goldberg_v03_eq";
				melodiaSelecionada = 6;
				carregaInstrumento();
			}
		});
		
		mEscolherMelodia.add(mMelodia6);
		
		mMelodia7  = new JMenuItem("bwv988goldberg_v03_eq");
		mMelodia7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeMelodia = "bwv988goldberg_v03_eq";
				melodiaSelecionada = 7;
				carregaInstrumento();
			}
		});
		
		mEscolherMelodia.add(mMelodia7);
		
		
		
		
		
		playBtn = new JButton("");
		playBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(playBtn.isEnabled()){
					tocar();
				}
			}
		});
		playBtn.setRolloverIcon(new ImageIcon(GUI.class.getResource("/Trabalho1/imagens/playRollOver.png")));
		playBtn.setDisabledIcon(new ImageIcon(GUI.class.getResource("/Trabalho1/imagens/playOff.png")));
		playBtn.setContentAreaFilled(false);
		playBtn.setBorder(null);
		playBtn.setBorderPainted(false);
		playBtn.setToolTipText("Reproduzir");
		playBtn.setIcon(new ImageIcon(GUI.class.getResource("/Trabalho1/imagens/play.png")));
		playBtn.setEnabled(false);		
		
		lblFileName = new JLabel("Selecione o instrumento e a melodia");
				
		JButton volBtn = new JButton("");
		volBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		volBtn.setIcon(new ImageIcon(GUI.class.getResource("/Trabalho1/imagens/volume.png")));
		volBtn.setToolTipText("Parar");
		volBtn.setFocusPainted(false);
		volBtn.setContentAreaFilled(false);
		volBtn.setBorderPainted(false);
		volBtn.setBorder(null);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(playBtn))
						.addComponent(lblFileName))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblFileName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(playBtn)))
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
			
	}
	
	public void tocar(){
		estado = Estado.TOCANDO;
		playBtn.setEnabled(false);
		//pauseBtn.setEnabled(true);
		//stopBtn.setEnabled(true);
		instrumento.tocar();
		//threadHandler();	
	}
	
//	public void pausar(){
//		estado = Estado.PAUSADO;
//		
//		pauseBtn.setEnabled(false);
//		playBtn.setEnabled(true);
//		stopBtn.setEnabled(true);
//		refresh.interrupt();
//	}
//	
//	public void parar(){
//		estado = Estado.PARADO;
//		
//		stopBtn.setEnabled(false);
//		playBtn.setEnabled(true);
//		pauseBtn.setEnabled(false);
//		refresh.interrupt();
//	}
	
	public void threadHandler(){
		refresh = new Thread(){
			public void run(){
					while(estado == Estado.TOCANDO && !Thread.currentThread().isInterrupted()){
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}
			}
		};
		refresh.start();
	}
	
//	public void atualizaProgresso(){
//		DecimalFormat df = new DecimalFormat("00");
//		if(progressBar.getValue() >= progressBar.getMaximum()){
//			parar();
//		}
//	}
	
	public void carregaInstrumento(){
		try {
			if(instrumentoSelecionado > 0 && melodiaSelecionada > 0){
				playBtn.setEnabled(true);
				lblFileName.setText("Instrumento: " + instrumentoSelecionado  +  "  Melodia: " + nomeMelodia);
				if (instrumentoSelecionado == 1) {
					instrumento = new Instrumento1(melodiaSelecionada, 12f, 30f, 900f, 900f);
				}
				else if (instrumentoSelecionado == 2){
					//Parametros: num(do tema), ganho(envoltoria)[P5], frequenciaEnv(frequencia da envoltoria)[P6], 
					//frequenciaEnvOsc(frequencia da portadora)[P7], frequenciaRuido[P8], ganhoRuido[P9],
					//Parametro opcional: valor(fator de andamento)
					instrumento = new Instrumento2(melodiaSelecionada, 12f, 30f, 900f, 900f, 15f);
				}
				else if (instrumentoSelecionado == 3){
					instrumento = new Instrumento3(melodiaSelecionada, 32f, 30f, 900f, 900f, 32f, 100f, 10f);
				}
			}else if(instrumentoSelecionado > 0){
				lblFileName.setText("Selecione a melodia");
			}else if(melodiaSelecionada>0){
				lblFileName.setText("Selecione o instrumento");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}


