package pooTrabalhoFinal;

public class Endereco {
	private String rua;
	private int numero;
	private String quadra;
	private String referencia;
	private String CEP;
	
	public Endereco(String rua, int numero, String quadra, String referencia, String CEP) {
		this.rua = rua;
		this.numero = numero;
		this.quadra = quadra;
		this.referencia = referencia;
		this.CEP = CEP;
	}
	
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getQuadra() {
		return quadra;
	}
	public void setQuadra(String quadra) {
		this.quadra = quadra;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getCEP() {
		return CEP;
	}
	public void setCEP(String cEP) {
		CEP = cEP;
	}

	@Override
	public String toString() {
		return "Endereco [rua=" + rua + ", numero=" + numero + ", quadra=" + quadra + ", referencia=" + referencia
				+ ", CEP=" + CEP + "]";
	}
	
}
