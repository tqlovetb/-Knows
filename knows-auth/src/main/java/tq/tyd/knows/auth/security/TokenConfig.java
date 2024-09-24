package tq.tyd.knows.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenConfig {
    private final String SIGNING_KEY="knows_jwt";
    // 向Spring容器中保存一个令牌的生成策略
    //  策略指:1.保存在内存中   2.保存在客户端
    //  先保存在内存中
    @Bean
    public TokenStore tokenStore(){

        return new JwtTokenStore(accessTokenConverter());
    }
    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        /*JwtAccessTokenConverter 是 Spring Security OAuth2 提供的一个工具类，用于将 JWT 令牌转换为 OAuth2 访问令牌，并且还可以将 OAuth2 访问令牌转换为 JWT 令牌。converter.setSigningKey(SIGNING_KEY); 设置了用于签名和验证 JWT 令牌的密钥。
        * JwtAccessTokenConverter 是一个帮助类，负责将 JWT 编码和解码为 OAuth2 访问令牌。它主要的功能包括：

将 OAuth2 访问令牌编码为 JWT。
验证和解码收到的 JWT 以获取 OAuth2 访问令牌。*/
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        /*2. converter.setSigningKey(SIGNING_KEY);
这行代码的作用是设置用于签名和验证 JWT 令牌的密钥。签名密钥用于确保 JWT 的完整性和真实性。JWT 令牌在生成时使用该密钥进行签名，在验证时也使用该密钥来验证签名是否正确。

签名密钥：用于对 JWT 令牌进行签名的秘密密钥。它确保只有持有该密钥的一方才能生成合法的 JWT 令牌。
签名过程：当服务器生成 JWT 令牌时，它使用签名密钥对令牌的头部和负载部分进行签名，生成一个唯一的签名。该签名附加在 JWT 令牌的末尾。
验证过程：当服务器接收到 JWT 令牌时，它使用同样的签名密钥对令牌进行验证，以确保令牌未被篡改。*/
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
/*这段代码是一个Spring配置类，用于配置JWT（JSON Web Token）的生成和存储策略。它主要作用于OAuth2认证过程中生成和验证令牌。下面是对这段代码的详细解释：

注解@Configuration：
这个注解标注了一个配置类，Spring容器会将这个类中的@Bean方法生成的实例注册到Spring容器中。这意味着你可以在其他地方通过依赖注入来使用这些实例。

私有变量SIGNING_KEY：
private final String SIGNING_KEY="knows_jwt";
这是用于签名JWT的密钥。签名密钥是JWT生成和验证过程中用于确保令牌真实性和完整性的关键。

方法tokenStore()：

java
复制代码
@Bean
public TokenStore tokenStore(){
    return new JwtTokenStore(accessTokenConverter());
}
这个方法返回一个JwtTokenStore实例，用于存储JWT令牌。JwtTokenStore使用accessTokenConverter()方法生成的JwtAccessTokenConverter来处理令牌的转换和验证。@Bean注解表示这个方法返回的对象将被注册为Spring容器中的一个bean。

方法accessTokenConverter()：

java
复制代码
@Bean
public JwtAccessTokenConverter accessTokenConverter(){
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(SIGNING_KEY);
    return converter;
}
这个方法返回一个JwtAccessTokenConverter实例，用于在生成和解析JWT时使用签名密钥。setSigningKey(SIGNING_KEY)方法设置了用于签名和验证JWT的密钥。这个bean被注入到tokenStore中。

什么时候使用这段代码？
你需要使用这段代码的情景包括：

配置OAuth2认证服务器：如果你正在构建一个需要OAuth2认证的应用程序，并且你选择使用JWT作为令牌格式，那么你需要配置令牌的生成和存储策略。这段代码就是为此目的而编写的。

实现自定义的令牌生成和验证策略：如果你需要自定义JWT的生成和验证逻辑，比如设置特定的签名密钥、调整令牌的存储位置等，你可以通过类似的配置类来实现。

提升安全性：通过配置签名密钥，你可以确保JWT的真实性和完整性，防止令牌被篡改。

总的来说，这段代码是Spring Security中用于JWT配置的典型示例，用于在应用程序中实现安全可靠的令牌管理。*/
