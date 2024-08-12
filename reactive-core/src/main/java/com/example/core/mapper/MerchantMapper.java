package com.example.core.mapper;

import com.example.core.config.MapStructConfig;
import com.example.core.dto.request.InsertMerchantRequest;
import com.example.core.dto.response.AccountResponse;
import com.example.core.model.Account;
import com.example.core.model.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface MerchantMapper {
    MerchantMapper MAPPER = Mappers.getMapper(MerchantMapper.class);

    Merchant mapInsertMerchantToMerchant (InsertMerchantRequest request);

}
