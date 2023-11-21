package com.tokiomarine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tokiomarine.controller.TransferController;
import com.tokiomarine.model.Transfer;
import com.tokiomarine.service.FeeService;

public class TransferControllerTest {

	@SuppressWarnings("null")
	@Test
	public void testScheduleTransfer_Success() {
		// Mocks
		TransferValidator transferValidatorMock = mock(TransferValidator.class);
		FeeService feeServiceMock = mock(FeeService.class);

		// Configuração de mocks
		Transfer transfer = new Transfer();
		when(transferValidatorMock.createSuccessResponse())
				.thenReturn(Collections.singletonMap("message", "Transferência agendada com sucesso!"));
		when(feeServiceMock.calculateTransferRate(any(Integer.class), any(BigDecimal.class)))
				.thenReturn(BigDecimal.TEN);

		// Controller
		TransferController transferController = new TransferController();

		// Executar o método
		ResponseEntity<Map<String, String>> response = transferController.scheduleTransfer(transfer);

		// Verificações
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().containsKey("message"));
		assertEquals("Transferência agendada com sucesso!", response.getBody().get("message"));

		// Verificações de chamadas de métodos em mocks
		verify(transferValidatorMock, times(1)).validateTransferForScheduling(transfer);
		verify(feeServiceMock, times(1)).calculateTransferRate(any(Integer.class), any(BigDecimal.class));
	}

	@SuppressWarnings("null")
	@Test
	public void testScheduleTransfer_ValidationFailure() {
		// Mocks
		TransferValidator transferValidatorMock = mock(TransferValidator.class);
		FeeService feeServiceMock = mock(FeeService.class);

		// Configuração de mocks para simular falha de validação
		Transfer transfer = new Transfer();
		doThrow(new IllegalArgumentException("Erro de validação")).when(transferValidatorMock)
				.validateTransferForScheduling(transfer);

		// Controller
		TransferController transferController = new TransferController();

		// Executar o método
		ResponseEntity<Map<String, String>> response = transferController.scheduleTransfer(transfer);

		// Verificações
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().containsKey("error"));
		assertEquals("Erro de validação", response.getBody().get("error"));

		// Verificações de chamadas de métodos em mocks
		verify(transferValidatorMock, times(1)).validateTransferForScheduling(transfer);
		verify(feeServiceMock, never()).calculateTransferRate(any(Integer.class), any(BigDecimal.class));
	}

	@SuppressWarnings({ "null" })
	@Test
	public void testScheduleTransfer_InternalServerError() {
		// Mocks
		TransferValidator transferValidatorMock = mock(TransferValidator.class);
		FeeService feeServiceMock = mock(FeeService.class);

		// Configuração de mocks para simular uma exceção interna
		Transfer transfer = new Transfer();
		when(transferValidatorMock.createErrorResponse(any(String.class)))
				.thenReturn(Collections.singletonMap("error", "Erro interno"));
		doThrow(new RuntimeException("Exceção interna")).when(feeServiceMock).calculateTransferRate(any(Integer.class),
				any(BigDecimal.class));

		// Controller
		TransferController transferController = new TransferController();

		// Executar o método
		ResponseEntity<Map<String, String>> response = transferController.scheduleTransfer(transfer);

		// Verificações
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().containsKey("error"));
		assertEquals("Erro interno", response.getBody().get("error"));

		// Verificações de chamadas de métodos em mocks
		verify(transferValidatorMock, times(1)).validateTransferForScheduling(transfer);
		verify(feeServiceMock, times(1)).calculateTransferRate(any(Integer.class), any(BigDecimal.class));
	}

	// Adicione mais testes conforme necessário
}
