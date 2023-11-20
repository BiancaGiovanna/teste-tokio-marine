package com.tokiomarine.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokiomarine.TransferValidator;
import com.tokiomarine.model.Transfer;
import com.tokiomarine.repository.TransferRepository;
import com.tokiomarine.service.FeeService;

@RestController
@RequestMapping("/transfers")
public class TransferController {

	@Autowired
	private FeeService feeService;

	@Autowired
	private TransferRepository transferRepository;

	@Autowired
	private TransferValidator transferValidator;

	@PostMapping("/schedule")
	public ResponseEntity<Map<String, String>> scheduleTransfer(@RequestBody Transfer transfer) {
		try {
			transferValidator.validateTransferForScheduling(transfer);

			transfer.setSchedulingDate(LocalDate.now());
			long daysDifference = ChronoUnit.DAYS.between(transfer.getSchedulingDate(), transfer.getTransferDate());

			BigDecimal fee = feeService.calculateTransferRate((int) daysDifference, transfer.getTransferAmount());
			transfer.setFee(fee);

			feeService.scheduleTransfer(transfer);
			return ResponseEntity.ok(transferValidator.createSuccessResponse());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(transferValidator.createErrorResponse(e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500)
					.body(transferValidator.createErrorResponse("Erro interno ao agendar a transferencia."));
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<Transfer>> getAllTransfers() {
		try {
			List<Transfer> transfers = transferRepository.findAll();
			return ResponseEntity.ok(transfers);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(null);
		}
	}
}
