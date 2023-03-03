package com.vault.springVault;

import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.vault.support.VaultResponseSupport;

public class VaultApp {
    public static void main(String[] args) {
        VaultTemplate vaultTemplate=new VaultTemplate(new VaultEndpoint(),new TokenAuthentication("00000000-0000-0000-0000-000000000000"));

        VaultResponse vaultResponse = vaultTemplate.write("secret/myapp", new Secrect("12345", "12345"));
        System.out.println(vaultResponse);

        VaultResponseSupport<Secrect> read = vaultTemplate.read("secret/myapp", Secrect.class);
        System.out.println(read.getData().getPassword());
        vaultTemplate.delete("secret/myapp");

    }
}
