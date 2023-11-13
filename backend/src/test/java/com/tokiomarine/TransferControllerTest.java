package com.tokiomarine;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tokiomarine.controller.TransferController;
import com.tokiomarine.model.Transfer;
import com.tokiomarine.repository.TransferRepository;
import com.tokiomarine.service.FeeService;

public class TransferControllerTest {

    @Mock
    private FeeService feeService;

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferController transferController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testScheduleTransfer_Success() {
        when(feeService.calculateTransferRate(any(Integer.class), any(BigDecimal.class)))
            .thenReturn(new BigDecimal("5.00"));

        when(transferRepository.save(any(Transfer.class))).thenReturn(new Transfer());

        Transfer transfer = new Transfer();
        transfer.setTransferDate(LocalDate.now().plusDays(1));
        transfer.setSchedulingDate(LocalDate.now());
        transfer.setTransferAmount(new BigDecimal("100.00"));
        transfer.setOriginAccount("1234567890");
        transfer.setDestinationAccount("0987654321");

        ResponseEntity<String> response = transferController.scheduleTransfer(transfer);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Transfer scheduled successfully!");

        // Verifica se o método save foi chamado no transferRepository        verify(transferRepository).save(any(Transfer.class));
    }

    @Test
    public void testScheduleTransfer_InvalidDates() {
        Transfer transfer = new Transfer();
        transfer.setTransferDate(LocalDate.of(2023, 11, 10));
        transfer.setSchedulingDate(LocalDate.of(2023, 11, 5));

        ResponseEntity<String> responseEntity = transferController.scheduleTransfer(transfer);

        // Verifica se o método save não foi chamado no transferRepository
        verify(transferRepository, times(0)).save(any(Transfer.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Transfer date cannot be earlier than today.", responseEntity.getBody());
    }

    @Test
    public void testScheduleTransfer_SameAccounts() {
        Transfer transfer = new Transfer();
        transfer.setTransferDate(LocalDate.now().plusDays(1));
        transfer.setSchedulingDate(LocalDate.now());
        transfer.setTransferAmount(new BigDecimal("100.00"));
        transfer.setOriginAccount("123");
        transfer.setDestinationAccount("123"); // Contas iguais

        ResponseEntity<String> responseEntity = transferController.scheduleTransfer(transfer);

        // Verifica se o método save não foi chamado no transferRepository
        verify(transferRepository, times(0)).save(any(Transfer.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Source and target account numbers cannot be the same.", responseEntity.getBody());
    }

    @Test
    public void testGetAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        transfers.add(new Transfer());
        transfers.add(new Transfer());

        // Configuração do Mock para transferRepository.findAll
        when(transferRepository.findAll()).thenReturn(transfers);

        ResponseEntity<List<Transfer>> response = transferController.getAllTransfers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(transfers);
    }
}
