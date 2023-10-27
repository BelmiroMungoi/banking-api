package com.bbm.banking.service.impl;

import com.bbm.banking.dto.request.AccountRequestDto;
import com.bbm.banking.model.Address;
import com.bbm.banking.repository.AddressRepository;
import com.bbm.banking.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address saveAddress(AccountRequestDto requestDto) {
        Address address = Address.builder()
                .province(requestDto.getProvince())
                .district(requestDto.getDistrict())
                .street(requestDto.getStreet())
                .houseNumber(requestDto.getHouseNumber())
                .zipCode(requestDto.getZipCode())
                .build();
        return addressRepository.save(address);
    }
}
