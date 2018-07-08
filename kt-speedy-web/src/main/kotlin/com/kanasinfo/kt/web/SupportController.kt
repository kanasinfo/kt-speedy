package com.kanasinfo.kt.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder

import java.text.SimpleDateFormat
import java.util.Date


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

    companion object {

        const val DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
        private const val BUSINESS_EXCEPTION_KEY = "bsExpCode"
    }

}
