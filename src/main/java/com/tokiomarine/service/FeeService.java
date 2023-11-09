package com.tokiomarine.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class FeeService {

	private static final Map<Integer, BigDecimal> RATE_PER_INTERVAL = new HashMap<>();

	static {
		RATE_PER_INTERVAL.put(0, new BigDecimal("3.00"));
		RATE_PER_INTERVAL.put(10, new BigDecimal("12.00"));
		RATE_PER_INTERVAL.put(20, new BigDecimal("0.082"));
		RATE_PER_INTERVAL.put(30, new BigDecimal("0.069"));
		RATE_PER_INTERVAL.put(40, new BigDecimal("0.017"));
	}

	public BigDecimal calcularTaxaTransferencia(int dias, BigDecimal valorTransferencia) {
		for (Map.Entry<Integer, BigDecimal> entry : RATE_PER_INTERVAL.entrySet()) {
			if (dias <= entry.getKey()) {
				if (entry.getValue().compareTo(BigDecimal.ONE) < 0) {
					return valorTransferencia.multiply(entry.getValue());
				} else {
					return entry.getValue();
				}
			}
		}

		throw new IllegalArgumentException("Taxa não aplicável para a data de transferência.");
	}
}
