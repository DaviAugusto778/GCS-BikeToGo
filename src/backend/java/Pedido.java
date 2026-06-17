package pooTrabalhoFinal;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Pedido {
    private static long contadorId = 0; 
    private long id;
    private ArrayList<Item> itensPedido;
    private Date data;
    private String status;
    private double valorTotal;

    public Pedido() {
        this.id = ++contadorId; 
        this.itensPedido = new ArrayList<>();
        this.data = new Date();
        this.status = "Finalizado";
        calcularValorTotal();
    }

    public void adicionarItem(Item item) {
        this.itensPedido.add(item);
        calcularValorTotal();
    }
    
    private void calcularValorTotal() {
        this.valorTotal = 0;
        for (Item item : itensPedido) {
            this.valorTotal += item.getValor();
        }
    }
    
    public long getId() { 
    	return id; 
    }
    
    public ArrayList<Item> getItensPedido() { 
    	return itensPedido; 
    }
    
    public String getDataFormatada() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data);
    }
    
    public String getStatus() { 
    	return status; 
    }
    
    public double getValorTotal() {
    	return valorTotal; 
    }

	public static long getContadorId() {
		return contadorId;
	}

	public static void setContadorId(long contadorId) {
		Pedido.contadorId = contadorId;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setItensPedido(ArrayList<Item> itensPedido) {
		this.itensPedido = itensPedido;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
}