package pooTrabalhoFinal;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JanelaProduto extends JPanel {
	Produto produto;
	ImageIcon imagemIcon = new ImageIcon(getClass().getResource("sem-imagem.jpg"));
	
	private static final long serialVersionUID = 1L;
		public JanelaProduto(Empresa empresa) {
			JPanel janela = new JPanel();
			janela.setVisible(true);
			
			JButton adicionarImagem = new JButton("Adicionar Imagem");
	        janela.add(adicionarImagem);
	        
	        adicionarImagem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SelecionarImagem();
				}
			});
			
			janela.add(new JLabel("Nome:"));
			JTextField labelNome = new JTextField(20);
			janela.add(labelNome);
			janela.add(new JLabel("Descrição:"));
			JTextField labelDescricao = new JTextField(40);
			janela.add(labelDescricao);
			janela.add(new JLabel("Valor:"));
			JTextField labelValor = new JTextField(5);
			janela.add(labelValor);
			janela.add(new JLabel("Quantidade:"));
			JTextField labelQuantidade = new JTextField(5);
			janela.add(labelQuantidade);
			
			int j = JOptionPane.showConfirmDialog(null,janela,"Digite os dados:",JOptionPane.OK_CANCEL_OPTION);
			if (j != JOptionPane.CANCEL_OPTION) {
			
			String nomeProduto = labelNome.getText();
			String descricaoProduto = labelDescricao.getText();
			int quantidadeProduto = Integer.parseInt(labelValor.getText());
			double valorProduto = Double.parseDouble(labelValor.getText());
	        
	        
            produto = new Produto(nomeProduto,descricaoProduto,valorProduto,quantidadeProduto,imagemIcon);
            empresa.adicionarItem(produto);
			}
		}
		
		public void SelecionarImagem() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Selecione uma imagem para o produto");
		
		 FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens (jpg, png, gif)", "jpg", "jpeg", "png", "gif");
	     fileChooser.setFileFilter(filter);
	     
	     int userSelection = fileChooser.showOpenDialog(this);
	     
	     if (userSelection == JFileChooser.APPROVE_OPTION) {
	            File arquivoSelecionado = fileChooser.getSelectedFile();
	            
	            imagemIcon = new ImageIcon(arquivoSelecionado.getPath());
	            System.out.println(arquivoSelecionado.getPath());
	     } else imagemIcon = new ImageIcon("");
	     }
		
		public Produto RetornaProduto() {
			return produto;
		}
	}