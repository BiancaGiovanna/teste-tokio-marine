package com.tokiomarine.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokiomarine.model.Transfer;
import com.tokiomarine.repository.TransferRepository;

@Service
public class FeeService {
	public FeeService() {
	}

	private static final Map<Integer, BigDecimal> RATE_PER_INTERVAL = new HashMap<>();

	static {
		RATE_PER_INTERVAL.put(0, new BigDecimal("0.025")); // 2.5%
		RATE_PER_INTERVAL.put(10, BigDecimal.ZERO); // 0.0%
		RATE_PER_INTERVAL.put(20, new BigDecimal("0.082")); // 8.2%
		RATE_PER_INTERVAL.put(30, new BigDecimal("0.069")); // 6.9%
		RATE_PER_INTERVAL.put(40, new BigDecimal("0.047")); // 4.7%
		RATE_PER_INTERVAL.put(50, new BigDecimal("0.017")); // 1.7%
	}

	@Autowired
	private TransferRepository transferRepository;

	public FeeService(TransferRepository transferRepository) {
		this.transferRepository = transferRepository;
	}

	public BigDecimal calculateTransferRate(int dias, BigDecimal valorTransferencia) {
		for (Map.Entry<Integer, BigDecimal> entry : RATE_PER_INTERVAL.entrySet()) {
			if (dias <= entry.getKey()) {
				return valorTransferencia.multiply(entry.getValue());
			}
		}

		throw new IllegalArgumentException("Taxa não aplicável para a data de transferência.");
	}

	public void scheduleTransfer(Transfer transfer) {
		try {
			transferRepository.save(transfer);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao agendar a transferência.");
		}
	}
}
