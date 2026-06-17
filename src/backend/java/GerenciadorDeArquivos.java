package pooTrabalhoFinal;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GerenciadorDeArquivos {

    public static void salvarEmpresas(ArrayList<Empresa> listaEmpresas, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Empresa empresa : listaEmpresas) {
                writer.write("---EMPRESA---");
                writer.newLine();
                writer.write("nome:" + empresa.getNomeEmpresa());
                writer.newLine();
                writer.write("cnpj:" + empresa.getCNPJ());
                writer.newLine();
                writer.write("telefone:" + empresa.getTelefone());
                writer.newLine();

                writer.write("---ITENS---");
                writer.newLine();

                for (Item item : empresa.getItens()) {
                    if (item instanceof Produto) {
                        Produto p = (Produto) item;
                        String linha = String.join(";", "PRODUTO", p.getNome(), p.getDescricao(), String.valueOf(p.getValor()), String.valueOf(p.getQuantidade()));
                        writer.write(linha);
                        writer.newLine();
                    } else if (item instanceof Servico) {
                        Servico s = (Servico) item;
                        String linha = String.join(";", "SERVICO", s.getNome(), s.getDescricao(), String.valueOf(s.getValor()), s.getObservacao());
                        writer.write(linha);
                        writer.newLine();
                    }
                }
            }
            System.out.println("Dados salvos com sucesso em " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    public static ArrayList<Empresa> carregarEmpresas(String nomeArquivo) {
        ArrayList<Empresa> listaEmpresas = new ArrayList<>();
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            System.out.println("Arquivo '" + nomeArquivo + "' não encontrado. Retornando lista vazia.");
            return listaEmpresas;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            Empresa empresaAtual = null;
            boolean lendoItens = false;

            while ((linha = reader.readLine()) != null) {
                if (linha.equals("---EMPRESA---")) {
                    empresaAtual = new Empresa();
                    listaEmpresas.add(empresaAtual);
                    lendoItens = false;
                } else if (linha.equals("---ITENS---")) {
                    lendoItens = true;
                } else if (empresaAtual != null) {
                    if (!lendoItens) {
                        String[] partes = linha.split(":", 2);
                        if (partes.length == 2) {
                            String chave = partes[0];
                            String valor = partes[1];
                            switch (chave) {
                                case "nome": empresaAtual.setNomeEmpresa(valor); break;
                                case "cnpj": empresaAtual.setCNPJ(valor); break;
                                case "telefone": empresaAtual.setTelefone(valor); break;
                            }
                        }
                    } else {
                        String[] partes = linha.split(";", -1);
                        String tipo = partes[0];
                        
                        if (tipo.equals("PRODUTO")) {
                            String nome = partes[1];
                            String desc = partes[2];
                            double valor = Double.parseDouble(partes[3]);
                            int qtd = Integer.parseInt(partes[4]);
                            empresaAtual.adicionarItem(new Produto(nome, desc, valor, qtd));
                        } else if (tipo.equals("SERVICO")) {
                            String nome = partes[1];
                            String desc = partes[2];
                            double valor = Double.parseDouble(partes[3]);
                            empresaAtual.adicionarItem(new Servico(nome, desc, valor));
                        }
                    }
                }
            }
            System.out.println("Dados carregados com sucesso de " + nomeArquivo);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar o arquivo: " + e.getMessage());
        }

        return listaEmpresas;
    }

    public static ArrayList<Empresa> carregarEmpresasComSeletor() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione um arquivo de empresas para carregar");
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Texto (.txt)", "txt");
        fileChooser.setFileFilter(filter);
        
        int resultado = fileChooser.showOpenDialog(null); 

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivoSelecionado = fileChooser.getSelectedFile();
            
            return carregarEmpresas(arquivoSelecionado.getAbsolutePath());
        } else {
            System.out.println("Nenhum arquivo selecionado. Operação de carregamento cancelada.");
            return new ArrayList<>(); 
        }
    }
}
