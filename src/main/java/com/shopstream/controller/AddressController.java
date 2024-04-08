package com.shopstream.controller;

import com.shopstream.model.Address;
import com.shopstream.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shopstream.exception.UserException;
import com.shopstream.service.AddressService;
import com.shopstream.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<Address> createAddressHandler(@RequestBody Address address,
                                                        @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Address createdAddress = addressService.findByAddressId(address.getId());
        return new ResponseEntity<>(createdAddress, HttpStatus.OK);
    }



}