package tech.ada.banco.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tech.ada.banco.dto.ContaDTO;
import tech.ada.banco.dto.InvestimentoDTO;
import tech.ada.banco.exception.SaldoInsuficienteException;
import tech.ada.banco.exception.ValorInvalidoException;
import tech.ada.banco.model.ClientePF;
import tech.ada.banco.model.Conta;
import tech.ada.banco.model.ContaCorrente;
import tech.ada.banco.model.ContaInvestimento;
import tech.ada.banco.repository.ContaRepository;
import tech.ada.banco.service.consultaSaldo.ConsultaSaldo;
import tech.ada.banco.service.deposito.Deposito;
import tech.ada.banco.service.investimento.InvestirPFImpl;
import tech.ada.banco.service.saque.SaquePFImpl;
import tech.ada.banco.service.transferencia.TransferenciaPFImpl;
import tech.ada.banco.util.SimpleLogger;

@Service
@RequiredArgsConstructor
public class ContaInvestimentoPFService implements SaquePFImpl<ContaInvestimento>, ConsultaSaldo<ContaInvestimento>,
        Deposito<ContaInvestimento>, TransferenciaPFImpl<ContaInvestimento>, InvestirPFImpl {

    private SimpleLogger logger = SimpleLogger.getInstance();
    private final ContaRepository contaRepository;
    private final ModelMapper modelMapper;

    public ContaDTO investir(InvestimentoDTO contaDTO) throws SaldoInsuficienteException, ValorInvalidoException {
        logger.log("Investindo na conta " + contaDTO.getNumeroConta() + " o valor de " + contaDTO.getValorOperacao() + "...");

        var conta = contaRepository.findByUuid(contaDTO.getUuid()).orElseThrow();
        ContaInvestimento contaInvestimento = investir((ClientePF) conta.getCliente(), (ContaCorrente) conta, contaDTO.getValorOperacao());
        contaRepository.save(conta);
        contaRepository.save(contaInvestimento);
        return convertDto(conta);
    }

    private ContaDTO convertDto(Conta conta){
        return modelMapper.map(conta, ContaDTO.class);
    }


}
