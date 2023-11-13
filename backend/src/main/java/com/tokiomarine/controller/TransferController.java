package com.tokiomarine.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/schedule")
	public ResponseEntity<String> scheduleTransfer(@RequestBody Transfer transfer) {
		try {
			if (transfer.getTransferDate() != null) {
				LocalDate currentDate = LocalDate.now();
				LocalDate transferDate = transfer.getTransferDate();

				if (transferDate.isBefore(currentDate)) {
					return ResponseEntity.badRequest().body("Transfer date cannot be earlier than today.");
				}
				if (transfer.getOriginAccount().equals(transfer.getDestinationAccount())) {
					return ResponseEntity.badRequest().body("Source and target account numbers cannot be the same.");
				}
				
				transfer.setSchedulingDate(currentDate);
				long daysDifference = ChronoUnit.DAYS.between(transfer.getSchedulingDate(), transferDate);

				BigDecimal fee = feeService.calculateTransferRate((int) daysDifference, transfer.getTransferAmount());
				transfer.setFee(fee);

				feeService.scheduleTransfer(transfer);
				return ResponseEntity.ok("Transfer scheduled successfully!");
			} else {
				return ResponseEntity.badRequest().body("Transfer date or scheduling date is null.");
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Internal error scheduling the transfer.");
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
