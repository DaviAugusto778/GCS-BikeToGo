package pooTrabalhoFinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CardServico extends JPanel {
	private static final long serialVersionUID = 1L;

	public CardServico(Servico servico,ArrayList<Item> carrinho) {
		super(new BorderLayout(15, 15)); 
		
		String nomeServico = servico.nome;
		String descricaoServico = servico.descricao;
		double valor = servico.valor;
		
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY), 
                BorderFactory.createEmptyBorder(10, 10, 10, 10) 
        ));
        this.setBackground(Color.WHITE);
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); 


        JPanel painelInfo = new JPanel(new GridLayout(2, 1)); 
        painelInfo.setBackground(Color.WHITE);

        
        JLabel nomeLabel = new JLabel(nomeServico);
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        painelInfo.add(nomeLabel);

        JTextArea areaDescricao = new JTextArea(descricaoServico);
        areaDescricao.setWrapStyleWord(true);
        areaDescricao.setLineWrap(true);
        areaDescricao.setEditable(false);
        areaDescricao.setFocusable(false);
        areaDescricao.setFont(new Font("Arial", Font.PLAIN, 12));
        areaDescricao.setForeground(Color.GRAY);
        areaDescricao.setOpaque(false); 
        painelInfo.add(areaDescricao);
        
        this.add(painelInfo, BorderLayout.CENTER);

        JPanel painelPreco = new JPanel(new BorderLayout(5, 5));
        painelPreco.setBackground(Color.WHITE);

        
        JLabel precoLabel = new JLabel(String.format("R$ %.2f", valor));
        precoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        precoLabel.setForeground(new Color(0, 102, 0)); 
        painelPreco.add(precoLabel, BorderLayout.CENTER);

        
        JButton adicionarAoCarrinho = new JButton("Adicionar");
        painelPreco.add(adicionarAoCarrinho, BorderLayout.SOUTH);
        
        adicionarAoCarrinho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				carrinho.add(servico);
			}
		});

        this.add(painelPreco, BorderLayout.EAST);
    }
}
