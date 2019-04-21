package com.kanasinfo.aliyun.sms

import com.aliyun.mns.common.ClientException
import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse
import com.aliyuncs.profile.DefaultProfile
import java.text.SimpleDateFormat
import java.util.*

/**
 * 阿里云短信发送工具类
 */
class AliyunSmsKt private constructor(private var client: DefaultAcsClient) {

    companion object {
        fun build(config: Config): AliyunSmsKt {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", config.defaultConnectTimeout)
            System.setProperty("sun.net.client.defaultReadTimeout", config.defaultReadTimeout)

            //初始化acsClient,暂不支持region化
            val profile = DefaultProfile.getProfile("cn-hangzhou", config.accessKeyId, config.accessKeySecret)
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", config.product, config.domain)
            val acsClient = DefaultAcsClient(profile)
            return AliyunSmsKt(acsClient)
        }

    }

    /**
     * 给单用户发送短信
     */
    @Throws(ClientException::class)
    fun sendSms(phoneNumbers: String, signName: String, templateCode: String, templateParam: String, outId: String? = null): SendSmsResponse? {
        //组装请求对象-具体描述见控制台-文档部分内容
        val request = SendSmsRequest()
        //必填:待发送手机号
        request.phoneNumbers = phoneNumbers
        //必填:短信签名-可在短信控制台中找到
        request.signName = signName
        //必填:短信模板-可在短信控制台中找到
        request.templateCode = templateCode
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.templateParam = templateParam

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.outId = outId

        //hint 此处可能会抛出异常，注意catch
        return client.getAcsResponse(request)
    }

    /**
     * @param phoneNumbers 查询电话号码
     * @param bizId 流水号
     * @param sendDate  发送日期，支持30天内的发送记录
     * @param pageSize  一页多少条记录
     * @param currentPage   当前页码，（从1开始）
     */
    @Throws(com.aliyuncs.exceptions.ClientException::class)
    fun querySendDetails(phoneNumbers: String, bizId: String, sendDate: Date, pageSize: Long = 10L, currentPage: Long = 1L): QuerySendDetailsResponse {
        //组装请求对象
        val request = QuerySendDetailsRequest()
        //必填-号码
        request.phoneNumber = phoneNumbers
        //可选-流水号
        request.bizId = bizId
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        val ft = SimpleDateFormat("yyyyMMdd")
        request.sendDate = ft.format(sendDate)
        //必填-页大小
        request.pageSize = pageSize
        //必填-当前页码从1开始计数
        request.currentPage = currentPage

        val response = this.client.getAcsResponse(request)
        if (response.code != "OK") {
            throw SmsSendException(response.code, response.message)
        }
        return response
    }

    data class Config(
            //产品名称:云通信短信API产品,开发者无需替换
            val product: String = "Dysmsapi",
            //产品域名,开发者无需替换
            val domain: String = "dysmsapi.aliyuncs.com",

            var defaultConnectTimeout: String = "10000",
            var defaultReadTimeout: String = "10000",
            var accessKeyId: String,
            var accessKeySecret: String
    )
}
