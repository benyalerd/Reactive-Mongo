package com.example.core.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "bill_payment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillPayment extends BaseModel{
    @Id
    private Long id;
    @Column(name = "merchant")
    private Merchant merchant;
    @Column(name = "ref1")
    private String ref1;
    @Column(name = "ref2")
    private String ref2;
    @Column(name = "due_date")
    private String dueDate;
    @Column(name = "status")
    private String status;
    @Column(name = "amount")
    private Double amount;
}
