package com.bbm.banking.service;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.model.Address;

public interface AddressService {

    Address saveAddress(AccountRequestDto requestDto);
}
