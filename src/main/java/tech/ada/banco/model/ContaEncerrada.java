package tech.ada.banco.model;

public class ContaEncerrada implements SituacaoConta {
    @Override
    public void encerrar(Conta conta) {
        throw new RuntimeException("Conta já encerrada");
    }

    @Override
    public void bloquear(Conta conta) {
        throw new RuntimeException("Conta já encerrada não permite bloqueio");
    }

    @Override
    public void desbloquear(Conta conta) {
        throw new RuntimeException("Conta já encerrada não permite desbloqueio");
    }
}
