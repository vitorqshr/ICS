package Trabalho3;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
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
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class GUI {
	private Thread refresh;
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu mnAbrir;
	private JMenuItem mntmArquivoMidi;
	private JLabel lblFileName;
	private File arquivoMidi;
	private int progresso;
	private int volume;
	private int andamento;
	private String armTon;
	private String duracao;
	private String metro;
	private String formCompass;
	private Conversor conversor;
	private enum Estado {
		TOCANDO, PAUSADO, PARADO, INICIAL
	}
	Estado estado = Estado.INICIAL;
	private JLabel lblAndamento;
	private JLabel lblFormCompass;
	private JLabel lblMetro;
	private JLabel lblArmTon;
	private JLabel lblDuracao;
	private JButton btnGerar;
	private JTextArea textArea;
	
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
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/Trabalho3/imagens/windowIcon.png")));
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnAbrir = new JMenu("Abrir");
		menuBar.add(mnAbrir);
		
		mntmArquivoMidi = new JMenuItem("Arquivo MIDI");
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
		mnAbrir.add(mntmArquivoMidi);
		
		
		lblFileName = new JLabel("Arquivo: ");
		
		lblDuracao = new JLabel("Dura\u00E7\u00E3o: ");
		
		lblFormCompass = new JLabel("F\u00F3rmula do Compasso: ");
		
		lblMetro = new JLabel("Metro: ");
		
		lblAndamento = new JLabel("Andamento: ");
		
		lblArmTon = new JLabel("Armadura de Tonalidade: ");
		
		btnGerar = new JButton("Gerar");
		btnGerar.setEnabled(false);
		
		btnGerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnGerar.isEnabled()){
					gerar();
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
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
								.addComponent(lblArmTon)))
						.addComponent(btnGerar))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
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
					.addGap(36)
					.addComponent(btnGerar)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		frame.getContentPane().setLayout(groupLayout);
			
	}
	
	
	private void gerar(){
		try {
			String info = conversor.converter();
			textArea.setText(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carregaMidi (){
		try {
			conversor = new Conversor(arquivoMidi);
			btnGerar.setEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DecimalFormat df = new DecimalFormat("00");
		lblFileName.setText("Arquivo:   " + arquivoMidi.getName());
	
		try {
			setArmTon(conversor.getGestor().getTonalidade());
			Dimension d = conversor.getGestor().getFormulaDeCompasso();
			setFormCompass((int)d.getWidth()+"/"+(int)d.getHeight()); 
			setMetro("1/" + (int)d.getHeight());
			setAndamento(conversor.getGestor().getAndamento());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


