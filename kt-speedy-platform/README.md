# 使用
引入依赖：

```xml
<dependency>
    <groupId>com.kanasinfo</groupId>
    <artifactId>kt-speedy-platform</artifactId>
    <version>${kt.version}</version>
</dependency>
```

# 配置项
1. ks.platform.token-secret-key=String(Token密钥)
2. ks.platform.expiration-day=10d(token失效日期)
3. ks.platform.inject.classpath=工程注入类，实现`IWebSecurityConfigInject`接口
4. ks.platform.timezone.utc=Boolean(是否开启全局utc标准时区)
5. ks.platform.multi-login=false(是否支持多端登陆)