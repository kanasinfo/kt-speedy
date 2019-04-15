package com.kanasinfo.platform.core.mvc

import com.kanasinfo.ext.presentOrNull
import com.kanasinfo.platform.exception.BusinessException
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException
import freemarker.template.Configuration
import freemarker.template.TemplateException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils

import java.io.IOException

/**
 * Created by gefangshuai on 2017/5/23.
 */
@Component
class MailSendContext {
    @Autowired
    private val javaMailSender: JavaMailSender? = null
    @Autowired
    private val freemarkerConfig: Configuration? = null

    private val logger = LoggerFactory.getLogger(MailSendContext::class.java)
    @Value("\${spring.mail.username:}")
    private var emailFrom: String? = null

    /**
     * @param to       收件人
     * @param subject  主题
     * @param template 模板
     * @param model    数据
     * @param cc       抄送
     * @param bcc      密送
     * @throws MessagingException
     * @throws IOException
     * @throws TemplateException
     */
    @Throws(MessagingException::class, IOException::class, TemplateException::class, BusinessException::class)
    fun sendMail(to: Array<String?>, subject: String, template: String, model: Map<String, Any?>, cc: Array<String>? = null, bcc: Array<String>? = null) {
        val mimeMessage = javaMailSender!!.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true)
        (emailFrom.presentOrNull()?:throw BusinessException("EMAIL_FROM_NOT_SET","please set 'spring.mail.username'")).let {
            helper.setFrom(it, it)
        }
        helper.setTo(to)
        if (cc != null && cc.isNotEmpty())
            helper.setCc(cc)
        if (bcc != null && bcc.isNotEmpty())
            helper.setBcc(bcc)
        helper.setSubject(subject)
        val t = freemarkerConfig!!.getTemplate(template)
        val text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model)

        helper.setText(text, true)
        logger.info("邮件内容: $text")
        try {
            javaMailSender.send(mimeMessage)
            logger.info("邮件发送成功: [${to.joinToString(",")}, $subject]")
        } catch (e: Exception) {
            logger.info("邮件发送失败: [${to.joinToString(",")}, $subject]", e)
            throw BusinessException("MAIN_SEND_FAIL", "MAIN_SEND_FAIL", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
