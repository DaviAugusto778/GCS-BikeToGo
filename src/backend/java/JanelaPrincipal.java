package pooTrabalhoFinal;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class JanelaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;

    public JanelaPrincipal(ArrayList<Empresa> lista,Cliente cliente) {
        novaJanela(lista,cliente);
    }

    public void novaJanela(ArrayList<Empresa> lista,Cliente cliente) {
        JFrame frame = new JFrame();
        
        frame.setLayout(new BorderLayout(0, 10)); 
        
        frame.setTitle("BikeToGo");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png")); 
        JLabel labelLogo = new JLabel(logoIcon);
        labelLogo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        frame.add(labelLogo,BorderLayout.PAGE_START);
        
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opções");
        JMenu menu2 = new JMenu("Perfil");
        JMenuItem novaEmpresa = new JMenuItem("Nova empresa...");
        JMenuItem carregarEmpresas = new JMenuItem("Carregar empresas(arquivo .txt)");
        JMenuItem salvarEmpresas = new JMenuItem("Salvar empresas(arquivo .txt)");
        JMenuItem meusPedidos = new JMenuItem("Meus Pedidos");
        menu.add(novaEmpresa);
        menu.add(carregarEmpresas);
        menu.add(salvarEmpresas);
        menu2.add(meusPedidos);
        menuBar.add(menu);
        menuBar.add(menu2);
        frame.setJMenuBar(menuBar);
        
        JPanel painelCards = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        for (Empresa empresa : lista) {
            CardEmpresa cardEmpresa = new CardEmpresa(empresa,cliente);
            painelCards.add(cardEmpresa.RetornaCard());
        }
        
        novaEmpresa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CriarEmpresa novaEmpresa = new CriarEmpresa();
                CardEmpresa novoCardEmpresa = new CardEmpresa(novaEmpresa.RetornaEmpresa(),cliente);
                lista.add(novaEmpresa.RetornaEmpresa());
                painelCards.add(novoCardEmpresa.RetornaCard());
                painelCards.revalidate();
                painelCards.repaint();
            }
        });
        
        meusPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                @SuppressWarnings("unused")
				JanelaPedidos meusPedios = new JanelaPedidos(cliente);
            }
        });
        
        salvarEmpresas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {             
            	GerenciadorDeArquivos.salvarEmpresas(lista, "empresas.txt");
            }
        });
        
        carregarEmpresas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                @SuppressWarnings("unused")
                ArrayList<Empresa> empresasCarregadas = GerenciadorDeArquivos.carregarEmpresasComSeletor();
                for (Empresa empresa : empresasCarregadas) {
                    CardEmpresa cardEmpresa = new CardEmpresa(empresa,cliente);
                    painelCards.add(cardEmpresa.RetornaCard());
                }
                painelCards.revalidate();
                painelCards.repaint();
            }
        });
        
        
        JScrollPane scrollPane = new JScrollPane(painelCards);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null); 
        
        frame.add(scrollPane, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }
}