package pooTrabalhoFinal;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CriarEmpresa extends JPanel  {
		Empresa empresa;
		ImageIcon imagemIcon = new ImageIcon(getClass().getResource("sem-imagem.jpg"));
		
		private static final long serialVersionUID = 1L;
			public CriarEmpresa() {
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
				janela.add(new JLabel("CNPJ:"));
				JTextField labelCNPJ = new JTextField(20);
				janela.add(labelCNPJ);
				janela.add(new JLabel("Telefone:"));
				JTextField labelTelefone = new JTextField(15);
				janela.add(labelTelefone);
				
				int j = JOptionPane.showConfirmDialog(null,janela,"Digite os dados:",JOptionPane.OK_CANCEL_OPTION);
				if (j != JOptionPane.CANCEL_OPTION) {
				
					try {
				        String nomeEmpresa = labelNome.getText();
				        String cnpj = labelCNPJ.getText();
				        String telefone = labelTelefone.getText();
				        
				        TelefoneInvalidoException.validar(telefone);

				        empresa = new Empresa(nomeEmpresa, cnpj, telefone, imagemIcon);

				    } catch (TelefoneInvalidoException e) {
				        JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				    }
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
			
			public Empresa RetornaEmpresa() {
				return empresa;
			}
}
