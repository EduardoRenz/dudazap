package br.com.eduardo.dudazap.model;

/**
 * Created by Eduardo on 13/12/2017.
 */

public class Mensagem {
    private String idUsuario,mensagem;

    public Mensagem() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
