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
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = false)
@Document(collection = "merchant")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Merchant extends BaseModel{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;
    @Column(name = "merchant_no")
    @Indexed(unique = true)
    private String merchantNo;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "tel")
    private String tel;
}
