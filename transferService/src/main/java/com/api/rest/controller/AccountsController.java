package com.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.rest.model.Account;
import com.api.rest.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/accounts")
@Api(tags = { "Accounts Controller" }, description = "Provide APIs for account related operation")
public class AccountsController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/{accountId}/balances")
	@ApiOperation(value = "Get account balance by id", response = Account.class, produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid ID supplied"),
							@ApiResponse(code = 404, message = "Account not found with ID")})
	public Account getBalance(
			@ApiParam(value = "ID related to the account", required = true) @PathVariable Long accountId) {
		return accountService.retrieveBalances(accountId);
	}
}
