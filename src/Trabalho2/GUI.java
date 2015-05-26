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
	
	private Tocador tocador;
	private Thread refresh;
	private JFrame frame;
	private JProgressBar progressBar;
	private JMenuBar menuBar;
	private JMenu mnAbrir;
	private JMenu mEscolherInstrumento;
	private JMenuItem mntmArquivoMidi;
	private JMenuItem mntmIstrumento1;
	private JMenuItem mntmIstrumento2;
	private JMenuItem mntmIstrumento3;
	private JButton playBtn;
	private JLabel lblFileName;
	private JButton pauseBtn;
	private JButton stopBtn;
	private File arquivoMidi;
	private int progresso;
	private int volume;
	private int andamento;
	private String armTon;
	private String duracao;
	private String metro;
	private String formCompass;
	private enum Estado {
		TOCANDO, PAUSADO, PARADO, INICIAL
	}
	Estado estado = Estado.INICIAL;
	private JSlider sliderVolume;
	private JLabel lblAndamento;
	private JLabel lblFormCompass;
	private JLabel lblMetro;
	private JLabel lblArmTon;
	private JLabel lblDuracao;
	private int instrumentoSelecionado;
	
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
	
	public void setFormCompass(String formCompass) {
		this.formCompass = formCompass;
		lblFormCompass.setText("F\u00F3rmula do Compasso:   " + formCompass);
	}

	
	public void setProgresso(int progresso) {
		this.progresso = progresso;
	}

	public void setAndamento(int andamento) {
		this.andamento = andamento;
		lblAndamento.setText("Andamento:   " + andamento);
	}

	public void setArmTon(String armTon) {
		this.armTon = armTon;
		lblArmTon.setText("Armadura de Tonalidade:   " + armTon);
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
		lblDuracao.setText("Dura\u00E7\u00E3o: " + duracao);
	}

	public void setMetro(String metro) {
		this.metro = metro;
		lblMetro.setText("Metro:   " + metro);
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
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/Trabalho1/imagens/windowIcon.png")));
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		//mnAbrir = new JMenu("Escolher instrumento");
		//menuBar.add(mnAbrir);
		
		mEscolherInstrumento = new JMenu("Escolher instrumento");
		menuBar.add(mEscolherInstrumento);
		
		mntmArquivoMidi = new JMenuItem("Instrumento 1");
		mntmArquivoMidi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser openMidi = new JFileChooser();
				openMidi.setFileFilter(new FileNameExtensionFilter("Arquivo MIDI", "mid", "midi"));		
				if(openMidi.showOpenDialog(openMidi) == JFileChooser.APPROVE_OPTION){
					arquivoMidi = openMidi.getSelectedFile();
					carregaMidi();
					estado = Estado.PARADO;
				}
			}
		});
		//mnAbrir.add(mntmArquivoMidi);
		
		mntmIstrumento1  = new JMenuItem("Instrumento 1");
		mntmArquivoMidi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instrumentoSelecionado = 1;
			}
		});
		
		mEscolherInstrumento.add(mntmIstrumento1);
		
		mntmIstrumento2  = new JMenuItem("Instrumento 2");
		mntmArquivoMidi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instrumentoSelecionado = 2;
			}
		});
		
		mEscolherInstrumento.add(mntmIstrumento2);
		
		mntmIstrumento3  = new JMenuItem("Instrumento 3");
		mntmArquivoMidi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instrumentoSelecionado = 3;
			}
		});
		
		mEscolherInstrumento.add(mntmIstrumento3);
		
		progressBar = new JProgressBar();
		progressBar.addMouseListener(new MouseAdapter() {
			int pos, horas, minutos, segundos;
			float mouseX, barWidth, dur;
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(estado != Estado.INICIAL){
					mouseX = e.getX();
					barWidth = progressBar.getWidth();
					dur = tocador.getSegundos();
					pos = Math.round ( (mouseX /barWidth) * dur);
					//System.out.println("pos="+pos+" mousex="+mouseX+" barW="+barWidth+" dur="+dur);
					horas = pos/3600;
					minutos = (pos%3600)/60;
					segundos = (pos%3600)%60;
					tocador.mudarPosicao(horas, minutos, segundos);
					//System.out.println("horas:"+horas+" minutos:"+minutos+" segundos:"+segundos);
					atualizaProgresso();
				}
			}
		});
		
		
		
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
		
		pauseBtn = new JButton("");
		pauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pauseBtn.isEnabled()){
					pausar();
				}
			}
		});
		pauseBtn.setFocusPainted(false);
		pauseBtn.setBorderPainted(false);
		pauseBtn.setDisabledIcon(new ImageIcon(GUI.class.getResource("/Trabalho1/imagens/pauseOff.png")));
		pauseBtn.setRolloverIcon(new ImageIcon(GUI.class.getResource("/Trabalho1/imagens/pauseRollOver.png")));
		pauseBtn.setIcon(new ImageIcon(GUI.class.getResource("/Trabalho1/imagens/pause.png")));
		pauseBtn.setToolTipText("Pausar");
		pauseBtn.setContentAreaFilled(false);
		pauseBtn.setBorder(null);
		pauseBtn.setEnabled(false);
		
		stopBtn = new JButton("");
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parar();
			}
		});
		stopBtn.setIcon(new ImageIcon(GUI.class.getResource("/Trabalho1/imagens/stop.png")));
		stopBtn.setRolloverIcon(new ImageIcon(GUI.class.getResource("/Trabalho1/imagens/stopRollOver.png")));
		stopBtn.setDisabledIcon(new ImageIcon(GUI.class.getResource("/Trabalho1/imagens/stopOff.png")));
		stopBtn.setToolTipText("Parar");
		stopBtn.setFocusPainted(false);
		stopBtn.setContentAreaFilled(false);
		stopBtn.setBorderPainted(false);
		stopBtn.setBorder(null);
		stopBtn.setEnabled(false);
		
		
		lblFileName = new JLabel("Arquivo: ");
		
		lblDuracao = new JLabel("Dura\u00E7\u00E3o: ");
		
		lblFormCompass = new JLabel("F\u00F3rmula do Compasso: ");
		
		lblMetro = new JLabel("Metro: ");
		
		lblAndamento = new JLabel("Andamento: ");
		
		lblArmTon = new JLabel("Armadura de Tonalidade: ");
		
		sliderVolume = new JSlider();
		sliderVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				try {
					tocador.mudaVolume(sliderVolume.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
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
							.addComponent(playBtn)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pauseBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(stopBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 219, Short.MAX_VALUE)
							.addComponent(volBtn)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
						.addComponent(lblFileName)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDuracao)
								.addComponent(lblAndamento))
							.addGap(113)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblFormCompass)
									.addGap(125)
									.addComponent(lblMetro))
								.addComponent(lblArmTon))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblFileName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDuracao)
						.addComponent(lblFormCompass)
						.addComponent(lblMetro))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAndamento)
						.addComponent(lblArmTon))
					.addPreferredGap(ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(playBtn)
							.addComponent(pauseBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addComponent(stopBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(volBtn))
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
			
	}
	
	public void carregaMidi (){
		try {
			tocador = new Tocador(this.getArquivoMidi());
		} catch (Exception e) {
			this.tocador.sair();
			e.printStackTrace();
		}
		DecimalFormat df = new DecimalFormat("00");
		setDuracao( df.format( ( (int)tocador.getSegundos() ) / 3600) + ":" + df.format(( ( (int)tocador.getSegundos())%3600) / 60) + ":" + df.format(( ( (int)tocador.getSegundos())%3600) % 60));
		lblFileName.setText("Arquivo:   " + arquivoMidi.getName());
	
		progressBar.setStringPainted(true);
		progressBar.setString("00:00:00");
		progressBar.setMaximum((int)tocador.getSegundos());
		playBtn.setEnabled(true);
		pauseBtn.setEnabled(false);
		stopBtn.setEnabled(false);
		try {
			tocador.mudaVolume(sliderVolume.getValue());
			setArmTon(tocador.getGestor().getTonalidade());
			Dimension d = tocador.getGestor().getFormulaDeCompasso();
			setFormCompass((int)d.getWidth()+"/"+(int)d.getHeight()); 
			setMetro("1/" + (int)d.getHeight());
			setAndamento(tocador.getGestor().getAndamento());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tocar(){
		estado = Estado.TOCANDO;
		playBtn.setEnabled(false);
		pauseBtn.setEnabled(true);
		stopBtn.setEnabled(true);
		tocador.tocar();
		threadHandler();	
	}
	
	public void pausar(){
		estado = Estado.PAUSADO;
		
		pauseBtn.setEnabled(false);
		playBtn.setEnabled(true);
		stopBtn.setEnabled(true);
		
		tocador.pausar();
		refresh.interrupt();
	}
	
	public void parar(){
		estado = Estado.PARADO;
		
		stopBtn.setEnabled(false);
		playBtn.setEnabled(true);
		pauseBtn.setEnabled(false);
		tocador.parar();
		refresh.interrupt();
		atualizaProgresso();
	}
	
	public void threadHandler(){
		refresh = new Thread(){
			public void run(){
					while(estado == Estado.TOCANDO && !Thread.currentThread().isInterrupted()){
						if(tocador.getTempo() * 1000000 == tocador.getSegundos()){
							tocador.parar();
						}
						
						atualizaProgresso();
						
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
	
	public void atualizaProgresso(){
		
		progressBar.setValue((int) (tocador.getTempo()/1000000));
		DecimalFormat df = new DecimalFormat("00");
		progressBar.setString(df.format( Math.round(( ((float)tocador.getTempo())/1000000 ) / 3600)) + ":" + df.format(( Math.round(( ((float)tocador.getTempo())/1000000))%3600) / 60) + ":" + df.format(( Math.round(( ((float)tocador.getTempo())/1000000))%3600) % 60));
		if(progressBar.getValue() >= progressBar.getMaximum()){
			parar();
		}
	}
}


