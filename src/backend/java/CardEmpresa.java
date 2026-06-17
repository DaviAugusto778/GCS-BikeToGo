package pooTrabalhoFinal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class CardEmpresa extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel CardEmpresa;
	
	public CardEmpresa(Empresa empresa,Cliente cliente) {
		
	String nomeEmpresa = empresa.getNomeEmpresa();
	String descricao = empresa.getCNPJ();
	String tagline = empresa.getTelefone();
	ImageIcon logo = empresa.imagemIcon;
	
	CardEmpresa = new JPanel();
	CardEmpresa.setLayout(new BorderLayout(10, 15)); 
	CardEmpresa.setPreferredSize(new Dimension(300, 420));
    
    Border bordaLinha = BorderFactory.createLineBorder(new Color(220, 220, 220));
    Border bordaVazia = BorderFactory.createEmptyBorder(15, 15, 15, 15);
    CardEmpresa.setBorder(BorderFactory.createCompoundBorder(bordaLinha, bordaVazia));
    CardEmpresa.setBackground(Color.WHITE);

    JLabel labelImagem = new JLabel();
    if (logo != null) {
        Image imagemRedimensionada = logo.getImage().getScaledInstance(270, 150, Image.SCALE_SMOOTH);
        labelImagem.setIcon(new ImageIcon(imagemRedimensionada));
    }
    labelImagem.setHorizontalAlignment(JLabel.CENTER);
    CardEmpresa.add(labelImagem, BorderLayout.NORTH);


    JPanel painelTexto = new JPanel();
    painelTexto.setLayout(new BoxLayout(painelTexto, BoxLayout.Y_AXIS));
    painelTexto.setOpaque(false); 

    JLabel labelNome = new JLabel(nomeEmpresa);
    labelNome.setFont(new Font("Segoe UI", Font.BOLD, 20));
    labelNome.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel labelTagline = new JLabel(tagline);
    labelTagline.setFont(new Font("Segoe UI", Font.ITALIC, 14));
    labelTagline.setForeground(Color.GRAY);
    labelTagline.setAlignmentX(Component.LEFT_ALIGNMENT);

    JTextArea areaDescricao = new JTextArea(descricao);
    areaDescricao.setWrapStyleWord(true);
    areaDescricao.setLineWrap(true);
    areaDescricao.setEditable(false);
    areaDescricao.setFocusable(false);
    areaDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    areaDescricao.setOpaque(false);
    areaDescricao.setAlignmentX(Component.LEFT_ALIGNMENT);

    painelTexto.add(labelNome);
    painelTexto.add(Box.createRigidArea(new Dimension(0, 5))); 
    painelTexto.add(labelTagline);
    painelTexto.add(Box.createRigidArea(new Dimension(0, 15))); 
    painelTexto.add(areaDescricao);

    CardEmpresa.add(painelTexto, BorderLayout.CENTER);
    
    JButton botaoAcao = new JButton("Visitar Site");
    botaoAcao.setFont(new Font("Segoe UI", Font.BOLD, 14));
    
    botaoAcao.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			@SuppressWarnings("unused")
			JanelaEmpresa a = new JanelaEmpresa(empresa,cliente);
		}
	});
    
    JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    painelBotao.setOpaque(false);
    painelBotao.add(botaoAcao);
    
    CardEmpresa.add(painelBotao, BorderLayout.SOUTH);
    
	}
	
	public JPanel RetornaCard() {
		return CardEmpresa;
	}
	
}
