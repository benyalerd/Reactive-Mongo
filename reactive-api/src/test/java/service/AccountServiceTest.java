package service;

import com.example.api.service.AccountService;
import com.example.core.dto.request.AccountRequest;
import com.example.core.model.Account;
import com.example.core.repository.AccountRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void success() {
        when(accountRepository.findAll())
                .thenReturn(Flux.just(mockAccount()));
        StepVerifier.create( accountService.findAll(new AccountRequest()))
                .expectNextMatches(r -> {
                    assertEquals("1", r.get(0).getId());
                    return true;
                }).verifyComplete();

    }
        Account mockAccount(){
        return Account.builder().id("1").build();
    }

}
