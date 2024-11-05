package service;

import com.example.api.service.BillPaymentService;
import com.example.core.dto.request.InsertBillPaymentRequest;
import com.example.core.dto.request.PaymentStatus;
import com.example.core.dto.request.UpdatePaymentRequest;
import com.example.core.exception.BusinessValidationException;
import com.example.core.model.BillPayment;
import com.example.core.model.Merchant;
import com.example.core.repository.BillPaymentRepository;
import com.example.core.repository.MerchantRepository;
import com.example.kafka.MessageProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BillPaymentServiceTest {

    @InjectMocks
    private BillPaymentService billPaymentService;
    @Mock
    private BillPaymentRepository billPaymentRepository;
    @Mock
    private MerchantRepository merchantRepository;
    @Mock
    private ReactiveMongoTemplate template;
    @Mock
    private MessageProducer messageProducer;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private Validator validator;

    @Test
    void insertBillPayment_success() {

        when(merchantRepository.findByMerchantNo(any()))
                .thenReturn(Mono.just(Merchant.builder().build()));
        when(billPaymentRepository.save(any()))
                .thenReturn(Mono.just(BillPayment.builder().id("1234").build()));

        StepVerifier.create(billPaymentService.insertBillPayment(mockInsertBillPaymentRequest()))
                .expectNextMatches(r -> {
                    assertEquals("1234", r.getId());
                    return true;
                }).verifyComplete();

    }

    @Test
    void insertBillPayment_merchant_not_exits() {

        when(merchantRepository.findByMerchantNo(any()))
                .thenReturn(Mono.empty());

        StepVerifier.create(billPaymentService.insertBillPayment(mockInsertBillPaymentRequest()))
                .expectErrorMatches(throwable -> throwable instanceof BusinessValidationException &&
                        throwable.getMessage().equals("merchant not exits.")
                ).verify();
    }

    @Test
    void getLatePayment_success() {
        when(template.find(any(), any())).thenReturn(Flux.just(BillPayment.builder()
                .id("1234").dueDate(LocalDate.now()).amount(1000.0)
                .merchant(Merchant.builder().id("1234").merchantNo("3456").email("test@email.com").firstname("test").lastname("jaa").tel("0998765433").build())
                .ref1("1234").ref2("4444").status(PaymentStatus.COMPLETED)
                .build()));

        StepVerifier.create(billPaymentService.getLatePayment())
                .expectNextMatches(r -> {
                    val item = r.get(0);
                    assertEquals("test@email.com", item.getEmail());
                    assertEquals("3456",item.getMerchantNo());
                    assertEquals("0998765433",item.getTel());
                    assertEquals(1000.0,item.getAmount());
                    assertEquals("jaa",item.getLastname());
                    assertEquals("test",item.getFirstname());
                    return true;
                }).verifyComplete();

    }

    @Test
    void updatePaymentStatus_success() {
        when(billPaymentRepository.findByRef1AndRef2(any(), any())).thenReturn(Mono.just(BillPayment.builder()
                .ref1("1234").ref2("2345")
                .status(PaymentStatus.AWAITING_PAYMENT).amount(1000.0)
                .merchant(Merchant.builder().id("1234").merchantNo("00001").email("test@email.com").firstname("test").lastname("jaa").tel("0998765433").build())
                .build()));

        when(billPaymentRepository.save(any())).thenReturn(Mono.just(BillPayment.builder()
                .ref1("1234").ref2("2345")
                .status(PaymentStatus.COMPLETED).amount(1000.0)
                .merchant(Merchant.builder().id("1234").merchantNo("00001").email("test@email.com").firstname("test").lastname("jaa").tel("0998765433").build())
                .build()));

        when(template.find(any(), any())).thenReturn(Flux.just(BillPayment.builder()
                .id("1234").dueDate(LocalDate.now()).amount(1000.0)
                .merchant(Merchant.builder().id("1234").merchantNo("3456").email("test@email.com").firstname("test").lastname("jaa").tel("0998765433").build())
                .ref1("1234").ref2("4444").status(PaymentStatus.COMPLETED)
                .build()));

        StepVerifier.create(billPaymentService.updatePaymentStatus(mockUpdateBillPaymentRequest()))
                .expectNextMatches(r -> {

                    return true;
                }).verifyComplete();

    }
    private InsertBillPaymentRequest mockInsertBillPaymentRequest(){
        val request = new InsertBillPaymentRequest();
        request.setAmount(1000.0);
        request.setStatus(PaymentStatus.AWAITING_PAYMENT);
        request.setMerchantNo("123456");
        request.setRef1("12345");
        request.setRef2("67890");
        request.setDueDate(LocalDateTime.now());
        request.setCreatedBy("System");
        return request;
    }

    private UpdatePaymentRequest mockUpdateBillPaymentRequest(){
        val request = new UpdatePaymentRequest();
        val item1 = new UpdatePaymentRequest.PaymentDetail();

        item1.setRef1("1234");
        item1.setRef2("2345");
        item1.setMerchantNo("00001");

        request.setPaymentDetailList(List.of(item1));
        request.setUpdatedBy("System");

        return request;
    }

}
