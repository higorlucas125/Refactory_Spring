package br.com.alura.adopet.api.exception;

public class CadastroException extends RuntimeException {
    public CadastroException(String message) {
        super(message);
    }
}
