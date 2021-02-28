package com.hamzatugrul.surl.service.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class Murmur3With32BitsHashKeyGeneratorTest {

    private static final String TEST_URL = "https://www.turkcell.com.tr/paket-ve-tarifeler/4-5-g-hizinda/yildiz-8?order=2&source=CMS";
    private static final String TEST_URL_FOR_LENGHT = "https://www.turkcell.com";

    private Murmur3With32BitsHashKeyGenerator murmur3With32BitsHashKeyGeneratorUnderTest;

    @Before
    public void setUp() {
        //Arrange
        murmur3With32BitsHashKeyGeneratorUnderTest = new Murmur3With32BitsHashKeyGenerator();
    }

    @Test
    public void GivenSameText_shouldGenerateSameUniqueKey() {
        //Act
        final String result = murmur3With32BitsHashKeyGeneratorUnderTest.generateKey(TEST_URL);

        // Assert
        assertEquals("a5862b22", result);
    }

    @Test
    public void givenDifferentLenghtOfString_shouldGenerate8CharacterLenghtOfKey() {
        //Act
        final String result = murmur3With32BitsHashKeyGeneratorUnderTest.generateKey(TEST_URL_FOR_LENGHT);

        // Assert 7b4a8124
        assertEquals("7b4a8124", result);
        assertEquals(8, result.length());
    }
}
