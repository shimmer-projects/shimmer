package io.github.shimmer.authority.filter;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import java.security.Key;

/**
 * <p>jwt token生成验证</p>
 * Created on 2024-02-05 15:17
 *
 * @author yu_haiyang
 */
public class TokenChecker {

    public static void main(String[] args) throws Exception {
        // 注意密钥长短（最少32个字符）
        HmacKey hmacKey = new HmacKey("eyJraWQiOm51bGwsImFsZyI6IkhTMjU2In0".getBytes());
        JsonWebSignature jsonWebSignature = jsonWebSignature(hmacKey, AlgorithmIdentifiers.HMAC_SHA256);
        String jwt = jsonWebSignature.getCompactSerialization();
        System.out.println(jwt);
        JwtConsumer jwtConsumer = jwtConsumer(hmacKey, AlgorithmIdentifiers.HMAC_SHA256);
        try {
            // 校验 JWT 并将其处理成 JwtClaims 对象
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
            System.out.println("JWT validation succeeded! JwtClaims: " + jwtClaims);
        } catch (InvalidJwtException e) {
            handleException(e);
        }
    }

    public void testRSA() throws JoseException {
        // 生成 RSA 密钥对（打包在 JsonWebKey 中），将用于签名和验签 JWT
        RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
        // 给 JsonWebKey 赋值一个密钥 ID（可选操作）
        rsaJsonWebKey.setKeyId("key1");
        JsonWebSignature jsonWebSignature = jsonWebSignature(rsaJsonWebKey.getPrivateKey(),
                rsaJsonWebKey.getKeyId(),
                AlgorithmIdentifiers.RSA_USING_SHA256);
        /*
         * 签名 JWS，生成 JWT 字符串
         * 如果想加密此字符串，则将此结果作为 JsonWebEncryption 对象的负载，并将 cty（Content Type）头设置为 jwt
         */
        String jwt = jsonWebSignature.getCompactSerialization();
        /*
         * 使用 JwtConsumerBuilder 构建一个合适的 JwtConsumer 对象，用于校验和处理 JWT。
         * 如果 JWT 已被加密，只需提供一个解密密钥或解密密钥解析器给 JwtConsumerBuilder。
         */
        JwtConsumer jwtConsumer = jwtConsumer(rsaJsonWebKey.getKey(), AlgorithmIdentifiers.RSA_USING_SHA256);
        try {
            // 校验 JWT 并将其处理成 JwtClaims 对象
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
            System.out.println("JWT validation succeeded! JwtClaims: " + jwtClaims);
        } catch (InvalidJwtException e) {
            handleException(e);
        }
    }

    private static JsonWebSignature jsonWebSignature(Key key, String algorithm) {
        return jsonWebSignature(key, null, algorithm);
    }

    /**
     * 一个 JWT 是一个携带 JwtClaims 作为负载的 JsonWebSignature 或 JsonWebEncryption 对象
     * 本例中的 JWT 是一个 JsonWebSignature 对象
     */
    private static JsonWebSignature jsonWebSignature(Key key, String kid, String algorithm) {
        JsonWebSignature jsonWebSignature = new JsonWebSignature();
        // 为 JsonWebSignature 对象添加负载：JwtClaims 对象的 Json 内容
        jsonWebSignature.setPayload(jwtClaims().toJson());
        // JWT 使用 RSA 私钥签名
        jsonWebSignature.setKey(key);
        // 可选操作
//        if (null != key) {
//            jsonWebSignature.setKeyIdHeaderValue(kid);
//        }
        // 在 JWT / JWS 上设置签名算法
        jsonWebSignature.setAlgorithmHeaderValue(algorithm);
        return jsonWebSignature;
    }

    /**
     * 创建 Claims，包装了 JWT 的内容
     */
    private static JwtClaims jwtClaims() {
        JwtClaims claims = new JwtClaims();
        // 设置 Token 的签发者
        claims.setIssuer("Issuer");
        // 设置过期时间
        // claims.setExpirationTime();
        // 设置过期时间为 10 分钟后
        claims.setExpirationTimeMinutesInTheFuture(10);
        claims.setSubject("Subject");
        // 设置 Token 将被发送给哪些对象
        claims.setAudience("Audience X", "Audience Y", "Audience Z");
        // claims.setNotBefore();
        // 设置生效时间为 2 分钟前
        claims.setNotBeforeMinutesInThePast(2);
        // claims.setIssuedAt();
        // 设置 Token 发布/创建 时间为当前时间
        claims.setIssuedAtToNow();
        // claims.setJwtId();
        // 为 JWT 设置一个自动生成的唯一 ID
        claims.setGeneratedJwtId();
        // 额外添加的生命属性
        claims.setClaim("email", "email@example.com");
        return claims;
    }

    /**
     * 使用 JwtConsumerBuilder 构建一个合适的 JwtConsumer 对象，用于校验和处理 JWT。
     * 如果 JWT 已被加密，只需提供一个解密密钥或解密密钥解析器给 JwtConsumerBuilder。
     */
    private static JwtConsumer jwtConsumer(Key key, String algorithm) {
        return new JwtConsumerBuilder()
                // 在验证时间时留出一些余量以解决时钟偏差问题
                .setAllowedClockSkewInSeconds(30)
                // 设置解密密钥
                // .setDecryptionKey()
                // 设置解密密钥解析器
                // .setDecryptionKeyResolver()
                // .setDisableRequireSignature()
                // 必须设置过期时间
                .setRequireExpirationTime()
                // 必须设置 Subject
                .setRequireSubject()
                // 必须设置 Token 签发者
                .setExpectedIssuer("Issuer")
                // 必须设置 Token 签发给谁
                .setExpectedAudience("Audience X")
                // 设置用于验证签名的公钥
                .setVerificationKey(key)
                // 设置允许的预期签名算法
                .setJwsAlgorithmConstraints(
                        AlgorithmConstraints.ConstraintType.PERMIT, algorithm)
                .build();
    }

    /**
     * 处理校验 JWT 并将其处理成 JwtClaims 对象过程中出现的异常
     */
    private static void handleException(InvalidJwtException e) {
        System.out.println("Invalid JWT:" + e);
        try {
            JwtClaims jwtClaims = e.getJwtContext().getJwtClaims();
            // 异常是否因 JWT 过期触发
            if (e.hasExpired()) {
                System.out.println("Expired at " + jwtClaims.getExpirationTime());
            }
            // 异常是否因 Audience 无效触发
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
                System.out.println("Invalid audience: " + jwtClaims.getAudience());
            }
            // 异常是否因缺少 Audience 触发
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_MISSING)) {
                System.out.println("Audience missing！");
            }
            // 异常是否因缺少加密触发
            if (e.hasErrorCode(ErrorCodes.ENCRYPTION_MISSING)) {
                System.out.println("Encryption missing！");
            }
            // 异常是否因缺少过期时间触发
            if (e.hasErrorCode(ErrorCodes.EXPIRATION_MISSING)) {
                System.out.println("Expiration missing!");
            }
            // 异常是否因过期时间太长触发
            if (e.hasErrorCode(ErrorCodes.EXPIRATION_TOO_FAR_IN_FUTURE)) {
                System.out.println("Expiration too far in future: " + jwtClaims.getExpirationTime());
            }
            // 异常是否因缺乏完整性触发
            if (e.hasErrorCode(ErrorCodes.INTEGRITY_MISSING)) {
                System.out.println("Integrity missing!");
            }
            // 异常是否因发布时间无效触发
            if (e.hasErrorCode(ErrorCodes.ISSUED_AT_INVALID_FUTURE)) {
                System.out.println("Issued at invalid future: " + jwtClaims.getIssuedAt());
            }
            // 异常是否因发布时间无效触发
            if (e.hasErrorCode(ErrorCodes.ISSUED_AT_INVALID_PAST)) {
                System.out.println("Issued at invalid past: " + jwtClaims.getIssuedAt());
            }
            // 异常是否因缺少发布时间触发
            if (e.hasErrorCode(ErrorCodes.ISSUED_AT_MISSING)) {
                System.out.println("Issued at missing!");
            }
            // 异常是否因签发者无效触发
            if (e.hasErrorCode(ErrorCodes.ISSUER_INVALID)) {
                System.out.println("Issuer invalid: " + jwtClaims.getIssuer());
            }
            // 异常是否因缺少签发者触发
            if (e.hasErrorCode(ErrorCodes.ISSUER_MISSING)) {
                System.out.println("Issuer missing!");
            }
            // 异常是否因 JSON 无效触发
            if (e.hasErrorCode(ErrorCodes.JSON_INVALID)) {
                System.out.println("Json invalid: " + jwtClaims.toString());
            }
            // 异常是否因缺少 JWT ID 触发
            if (e.hasErrorCode(ErrorCodes.JWT_ID_MISSING)) {
                System.out.println("JWT ID missing!");
            }
            // 异常是否因 JwtClaims 格式错误触发
            if (e.hasErrorCode(ErrorCodes.MALFORMED_CLAIM)) {
                System.out.println("Malformed claim!");
            }
            // 异常是否因缺少生效时间触发
            if (e.hasErrorCode(ErrorCodes.NOT_BEFORE_MISSING)) {
                System.out.println("Not before missing!");
            }
            // 异常是否因 Token 尚未生效触发
            if (e.hasErrorCode(ErrorCodes.NOT_YET_VALID)) {
                System.out.println("Not yet valid: " + jwtClaims.getNotBefore());
            }
            // 异常是否因 Token 的 Signature 部分无效触发
            if (e.hasErrorCode(ErrorCodes.SIGNATURE_INVALID)) {
                System.out.println("Signature invalid: " + jwtClaims.toString());
            }
            // 异常是否因 Token 的 Signature 部分缺失触发
            if (e.hasErrorCode(ErrorCodes.SIGNATURE_MISSING)) {
                System.out.println("Signature missing!");
            }
            // 异常是否因 Subject 无效触发
            if (e.hasErrorCode(ErrorCodes.SUBJECT_INVALID)) {
                System.out.println("Subject invalid: " + jwtClaims.getSubject());
            }
            // 异常是否因 Subject 缺失触发
            if (e.hasErrorCode(ErrorCodes.SUBJECT_MISSING)) {
                System.out.println("Subject missing!");
            }
            // 异常是否因 Type 无效触发
            if (e.hasErrorCode(ErrorCodes.TYPE_INVALID)) {
                System.out.println("Type invalid: " + jwtClaims.getRawJson());
            }
            // 异常是否因 Type 缺失触发
            if (e.hasErrorCode(ErrorCodes.TYPE_MISSING)) {
                System.out.println("Type missing!");
            }
        } catch (MalformedClaimException e1) {
            System.out.println("Malformed claim: " + e);
        }
    }
}
