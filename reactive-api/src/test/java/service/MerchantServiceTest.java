package service;

import com.example.api.service.MerchantService;
import com.example.core.dto.request.InsertMerchantRequest;
import com.example.core.exception.BusinessValidationException;
import com.example.core.model.Merchant;
import com.example.core.repository.MerchantRepository;
import jakarta.validation.Validator;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MerchantServiceTest {
    @InjectMocks
    private MerchantService merchantService;
    @Mock
    private MerchantRepository merchantRepository;
    @Mock
    private Validator validator;

    @Test
    void insertMerchant_success() {

        when(merchantRepository.findByemail(any()))
                .thenReturn(Mono.empty());
        when(merchantRepository.save(any()))
                .thenReturn(Mono.just(Merchant.builder().id("1234").build()));

        StepVerifier.create(merchantService.insertMerchant(mockMerchantRequest()))
                .expectNextMatches(r -> {
                    assertEquals("1234", r.getId());
                    return true;
                }).verifyComplete();

    }

    @Test
    void insertMerchant_email_already_exits() {

        when(merchantRepository.findByemail(any()))
                .thenReturn(Mono.just(Merchant.builder().id("12345").build()));
        when(merchantRepository.save(any()))
                .thenReturn(Mono.just(Merchant.builder().id("1234").build()));

        StepVerifier.create(merchantService.insertMerchant(mockMerchantRequest()))
                .expectErrorMatches(throwable -> throwable instanceof BusinessValidationException &&
                        throwable.getMessage().equals("email is already exits.")
                ).verify();
    }
    private InsertMerchantRequest mockMerchantRequest(){
        val request = new InsertMerchantRequest();
        request.setEmail("test@email.com");
        request.setTel("9876543909");
        request.setLastname("Ben");
        request.setFirstname("Ya");
        request.setCreatedBy("System");
        return request;
    }
}
