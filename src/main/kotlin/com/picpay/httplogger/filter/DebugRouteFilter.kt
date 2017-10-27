package com.picpay.httplogger.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.springframework.stereotype.Component

@Component
class DebugRouteFilter : ZuulFilter() {

    override fun run(): Any? {
        val ctx = RequestContext.getCurrentContext()
        return null
    }

    override fun shouldFilter(): Boolean = false

    override fun filterType(): String = "route"

    override fun filterOrder(): Int = 0
}