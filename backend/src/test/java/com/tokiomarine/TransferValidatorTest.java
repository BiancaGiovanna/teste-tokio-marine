package com.tokiomarine;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tokiomarine.model.Transfer;

public class TransferValidatorTest {

    private TransferValidator transferValidator;

    @BeforeEach
    public void setUp() {
        transferValidator = new TransferValidator();
    }

    @Test
    public void testValidTransfer() {
        Transfer transfer = createValidTransfer();
        assertDoesNotThrow(() -> transferValidator.validateTransferForScheduling(transfer));
    }

    @Test
    public void testInvalidTransferWithSameAccount() {
        Transfer transfer = createValidTransfer();
        transfer.setDestinationAccount(transfer.getOriginAccount()); // Same account
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transferValidator.validateTransferForScheduling(transfer));
        assertEquals("Os números das contas de origem e de destino não podem ser iguais.", exception.getMessage());
    }

    @Test
    public void testInvalidTransferWithInvalidAmount() {
        Transfer transfer = createValidTransfer();
        transfer.setTransferAmount(BigDecimal.valueOf(-5)); // Invalid amount
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transferValidator.validateTransferForScheduling(transfer));
        assertEquals("O valor da transferência deve ser maior que zero.", exception.getMessage());
    }

    @Test
    public void testInvalidTransferWithInvalidDate() {
        Transfer transfer = createValidTransfer();
        transfer.setTransferDate(LocalDate.now().plusDays(51)); // Invalid date
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transferValidator.validateTransferForScheduling(transfer));
        assertEquals("Data de transferência inválida. Deve ser entre hoje e 50 dias a partir de hoje.",
                exception.getMessage());
    }

    @Test
    public void testInvalidTransferWithInvalidAmountForSameDay() {
        Transfer transfer = createValidTransfer();
        transfer.setTransferAmount(BigDecimal.valueOf(5)); // Invalid amount for the same day
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transferValidator.validateTransferForScheduling(transfer));
        assertEquals("Valor de transferência inválido. O valor mínimo para o prazo selecionado é: R$10",
                exception.getMessage());
    }

    @Test
    public void testInvalidTransferWithInvalidAmountFor1to10Days() {
        Transfer transfer = createValidTransfer();
        transfer.setTransferDate(LocalDate.now().plusDays(5)); // 5 days difference
        transfer.setTransferAmount(BigDecimal.valueOf(15)); // Invalid amount for 1 to 10 days
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transferValidator.validateTransferForScheduling(transfer));
        assertEquals("Valor de transferência inválido. O valor mínimo para o prazo selecionado é: R$20",
                exception.getMessage());
    }

    private Transfer createValidTransfer() {
        Transfer transfer = new Transfer();
        transfer.setOriginAccount("1234567890");
        transfer.setDestinationAccount("0987654321");
        transfer.setTransferAmount(BigDecimal.valueOf(100));
        transfer.setTransferDate(LocalDate.now().plusDays(10));
        return transfer;
    }
}

