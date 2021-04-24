package tech.pzjswpooks.zzpj.chat.api.ejb.utils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * Typ Properties loader.
 */
@Startup
@Singleton
public class PropertiesLoader {

    private String confirmationJwtSecret;

    /**
     * Pobiera pole confirmation jwt secret.
     *
     * @return confirmation jwt secret
     */
    public String getConfirmationJwtSecret() {
        return confirmationJwtSecret;
    }

    /**
     * Pobiera pole confirmation jwt expiration.
     *
     * @return confirmation jwt expiration
     */
    public Long getConfirmationJwtExpiration() {
        return confirmationJwtExpiration;
    }

    private Long confirmationJwtExpiration;

    @PostConstruct
    private void loadProperties() {
        Properties prop = null;
        try {
            prop = new Properties();
            InputStream inputStream  = PropertiesLoader.class.getClassLoader()
                    .getResourceAsStream("application.properties");
            if (inputStream != null) {
                prop.load(inputStream);
            }
        } catch (IOException e) {
            // TODO: 18.04.2021
        }
        confirmationJwtSecret = prop.getProperty("confirmation.jwt.secret");
        confirmationJwtExpiration = Long.valueOf(prop.getProperty("confirmation.jwt.expirationMs"));
        System.out.println(confirmationJwtExpiration);
        System.out.println(confirmationJwtSecret);
    }
}
