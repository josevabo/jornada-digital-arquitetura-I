package tech.ada.banco.enums;

import tech.ada.banco.model.SituacaoConta;

public enum SituacaoContaEnum  {
    ATIVA(1), BLOQUEADA(2), ENCERRADA(3);

    private final Integer id;

    SituacaoContaEnum(int i) {
        id = i;
    }
}
