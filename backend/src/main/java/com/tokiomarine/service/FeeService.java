package com.tokiomarine.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokiomarine.TransferValidator;
import com.tokiomarine.model.Transfer;
import com.tokiomarine.repository.TransferRepository;

@Service
public class FeeService {
	public FeeService() {
	}

	public FeeService(TransferRepository transferRepository) {

	}

	@Autowired
	private TransferRepository transferRepository;

	@Autowired
	private TransferValidator transferValidator;

	public BigDecimal calculateTransferRate(int dias, BigDecimal valorTransferencia) {
		if (dias >= 0) {
			BigDecimal taxa = new BigDecimal("0.025"); // 2.5%
			BigDecimal taxaFixa = new BigDecimal("3.00"); // R$3.00
			return valorTransferencia.multiply(taxa).add(taxaFixa);
		} else if (dias <= 10) {
			return new BigDecimal("12.00");
		} else if (dias <= 20) {
			return valorTransferencia.multiply(new BigDecimal("0.082")); // 8.2%
		} else if (dias <= 30) {
			return valorTransferencia.multiply(new BigDecimal("0.069")); // 6.9%
		} else if (dias <= 40) {
			return valorTransferencia.multiply(new BigDecimal("0.047")); // 4.7%
		} else if (dias <= 50) {
			return valorTransferencia.multiply(new BigDecimal("0.017")); // 1.7%
		} else {
			throw new IllegalArgumentException("Taxa não aplicável para a data de transferência.");
		}
	}

	public void scheduleTransfer(Transfer transfer) {
		try {
			transferValidator.validateTransferForScheduling(transfer);

			transferRepository.save(transfer);
		} catch (Exception error) {
			error.printStackTrace();
			throw new RuntimeException("Erro ao agendar a transfêrencia.");
		}
	}

	public void setTransferValidator(TransferValidator transferValidator) {
		
	}
}
