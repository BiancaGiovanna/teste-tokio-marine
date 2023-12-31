package com.tokiomarine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tokiomarine.model.Transfer;

@Component
public class TransferValidator {

	public void validateTransferForScheduling(Transfer transfer) {
		validateTransferInput(transfer);

		if (transfer.getOriginAccount().length() != 10 || transfer.getDestinationAccount().length() != 10) {
			throw new IllegalArgumentException(
					"Os números das contas de origem e de destino devem ter exatamente 10 dígitos.");
		}
	}

	private void validateTransferInput(Transfer transfer) {
		LocalDate currentDate = LocalDate.now();

		if (transfer.getTransferAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
		}

		if (transfer.getOriginAccount().equals(transfer.getDestinationAccount())) {
			throw new IllegalArgumentException("Os números das contas de origem e de destino não podem ser iguais.");
		}

		if (transfer.getTransferDate().isBefore(currentDate)
				|| transfer.getTransferDate().isAfter(currentDate.plusDays(50))) {
			throw new IllegalArgumentException(
					"Data de transferência inválida. Deve ser entre hoje e 50 dias a partir de hoje.");
		}
		long daysDifference = ChronoUnit.DAYS.between(currentDate, transfer.getTransferDate());
		BigDecimal minTransferAmount = BigDecimal.ZERO;

		if (daysDifference == 0) {
			minTransferAmount = new BigDecimal("10");
		} else if (daysDifference >= 1 && daysDifference <= 10) {
			minTransferAmount = new BigDecimal("20");
		}

		if (transfer.getTransferAmount().compareTo(minTransferAmount) < 0) {
			throw new IllegalArgumentException(
					"Valor de transferência inválido. O valor mínimo para o prazo selecionado é: R$" + minTransferAmount);
		}
	}

	public Map<String, String> createSuccessResponse() {
		return Collections.singletonMap("message", "Transferência agendada com sucesso!");
	}

	public Map<String, String> createErrorResponse(String errorMessage) {
		return Collections.singletonMap("error", errorMessage);
	}
}
