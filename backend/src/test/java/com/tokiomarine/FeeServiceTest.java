package com.tokiomarine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tokiomarine.model.Transfer;
import com.tokiomarine.repository.TransferRepository;
import com.tokiomarine.service.FeeService;

@ExtendWith(MockitoExtension.class)
public class FeeServiceTest {

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private TransferValidator transferValidator;

    @InjectMocks
    private FeeService feeService;

    @Test
    public void testCalculateTransferRate() {
        // Configuração de dados de teste
        int days = 5;
        BigDecimal transferAmount = new BigDecimal("100.00");

        // Executar o método a ser testado
        BigDecimal result = feeService.calculateTransferRate(days, transferAmount);

        // Verificações
        assertEquals(new BigDecimal("5.50"), result); // 2.5% de R$100 + R$3.00
    }

    @Test
    public void testScheduleTransfer_Success() {
        // Configuração de dados de teste
        Transfer transfer = new Transfer();

        // Configuração de mocks
        doNothing().when(transferValidator).validateTransferForScheduling(transfer);

        // Executar o método a ser testado
        feeService.scheduleTransfer(transfer);

        // Verificações
        verify(transferValidator, times(1)).validateTransferForScheduling(transfer);
        verify(transferRepository, times(1)).save(transfer);
    }

    @Test
    public void testScheduleTransfer_ValidationFailure() {
        // Configuração de dados de teste
        Transfer transfer = new Transfer();

        // Configuração de mocks para simular falha de validação
        doThrow(new IllegalArgumentException("Erro de validação")).when(transferValidator).validateTransferForScheduling(transfer);

        // Executar o método a ser testado
        assertThrows(RuntimeException.class, () -> feeService.scheduleTransfer(transfer));

        // Verificações
        verify(transferValidator, times(1)).validateTransferForScheduling(transfer);
        verify(transferRepository, never()).save(transfer);
    }
}
