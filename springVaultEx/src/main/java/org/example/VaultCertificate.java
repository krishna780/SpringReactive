package org.example;

import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultPkiOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.CertificateBundle;
import org.springframework.vault.support.VaultCertificateRequest;

import java.security.KeyStore;
import java.time.Duration;
import java.util.Arrays;

public class VaultCertificate {
    public static void main(String[] args) {
        VaultOperations vaultOperations=new VaultTemplate(new VaultEndpoint());
        VaultPkiOperations opsForPki = vaultOperations.opsForPki("pki");
        VaultCertificateRequest request = VaultCertificateRequest.builder().ttl(Duration.ofHours(2))
                .altNames(Arrays.asList("prod.dc-1.example.com", "prod.dc-2.example.com"))
                .withIpSubjectAltName("1.2.3.4").commonName("hello.example.com").build();
        CertificateBundle certificateBundle = opsForPki.issueCertificate("role", request).getRequiredData();
        KeyStore keyStore = certificateBundle.createKeyStore("keyStore");
        opsForPki.revoke(certificateBundle.getSerialNumber());
    }
}
