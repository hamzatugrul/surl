package com.hamzatugrul.surl.service;

import com.hamzatugrul.surl.exception.KeyNotFoundException;
import com.hamzatugrul.surl.model.ShortenerDTO;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */

public interface UrlShortenerService {

    ShortenerDTO shortener(ShortenerDTO shortenerDTO);

    String resolve(String surl) throws KeyNotFoundException;
}
