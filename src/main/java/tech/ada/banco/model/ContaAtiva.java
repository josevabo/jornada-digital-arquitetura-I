package tech.ada.banco.model;

public class ContaAtiva implements SituacaoConta {
    @Override
    public void encerrar(Conta conta) {
        conta.setSituacao(new ContaEncerrada());
    }

    @Override
    public void bloquear(Conta conta) {

        conta.setSituacao(new ContaBloqueada());
    }

    @Override
    public void desbloquear(Conta conta) {
        throw new RuntimeException("Só é possível desbloquear contas bloqueadas");
    }
}
