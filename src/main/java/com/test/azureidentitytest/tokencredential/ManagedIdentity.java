package com.test.azureidentitytest.tokencredential;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class ManagedIdentity {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ManagedIdentityCredential msi = new ManagedIdentityCredentialBuilder().build();
        TokenRequestContext requestContext = new TokenRequestContext();
        requestContext.addScopes("https://ossrdbms-aad.database.windows.net/.default");
        msi.getToken(requestContext)
                .subscribe(new BaseSubscriber<AccessToken>(){
                               @Override
                               protected void hookOnNext(AccessToken value) {
                                   System.out.println(value.getToken());

                               }

                               @Override
                               protected void hookOnComplete() {
                                   System.out.println("requestContext = " + requestContext);
                                   countDownLatch.countDown();
                               }

                               @Override
                               protected void hookOnError(Throwable throwable) {
                                   System.out.println("throwable = " + throwable);
                                   countDownLatch.countDown();

                               }
                           }
                );
        countDownLatch.await();
    }
}
