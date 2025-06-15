package com.gbyzzz.linkedinjobsbotapi.security.telegram;

import com.gbyzzz.linkedinjobsbotapi.controller.payload.request.TelegramLoginRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class TelegramAuthService {

    @Value("${bot.token}")
    private String tgBotToken;

    public boolean isDataValid(TelegramLoginRequest telegramLoginRequest) {
        String hash = telegramLoginRequest.getHash();
        String dataCheckString = createDataCheckString(telegramLoginRequest);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] key = digest.digest(tgBotToken.getBytes(StandardCharsets.UTF_8));

            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
            hmac.init(secretKeySpec);

            byte[] hmacBytes = hmac.doFinal(dataCheckString.getBytes(StandardCharsets.UTF_8));
            StringBuilder validateHash = new StringBuilder();
            for (byte b : hmacBytes) {
                validateHash.append(String.format("%02x", b));
            }

            return hash.contentEquals(validateHash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
//            log.error("Error while authenticate: {}", e.getMessage());
            return false;
        }
    }

    private String createDataCheckString(TelegramLoginRequest telegramLoginRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append("auth_date=").append(telegramLoginRequest.getAuth_date()).append("\n");
        if(telegramLoginRequest.getFirst_name() != null) {
            sb.append("first_name=").append(telegramLoginRequest.getFirst_name()).append("\n");
        }
        sb.append("id=").append(telegramLoginRequest.getId()).append("\n");
        if(telegramLoginRequest.getPhoto_url() != null) {
            sb.append("photo_url=").append(telegramLoginRequest.getPhoto_url()).append("\n");
        }
        sb.append("username=").append(telegramLoginRequest.getUsername());
        return sb.toString();
    }
}
