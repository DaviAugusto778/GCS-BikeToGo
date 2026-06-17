package pooTrabalhoFinal;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Launcher {
	
	
	public static void main(String[] args) {
		ArrayList<Empresa> listaEmpresas = GerenciadorDeArquivos.carregarEmpresas("empresas.txt");
		
		Cliente cliente1 = new Cliente("Claudette","66612345678","123456789-10");
		
		/*
		Cliente cliente1 = new Cliente("Claudette","66612345678","123456789-10");
		Empresa empresa1 = new Empresa("Bicicletaria Do Boy", "71.400.069/0001-50", "(62)88542-3412");
		Empresa empresa2 = new Empresa("Elisio Rodado", "52.364.683/0001-60", "(22)99412-3421");
		Endereco endereco1 = new Endereco("Rua da Entidade",666,"01","Do lado do gancho","00000666");
		Item corrente = new Produto("Corrente de Bicleta","Feita com fibra de carbono. Vem com duas chaves",90.2,4);
		Item farol = new Produto("Qcp Faróis De Motocicleta, Holofotes Externos, Faróis Altos","Feito com gases halógenos, bateria 500A, super duravel.",50.4,4);
		Item manutencao = new Servico("Manutenção de bicicleta","Manter sua bicicleta em perfeito estado é essencial para garantir segurança, conforto e performance durante seus passeios e treinos. Nosso serviço de manutenção de bicicleta é completo e personalizado",50.3);
		empresa1.adicionarItem(corrente);
		empresa1.adicionarItem(farol);
		empresa2.adicionarItem(manutencao);
		empresa1.adicionarItem(manutencao);
		cliente1.setEndereco(endereco1);
		empresa1.setEndereco(endereco1);
		
		listaEmpresas.add(empresa1);
		listaEmpresas.add(empresa2);
		*/
		
		//GerenciadorDeArquivos.salvarEmpresas(listaEmpresas, "empresas.txt");
		
		SwingUtilities.invokeLater(new Runnable ( ) {
			@Override
			public void run() {
				@SuppressWarnings("unused")
				JanelaPrincipal a = new JanelaPrincipal(listaEmpresas,cliente1);
			}
		});
		
	}

}
