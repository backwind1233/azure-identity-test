package com.test.azureidentitytest.tokencredential;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.AzureCliCredential;
import com.azure.identity.AzureCliCredentialBuilder;
import com.azure.identity.ChainedTokenCredential;
import com.azure.identity.ChainedTokenCredentialBuilder;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.stream.Collectors;

public class CredentialAutoconfiguration {
    @Bean
    @ConditionalOnMissingBean
    ChainedTokenCredential credential(ObjectProvider<TokenCredential> objectProvider) {
        ChainedTokenCredentialBuilder builder = new ChainedTokenCredentialBuilder();
        List<TokenCredential> collect = objectProvider.stream().map(t -> {
            if (t instanceof ManagedIdentityCredential)
                return new WrapperTokenCredential(t);
            else return t;
        }).sorted().collect(Collectors.toList());
        builder.addAll(collect);
        return builder.build();
    }


    @Bean
    @ConditionalOnMissingBean
    AzureCliCredential azureCliCredential() {
        return new AzureCliCredentialBuilder().build();

    }

    @Bean
    @Order(10)
    @ConditionalOnMissingBean
    ManagedIdentityCredential managedIdentityCredential() {
        return new ManagedIdentityCredentialBuilder().build();
    }
}
