package com.hamzatugrul.surl.service.impl;

import com.google.common.hash.Hashing;
import com.hamzatugrul.surl.service.KeyGenerator;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/28/2021
 */

@Service
public class Murmur3With32BitsHashKeyGenerator implements KeyGenerator {
    @Override
    public String generateKey(String text) {
        //TODO: This could be improved
        return Hashing.murmur3_32().hashString(text, StandardCharsets.UTF_8).toString();
    }
}
