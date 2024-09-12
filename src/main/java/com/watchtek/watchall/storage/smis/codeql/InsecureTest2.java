package com.watchtek.watchall.storage.smis.codeql;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class InsecureTest2
{
    public static void main(String[] args) throws Exception
    {
        SSLContext context2 = SSLContext.getInstance("TLS");
        File certificateFile = new File("path/to/self-signed-certificate");
        // Create a `KeyStore` with default type
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        // `keyStore` is initially empty
        keyStore.load(null, null);
        X509Certificate generatedCertificate;
        try (InputStream cert = new FileInputStream(certificateFile)) {
            generatedCertificate = (X509Certificate) CertificateFactory.getInstance("X509")
                    .generateCertificate(cert);
        }
        // Add the self-signed certificate to the key store
        keyStore.setCertificateEntry(certificateFile.getName(), generatedCertificate);
        // Get default `TrustManagerFactory`
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // Use it with our key store that trusts our self-signed certificate
        tmf.init(keyStore);
        TrustManager[] trustManagers = tmf.getTrustManagers();
        context2.init(null, trustManagers, null);
        // GOOD, we are not using a custom `TrustManager` but instead have
        // added the self-signed certificate we want to trust to the key
        // store. Note, the `trustManagers` will **only** trust this one
        // certificate.
        
        URL url = new URL("https://self-signed.badssl.com/");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setSSLSocketFactory(context2.getSocketFactory());
    }
}
