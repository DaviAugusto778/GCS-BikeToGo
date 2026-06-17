package pooTrabalhoFinal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class JanelaPedidos extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JTable tabelaPedidos;
    private JList<Item> listaItens;
    private DefaultTableModel tableModel;
    private DefaultListModel<Item> listModel;
    private Cliente cliente;

    public JanelaPedidos(Cliente cliente) {
        this.cliente = cliente;

        setTitle("Histórico de Pedidos de: " + cliente.getNome());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        tableModel = new DefaultTableModel(new Object[]{"ID Pedido", "Data", "Status", "Valor Total"}, 0) {
        	
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        listModel = new DefaultListModel<>();

        tabelaPedidos = new JTable(tableModel);
        listaItens = new JList<>(listModel);
        
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createTitledBorder("Todos os Pedidos"));
        painelTabela.add(new JScrollPane(tabelaPedidos), BorderLayout.CENTER);
        
        JPanel painelLista = new JPanel(new BorderLayout());
        painelLista.setBorder(BorderFactory.createTitledBorder("Itens do Pedido Selecionado"));
        painelLista.add(new JScrollPane(listaItens), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelTabela, painelLista);
        splitPane.setDividerLocation(450);

        add(splitPane);

        tabelaPedidos.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = tabelaPedidos.getSelectedRow();
                if (selectedRow != -1) {
                    Pedido pedidoSelecionado = cliente.getPedidos().get(selectedRow);
                    atualizarListaDeItens(pedidoSelecionado);
                }
            }
        });

        carregarPedidosNaTabela();
    }

    private void carregarPedidosNaTabela() {
        tableModel.setRowCount(0);

        ArrayList<Pedido> pedidos = cliente.getPedidos();
        for (Pedido pedido : pedidos) {
            tableModel.addRow(new Object[]{
                pedido.getId(),
                pedido.getDataFormatada(),
                pedido.getStatus(),
                String.format("R$ %.2f", pedido.getValorTotal())
            });
        }
    }

    private void atualizarListaDeItens(Pedido pedido) {
        listModel.clear();
        for (Item item : pedido.getItensPedido()) {
            listModel.addElement(item);
        }
    }
}
