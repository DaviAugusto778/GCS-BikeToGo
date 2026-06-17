package pooTrabalhoFinal;

public class Servico extends Item {
	public Servico(String nome, String descricao, double valor) {
		super(nome, descricao, valor);
	}

	protected String Observacao;

	public String getObservacao() {
		return Observacao;
	}

	public void setObservacao(String observacao) {
		Observacao = observacao;
	}
	
	
}
