package com.personal_finance_manager_api.config;

import com.personal_finance_manager_api.middleware.TransactionOwnership;
import com.personal_finance_manager_api.middleware.UserOwnership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserOwnership userOwnershipInterceptor;
    private final TransactionOwnership transactionOwnershipInterceptor;

    @Autowired
    public WebConfig(UserOwnership userOwnershipInterceptor, TransactionOwnership transactionOwnershipInterceptor) {
        this.userOwnershipInterceptor = userOwnershipInterceptor;
        this.transactionOwnershipInterceptor = transactionOwnershipInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Apply the interceptor only to user-specific routes (exclude register)
        registry.addInterceptor(userOwnershipInterceptor)
                .addPathPatterns("/api/users/*")
                .excludePathPatterns("/api/users/register");
        registry.addInterceptor(transactionOwnershipInterceptor)
                .addPathPatterns("/api/transactions/*");
    }
}