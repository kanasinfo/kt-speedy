package com.kanasinfo.web

import javax.servlet.http.HttpServletRequest

fun HttpServletRequest.getDomain(): String {
    val url = this.requestURL
    return url.delete(url.length - this.requestURI.length, url.length).append(this.servletContext.contextPath).toString()
}