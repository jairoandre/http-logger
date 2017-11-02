package com.picpay.httplogger.service

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class LoggerService {

    val map = HashMap<String, String>()
    val times = HashMap<String, Long>()

}