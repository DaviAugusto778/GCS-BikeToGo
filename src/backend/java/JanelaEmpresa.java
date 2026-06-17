package pooTrabalhoFinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JanelaEmpresa extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public JanelaEmpresa(Empresa empresa,Cliente cliente) {
		ArrayList<Item> carrinho = new ArrayList<>();
		setTitle(empresa.getNomeEmpresa());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        
        JMenu menu = new JMenu("Opções");
		JMenuBar menuBar = new JMenuBar();
		JMenuItem novoProduto= new JMenuItem("Novo Produto...");
		JMenuItem novoServico= new JMenuItem("Novo Serviço...");
		menu.add(novoProduto);
		menu.add(novoServico);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		

        JPanel listaItens = new JPanel();
        listaItens.setLayout(new BoxLayout(listaItens, BoxLayout.Y_AXIS));
        listaItens.setBackground(Color.WHITE);
        
        for (Item item : empresa.Itens) {
        	if (item instanceof Servico) {
        		Servico servico = (Servico) item;
        		CardServico cardServico = new CardServico(servico, carrinho);
        		listaItens.add(cardServico);
            } else if (item instanceof Produto) {
                Produto produto = (Produto) item;
                CardProduto cardProduto = new CardProduto(produto, carrinho);
                listaItens.add(cardProduto);
             }
        }
        
        novoProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JanelaProduto novoProduto = new JanelaProduto(empresa);
				Produto produtoTemp = novoProduto.RetornaProduto();
				if (produtoTemp != null) {
				CardProduto cardProduto = new CardProduto(produtoTemp,carrinho);
	        	listaItens.add(cardProduto); 
	        	setVisible(true);
	        	}
			}
		});
        
        novoServico.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JanelaServico novoServico = new JanelaServico(empresa);
				Servico servicoTemp = novoServico.retornaServico();
				if (servicoTemp != null) {
				CardServico cardServico = new CardServico(servicoTemp,carrinho);
	        	listaItens.add(cardServico);
	        	setVisible(true);
	        	}
			}
		});
        
        JPanel labelCarrinho = new JPanel();
        labelCarrinho.setBackground(Color.GRAY);
        labelCarrinho.setPreferredSize(new Dimension(75,75));
        labelCarrinho.setBorder(BorderFactory.createDashedBorder(Color.CYAN));
        JButton abrirCarrinho = new JButton("Abrir carrinho");
        abrirCarrinho.setPreferredSize(new Dimension(150,30));
        labelCarrinho.add(abrirCarrinho);
        add(labelCarrinho,BorderLayout.SOUTH);
        
        abrirCarrinho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//for (Item i : carrinho) System.out.println(i);
				@SuppressWarnings("unused")
				JanelaPedido pedido = new JanelaPedido(carrinho,cliente);
			}
		});
        
        
        JScrollPane scrollPane = new JScrollPane(listaItens);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); 
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
        add(scrollPane);
	}
	}
