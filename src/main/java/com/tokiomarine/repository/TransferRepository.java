package com.tokiomarine.repository;

import com.tokiomarine.model.Transfer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
	@SuppressWarnings("unchecked")
	Transfer save(Transfer transfer) throws RuntimeException;

	List<Transfer> findAll() throws RuntimeException;
}
