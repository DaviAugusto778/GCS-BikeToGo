package pooTrabalhoFinal;

public class TelefoneInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;

	public TelefoneInvalidoException(String mensagem) {
        super(mensagem);
    }

    public static void validar(String telefone) throws TelefoneInvalidoException {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new TelefoneInvalidoException("O campo 'Telefone' não pode estar vazio.");
        }
        for (char c : telefone.toCharArray()) {
            if (Character.isLetter(c)) {
                throw new TelefoneInvalidoException("O telefone não pode conter letras. Caractere inválido: '" + c + "'");
            }
        }
    }
}