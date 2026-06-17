package pooTrabalhoFinal;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Empresa {
	private String nomeEmpresa;
	private String CNPJ;
	private String telefone;
	private Endereco Endereco;
	protected ImageIcon imagemIcon = new ImageIcon(getClass().getResource("BicicletaBoy.jpg"));
	
	ArrayList<Item> Itens = new ArrayList<>();
	
	public Empresa() {
		
	}

	public Empresa(String nomeEmpresa, String CNPJ, String telefone) {
		this.nomeEmpresa = nomeEmpresa;
		this.CNPJ = CNPJ;
		this.telefone = telefone;
	}
	
	public Empresa(String nomeEmpresa, String CNPJ, String telefone, ImageIcon imagemIcon) {
		this.nomeEmpresa = nomeEmpresa;
		this.CNPJ = CNPJ;
		this.telefone = telefone;
		this.imagemIcon = imagemIcon;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return Endereco;
	}

	public void setEndereco(Endereco endereco) {
		Endereco = endereco;
	}

	public void adicionarItem(Item item) {
		Itens.add(item);
	}

	public ArrayList<Item> getItens() {
		return Itens;
	}

	@Override
	public String toString() {
		return "Empresa [nomeEmpresa=" + nomeEmpresa + ", CNPJ=" + CNPJ + ", telefone=" + telefone + ", Endereco="
				+ Endereco + "]";
	}

	
}
