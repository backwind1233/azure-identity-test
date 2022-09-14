package com.test.azureidentitytest.tokencredential;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import reactor.core.publisher.Mono;

public class WrapperTokenCredential implements TokenCredential {

    TokenCredential tokenCredential;

    public WrapperTokenCredential(TokenCredential tokenCredential) {
        this.tokenCredential = tokenCredential;
    }

    @Override
    public Mono<AccessToken> getToken(TokenRequestContext tokenRequestContext) {
        return null;
    }
}
