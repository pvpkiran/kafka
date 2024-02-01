package com.example.kafka;

import com.google.common.hash.Hashing;

import static com.google.common.base.Charsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HmacTest {
    
   @Test
    public void testHmac() {
        String paload = "{\"entry\": [{\"id\": \"0\", \"time\": 1679057300, \"changes\": [{\"field\": \"in_process_ad_objects\", \"value\": {\"id\": \"111111111111\", \"level\": \"CREATIVE\", \"status_name\": \"Paused\"}}]}], \"object\": \"ad_account\"}";
        byte[] key = "4dc42f6f3de89e972c7681f95c5bcca6".getBytes();
        String givenSignature = "163671a7cfa6758c9f984601bdcf4d9d83736084c24ed9b94830b124dac3ff24";

        String calculatedSignature = Hashing.hmacSha256(key)
                .newHasher()
                .putString(paload, UTF_8)
                .hash()
                .toString();
        assertEquals(givenSignature, calculatedSignature);
    }
}