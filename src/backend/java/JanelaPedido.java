package pooTrabalhoFinal;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class JanelaPedido extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private JTable itensDisponiveisTable;
    private JTable itensPedidoTable;
    private DefaultTableModel itensDisponiveisTableModel;
    private DefaultTableModel itensPedidoTableModel;
    private JButton adicionarItemButton;
    private JButton finalizarPedidoButton;
    private JLabel totalLabel;

    protected ArrayList<Item> pedidoAtual = new ArrayList<>();
    protected double valorTotal;

    public JanelaPedido(ArrayList<Item> carrinho,Cliente cliente) {
        
        setTitle("Sistema de Pedidos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setVisible(true);
         
        
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));


        JPanel disponiveisPanel = new JPanel(new BorderLayout());
        disponiveisPanel.setBorder(BorderFactory.createTitledBorder("Carrinho:"));
        itensDisponiveisTableModel = new DefaultTableModel(new Object[]{"Nome", "Valor"}, 0){
             /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
             public boolean isCellEditable(int row, int column) {
                return false;
             }
        };
        itensDisponiveisTable = new JTable(itensDisponiveisTableModel);
        atualizarTabelaItensDisponiveis(carrinho);
        disponiveisPanel.add(new JScrollPane(itensDisponiveisTable), BorderLayout.CENTER);
        adicionarItemButton = new JButton("Adicionar ao Pedido >>");
        disponiveisPanel.add(adicionarItemButton, BorderLayout.SOUTH);

      
        JPanel pedidoPanel = new JPanel(new BorderLayout());
        pedidoPanel.setBorder(BorderFactory.createTitledBorder("Itens no Pedido Atual"));
        itensPedidoTableModel = new DefaultTableModel(new Object[]{"Nome", "Valor"}, 0){
             /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
             public boolean isCellEditable(int row, int column) {
                return false;
             }
        };
        itensPedidoTable = new JTable(itensPedidoTableModel);
        pedidoPanel.add(new JScrollPane(itensPedidoTable), BorderLayout.CENTER);

        centerPanel.add(disponiveisPanel);
        centerPanel.add(pedidoPanel);
        add(centerPanel, BorderLayout.CENTER);

       
        JPanel bottomPanel = new JPanel(new BorderLayout());
        totalLabel = new JLabel("Total do Pedido: R$ 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        finalizarPedidoButton = new JButton("Finalizar Pedido");
        bottomPanel.add(totalLabel, BorderLayout.WEST);
        bottomPanel.add(finalizarPedidoButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        adicionarItemButton.addActionListener(e -> adicionarItemAoPedido(carrinho));
        finalizarPedidoButton.addActionListener(e -> finalizarPedido(pedidoAtual,cliente));
    }
    
    public double calcularValorTotal(ArrayList<Item> pedidoAtual) {
    	valorTotal = 0;
    	for (Item item : pedidoAtual) {
    		valorTotal += item.valor;
    	}
    	return valorTotal;
    }

    public void atualizarTabelaItensDisponiveis(ArrayList<Item> carrinho) {
        itensDisponiveisTableModel.setRowCount(0); 
        for (Item item : carrinho) {
            itensDisponiveisTableModel.addRow(new Object[]{item.getNome(), String.format("%.2f", item.getValor())});
        }
    }
    
    public void atualizarTabelaItensPedido(ArrayList<Item> carrinho) {
        itensPedidoTableModel.setRowCount(0); 
        for (Item item : pedidoAtual) {
            itensPedidoTableModel.addRow(new Object[]{item.getNome(), String.format("%.2f", item.getValor())});
        }
        totalLabel.setText(String.format("Total do Pedido: R$ %.2f", calcularValorTotal(pedidoAtual)));
    }

    public void adicionarItemAoPedido(ArrayList<Item> carrinho) {
        int selectedRow = itensDisponiveisTable.getSelectedRow();
        if (selectedRow >= 0) {
            Item itemSelecionado = carrinho.get(selectedRow);
            pedidoAtual.add(itemSelecionado);
            atualizarTabelaItensPedido(carrinho);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item para adicionar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void finalizarPedido(ArrayList<Item> pedidoAtual,Cliente cliente) {
        if (pedidoAtual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O pedido está vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Pedido pedido = new Pedido();
        pedido.setStatus("Aguardando Pagamento");
        pedido.setValorTotal(calcularValorTotal(pedidoAtual));
        pedido.setItensPedido(pedidoAtual);
        cliente.adicionarPedido(pedido);

        
        String resumo = "Pedido Finalizado com Sucesso!\n\n" +
                        "Valor Total: R$ " + String.format("%.2f", calcularValorTotal(pedidoAtual)) + "\n";

        JOptionPane.showMessageDialog(this, resumo, "Pedido Confirmado", JOptionPane.INFORMATION_MESSAGE);

   }
}
