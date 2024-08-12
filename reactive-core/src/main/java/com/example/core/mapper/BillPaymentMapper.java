package com.example.core.mapper;

import com.example.core.config.MapStructConfig;
import com.example.core.dto.request.InsertBillPaymentRequest;
import com.example.core.dto.response.LatePaymentResponse;
import com.example.core.model.BillPayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface BillPaymentMapper {
    BillPaymentMapper MAPPER = Mappers.getMapper(BillPaymentMapper.class);

    BillPayment mapInsertBillPaymentToBillPayment(InsertBillPaymentRequest request);
    @Mapping(source = "merchant.merchantNo",target = "merchantNo")
    @Mapping(source = "merchant.firstname",target = "firstname")
    @Mapping(source = "merchant.lastname",target = "lastname")
    @Mapping(source = "merchant.email",target = "email")
    @Mapping(source = "merchant.tel",target = "tel")
    LatePaymentResponse mapBillPaymentToLatePayment(BillPayment request);

}
