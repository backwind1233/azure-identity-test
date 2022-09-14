package com.test.azureidentitytest.tokencredential;

import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;

public class GetTokenWithDAC {
    public static void main(String[] args) {
        DefaultAzureCredential dac = new DefaultAzureCredentialBuilder().build();
        TokenRequestContext requestContext = new TokenRequestContext();
        requestContext.addScopes("https://ossrdbms-aad.database.windows.net/.default");
        String token = dac.getToken(requestContext).block().getToken();
        System.out.println("token = " + token);
    }
}
