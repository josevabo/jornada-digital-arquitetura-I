package tech.ada.banco.model;

public class ContaBloqueada implements SituacaoConta {
    @Override
    public void encerrar(Conta conta) {
        conta.setSituacao(new ContaEncerrada());
    }

    @Override
    public void bloquear(Conta conta) {
        throw new RuntimeException("Conta já bloqueada");
    }

    @Override
    public void desbloquear(Conta conta) {
        conta.setSituacao(new ContaAtiva());
    }
}
