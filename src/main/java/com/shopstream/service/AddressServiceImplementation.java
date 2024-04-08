package com.shopstream.service;

import com.shopstream.exception.UserException;
import com.shopstream.model.Address;
import com.shopstream.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImplementation implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address findByAddressId(Long addressId) throws UserException {

        Optional<Address> opt = Optional.ofNullable(addressRepository.findByAddressId(addressId));

        if(opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("Address with id not found : "+addressId);
    }
}
