package pooTrabalhoFinal;

import java.util.ArrayList;

public class Cliente {
	private String nome;
	private String telefone;
	private String CPF;
	private Endereco endereco;
	
	ArrayList <Pedido> pedidos = new ArrayList<>();
	
	public Cliente (String nome, String telefone, String CPF) {
		this.nome = nome;
		this.telefone = telefone;
		this.CPF = CPF;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public void adicionarPedido(Pedido pedido) {
		pedidos.add(pedido);
	}
	
	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", telefone=" + telefone + ", CPF=" + CPF + ", endereco=" + endereco + "]";
	}
	
	
}
