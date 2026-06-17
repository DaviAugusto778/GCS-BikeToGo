package pooTrabalhoFinal;

import javax.swing.ImageIcon;

public class Produto extends Item {
	protected int quantidade;
	protected ImageIcon imagem;
	
	public Produto(String nome, String descricao, double valor,int quantidade, ImageIcon imagem) {
		super(nome, descricao, valor);
		this.quantidade = quantidade;
		this.imagem = imagem;
	}
	
	public Produto(String nome, String descricao, double valor,int quantidade) {
		super(nome, descricao, valor);
		this.quantidade = quantidade;
		imagem = new ImageIcon(getClass().getResource("sem-imagem.jpg"));
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public ImageIcon getImagem() {
        return imagem;
    }
	
	
}
