package com.example.core.mapper;

import com.example.core.config.MapStructConfig;
import com.example.core.dto.response.AccountResponse;
import com.example.core.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface AccountMapper {
    AccountMapper MAPPER = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "valueAccount", source = "value")
    AccountResponse map(Account account);


}
