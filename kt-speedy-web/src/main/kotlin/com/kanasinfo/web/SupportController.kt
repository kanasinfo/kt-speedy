package com.kanasinfo.kt.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.http.MediaType
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import java.net.URLConnection
import java.net.URLEncoder

import java.text.SimpleDateFormat
import java.util.Date
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * @author J.Mars
 */
open class SupportController {

    protected val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        val dateFormat = SimpleDateFormat(DEFAULT_DATETIME_FORMAT)
        dateFormat.isLenient = false
        binder.registerCustomEditor(Date::class.java, CustomDateEditor(dateFormat, true))
        //        binder.registerCustomEditor(ObjectId.class, new ObjectIdEditor());
    }

    /**
     * 将错误信息统一打到页面，Key为"code"
     *
     * @param object
     * @param model
     */
    protected fun addBusinessExceptionAttribute(`object`: Any, model: Model) {
        model.addAttribute(BUSINESS_EXCEPTION_KEY, `object`)
    }

    protected fun setDownloadHeader(filename: String, request: HttpServletRequest, response: HttpServletResponse){
        var fileName = filename
        val userAgent = request.getHeader("User-Agent").toLowerCase();
        if (!userAgent.contains("chrome")) {    // 非Chrome
            //处理safari的乱码问题xzl
            val bytesName = fileName.toByteArray(Charsets.UTF_8)
            fileName = String(bytesName, Charsets.ISO_8859_1);
            response.setHeader("content-disposition", "attachment;fileName=$fileName");
        } else {    // Chrome
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        }
        response.contentType = URLConnection.guessContentTypeFromName(filename) ?: MediaType.APPLICATION_OCTET_STREAM.toString()
    }

    companion object {

        const val DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
        private const val BUSINESS_EXCEPTION_KEY = "bsExpCode"
    }

}
