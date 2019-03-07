package com.kanasinfo.kt.platform.core.inject

import com.kanasinfo.kt.ext.isPresent
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 * @author gefangshuai
 * @createdAt 2019-03-07 16:51
 **/
@Component
class InjectContextLoader{
    private var webSecurityConfigInject: IWebSecurityConfigInject? = null

    @Value("\${ks.platform.inject.security.classpath}")
    private var webSecurityConfigInjectClass:String? = null

    @PostConstruct
    fun init(){
        if(webSecurityConfigInjectClass.isPresent()) {
            val webSecurityConfigInjectClass = Class.forName(webSecurityConfigInjectClass)
            this.webSecurityConfigInject = webSecurityConfigInjectClass.newInstance() as IWebSecurityConfigInject
        }
    }

    fun getWebSecurityConfigInject() = webSecurityConfigInject
}