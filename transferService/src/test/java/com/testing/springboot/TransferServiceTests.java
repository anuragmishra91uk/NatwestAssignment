package com.testing.springboot;

/*Work on this test class was in progress*/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.api.rest.dao.AccountRepository;
import com.api.rest.exception.AccountNotExistException;
import com.api.rest.exception.OverDraftException;
import com.api.rest.exception.SystemException;
import com.api.rest.model.Account;
import com.api.rest.model.TransferRequest;
import com.api.rest.service.AccountService;


@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTests {

	@Mock
	AccountRepository accRepo;
	
	@InjectMocks
	AccountService accService;
	
	@Test
	public void testRetrieveBalance() {
		when(accRepo.findByAccountId(1L)).thenReturn(Optional.of(new Account(1L, BigDecimal.ONE)));
		
		assertEquals(BigDecimal.ONE, accService.retrieveBalances(1L).getBalance());
	}
	
	@Test
	public void testRetrieveBalanceFromInvalidAccount() {
		when(accRepo.findByAccountId(1L)).thenReturn(Optional.empty());
		
		accService.retrieveBalances(1L);
	}
	
	@Test
	public void testTransferBalance() throws Exception, Exception, Exception {
		Long accountFromId = 1L;
		Long accountFromTo = 2L;
		BigDecimal amount = new BigDecimal(10);
		
		TransferRequest request = new TransferRequest();
		request.setAccountFromId(accountFromId);
		request.setAccountToId(accountFromTo);
		request.setAmount(amount);
		
		Account accFrom = new Account(accountFromId, BigDecimal.TEN);
		Account accTo = new Account(accountFromId, BigDecimal.TEN);
		
		when(accRepo.getAccountForUpdate(accountFromId)).thenReturn(Optional.of(accFrom));
		when(accRepo.getAccountForUpdate(accountFromTo)).thenReturn(Optional.of(accTo));
		
		accService.transferBalances(request);
		
		assertEquals(BigDecimal.ZERO, accFrom.getBalance());
		assertEquals(BigDecimal.TEN.add(BigDecimal.TEN), accTo.getBalance());
	}
	
	@Test
	public void testOverdraftBalance() throws OverDraftException, AccountNotExistException, SystemException {
		Long accountFromId = 1L;
		Long accountFromTo = 2L;
		BigDecimal amount = new BigDecimal(20);
		
		TransferRequest request = new TransferRequest();
		request.setAccountFromId(accountFromId);
		request.setAccountToId(accountFromTo);
		request.setAmount(amount);
		
		Account accFrom = new Account(accountFromId, BigDecimal.TEN);
		Account accTo = new Account(accountFromId, BigDecimal.TEN);
		
		when(accRepo.getAccountForUpdate(accountFromId)).thenReturn(Optional.of(accFrom));
		when(accRepo.getAccountForUpdate(accountFromTo)).thenReturn(Optional.of(accTo));
		
		accService.transferBalances(request);
	}
}
