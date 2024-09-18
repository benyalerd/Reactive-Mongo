package com.example.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Document(collection = "bill_payment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillPayment extends BaseModel{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;
    @Column(name = "merchant")
    private Merchant merchant;
    @Column(name = "ref1")
    private String ref1;
    @Column(name = "ref2")
    private String ref2;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "status")
    private String status;
    @Column(name = "amount")
    private Double amount;
}
