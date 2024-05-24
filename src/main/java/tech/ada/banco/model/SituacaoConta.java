package tech.ada.banco.model;


public interface SituacaoConta {
    void encerrar(Conta conta);
    void bloquear(Conta conta);
    void desbloquear(Conta conta);
}
