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
3. ks.platform.timezone.utc=Boolean(是否开启全局utc标准时区)
4. ks.platform.multi-login=false(是否支持多端登陆)

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

