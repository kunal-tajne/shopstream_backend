package com.shopstream.service;

import com.shopstream.exception.UserException;
import com.shopstream.model.Address;

public interface AddressService {

    public Address findByAddressId(Long addressId) throws UserException;

}
