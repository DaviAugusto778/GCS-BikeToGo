package pooTrabalhoFinal;

import javax.swing.*;

public class JanelaServico extends JPanel {
	private static final long serialVersionUID = 1L;
	
	Servico servico;
	
	public JanelaServico(Empresa empresa) {
	JPanel janela = new JPanel();
	janela.setVisible(true);
	
	janela.add(new JLabel("Nome:"));
	JTextField labelNome = new JTextField(20);
	janela.add(labelNome);
	janela.add(new JLabel("Descrição:"));
	JTextField labelDescricao = new JTextField(40);
	janela.add(labelDescricao);
	janela.add(new JLabel("Valor:"));
	JTextField labelValor = new JTextField(5);
	janela.add(labelValor);
	
	int j = JOptionPane.showConfirmDialog(null,janela,"Digite os dados:",JOptionPane.OK_CANCEL_OPTION);
	if (j != JOptionPane.CANCEL_OPTION) {
	
	String nomeServico = labelNome.getText();
	String descricaoServico = labelDescricao.getText();
	double valorServico = Double.parseDouble(labelValor.getText());
    
    
       servico = new Servico(nomeServico,descricaoServico,valorServico);
       empresa.adicionarItem(servico);
	}
	}
	public Servico retornaServico() {
		return servico;
	}
}