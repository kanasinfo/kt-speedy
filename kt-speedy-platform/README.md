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
1. ks.platform.token.private-key-path=String(Token公钥密钥路径：classpath:xxx)
2. ks.platform.token.public-key-path=String(Token私钥密钥路径classpath:xxx)
3. ks.platform.token.expiration-day=10d(token失效日期)
4. ks.platform.koken.rsaKey-auto-generate=Boolean(是否自动生成RSA密钥对，默认路径为classpath:rsa/public.pem, classpath:rsa/private.pem)
5. ks.platform.timezone.utc=Boolean(是否开启全局utc标准时区)
6. ks.platform.multi-login=false(是否支持多端登陆)

# 生成RSA密钥对
- 生成私钥
 ```bash
openssl genrsa -out private.pem 1024
```
- 生成公钥
```bash
openssl rsa -in private.pem -outform PEM -pubout -out public.pem
```

# 扩展
实现 `com.kanasinfo.platform.core.inject.IWebSecurityConfigInject` 接口，可实现具体方法完成功能扩展


# 用户登录
```javascript
post('/api/login', {
    username: this.username,
    password: this.password
})
```
> 参数为 `RequestBody` 数据模型 

