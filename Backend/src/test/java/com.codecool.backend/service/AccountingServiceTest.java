package com.codecool.backend.service;

import com.codecool.backend.model.entity.Closer;
import com.codecool.backend.model.entity.Household;
import com.codecool.backend.model.entity.Transaction;
import com.codecool.backend.repository.CloserRepository;
import com.codecool.backend.repository.HouseholdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AccountingServiceTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private CloserRepository closerRepository;

    @Mock
    private HouseholdRepository householdRepository;

    @InjectMocks
    private AccountingService accountingService;

    private Household household;
    private Closer lastCloser;
    private Transaction transaction1;
    private Transaction transaction2;
    private LocalDate balanceDate;
    private LocalDate closerDate;

    @BeforeEach
    void setUp() {
        household = new Household();
        household.setId(1L);

        closerDate = LocalDate.of(2023, 1, 1);

        lastCloser = new Closer();
        lastCloser.setId(1L);
        lastCloser.setHousehold(household);
        lastCloser.setDate(closerDate);
        lastCloser.setAmount(new BigDecimal("1000.00"));

        transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setAmount(new BigDecimal("100.00"));

        transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(new BigDecimal("200.00"));

        balanceDate = LocalDate.of(2023, 1, 31);
    }

    @DisplayName("JUnit test for AccountingService - getBalance()")
    @Test
    void givenHouseholdIdAndDate_whenGetBalance_thenReturnCorrectBalance() {
        // GIVEN
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        given(closerRepository.findFirstByHouseholdIdAndDateLessThanEqualOrderByDateDesc(
                1L, balanceDate)).willReturn(lastCloser);

        given(transactionService.getTransactionsBetweenDates(
                1L, closerDate, balanceDate)).willReturn(transactions);

        // WHEN
        BigDecimal balance = accountingService.getBalance(1L, balanceDate);

        // THEN
        assertThat(balance).isEqualTo(new BigDecimal("1300.00")); // 1000 + 100 + 200

        // Verify repository interactions
        verify(closerRepository, times(1))
                .findFirstByHouseholdIdAndDateLessThanEqualOrderByDateDesc(1L, balanceDate);
        verify(transactionService, times(1))
                .getTransactionsBetweenDates(1L, closerDate, balanceDate);
    }

    @DisplayName("JUnit test for AccountingService - getBalance() with no previous closer")
    @Test
    void givenHouseholdIdAndDate_whenGetBalanceWithNoCloser_thenReturnTransactionSum() {
        // GIVEN
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        given(closerRepository.findFirstByHouseholdIdAndDateLessThanEqualOrderByDateDesc(
                1L, balanceDate)).willReturn(null);

        // WHEN
        BigDecimal balance = accountingService.getBalance(1L, balanceDate);

        // THEN
        assertThat(balance).isEqualTo(BigDecimal.ZERO); // No closer found, start from 0

        // Verify repository interaction
        verify(closerRepository, times(1))
                .findFirstByHouseholdIdAndDateLessThanEqualOrderByDateDesc(1L, balanceDate);
        // No transactions should be fetched if there's no closer
        verify(transactionService, never()).getTransactionsBetweenDates(any(), any(), any());
    }

    @DisplayName("JUnit test for AccountingService - createCloser()")
    @Test
    void givenHouseholdIdAndDate_whenCreateCloser_thenReturnNewCloser() {
        // GIVEN
        given(householdRepository.findById(1L)).willReturn(Optional.of(household));

        // Mock the dependencies that getBalance() would call internally
        given(closerRepository.findFirstByHouseholdIdAndDateLessThanEqualOrderByDateDesc(
                1L, balanceDate)).willReturn(lastCloser);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        given(transactionService.getTransactionsBetweenDates(
                1L, closerDate, balanceDate)).willReturn(transactions);

        // Setup the repository to return our expected closer when saved
        Closer newCloser = new Closer(household, balanceDate, new BigDecimal("1300.00"));
        given(closerRepository.save(any(Closer.class))).willReturn(newCloser);

        // WHEN
        Closer result = accountingService.createCloser(1L, balanceDate);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualTo(new BigDecimal("1300.00"));
        assertThat(result.getDate()).isEqualTo(balanceDate);
        assertThat(result.getHousehold()).isEqualTo(household);

        // Verify repository interactions
        verify(householdRepository, times(1)).findById(1L);
        verify(closerRepository, times(1)).save(any(Closer.class));
    }
}
