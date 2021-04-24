package tech.pzjswpooks.zzpj.chat.api.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;


import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.ParseException;
import java.util.Date;

import tech.pzjswpooks.zzpj.chat.api.ejb.utils.PropertiesLoader;

/**
 * Typ Jwt utils.
 */
@Stateless
public class JwtUtils {

    @Inject
    private PropertiesLoader propertiesLoader;

    private String registrationConfirmationJwtSecret;
    private Long registrationConfirmationJwtExpirationMs;

    @PostConstruct
    private void init() {
        registrationConfirmationJwtSecret = propertiesLoader.getConfirmationJwtSecret();
        registrationConfirmationJwtExpirationMs = propertiesLoader.getConfirmationJwtExpiration();
    }

    /**
     * Generuje token JWT na potrzeby weryfikacji rejestracji.
     *
     * @param username username
     * @return JWT token
     */
    public String generateJwtTokenForUser(String username) {
        try {
            final JWSSigner signer = new MACSigner(registrationConfirmationJwtSecret);
            final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .expirationTime(new Date(new Date().getTime() + registrationConfirmationJwtExpirationMs))
                    .build();
            final SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS384), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            // TODO: 18.04.2021
            return "JWT error";
        }
    }

    /**
     * Pobiera login użytkownika z tokenu JWT wydanego na potrzebę weryfikacji konta po rejestracji.
     * @param token JWT token
     * @return Login użytkownika o zadanym tokenie
     * @throws ParseException ParseException
     */
    public String getUserNameFromJwtToken(String token) throws ParseException {
        return SignedJWT.parse(token).getJWTClaimsSet().getSubject();
    }

    /**
     * Metoda sprawdzająca token jwt na potrzeby weryfikacji konta poprzez email.
     * @param tokenToValidate JWT token
     * @return boolean
     */

    public boolean validateJwtToken(String tokenToValidate) {
        try {
            JWSObject jwsObject = JWSObject.parse(tokenToValidate);
            JWSVerifier jwsVerifier = new MACVerifier(registrationConfirmationJwtSecret);
            return jwsObject.verify(jwsVerifier);
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return false;
        }
    }


}
