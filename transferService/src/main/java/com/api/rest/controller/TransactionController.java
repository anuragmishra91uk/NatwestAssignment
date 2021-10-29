package com.api.rest.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.rest.dto.TransferResult;
import com.api.rest.exception.AccountNotExistException;
import com.api.rest.exception.CheckBalanceException;
import com.api.rest.exception.OverDraftException;
import com.api.rest.model.TransferRequest;
import com.api.rest.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/transaction")
@Api(tags = {"Transaction Controller"}, description = "Provide APIs for transaction related operation")
public class TransactionController {
	
	private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	private AccountService accountService;

	@PostMapping(consumes = { "application/json" })
	@ApiOperation(value = "API to create transaction", response = TransferResult.class, produces = "application/json")
	public ResponseEntity transferMoney(@RequestBody @Valid TransferRequest request) throws Exception {

		try {
			accountService.transferBalances(request);
			
			TransferResult result = new TransferResult();
			result.setAccountFromId(request.getAccountFromId());
			result.setBalanceAfterTransfer(accountService.checkBalance(request.getAccountFromId()));
			
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		} catch (AccountNotExistException | OverDraftException e) {
			log.error("Fail to transfer balances, please check with system administrator.");
			throw e;
		} 
	}
}
