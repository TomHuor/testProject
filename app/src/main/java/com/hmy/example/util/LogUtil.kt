package com.hmy.example.util

import android.util.Log

/**
 * Created by hmy on 2017/9/19.
 */
object LogUtil {
    var TAG = "TestProject"
    fun v(vararg messages: Any?) {
        log(Log.VERBOSE, null, *messages)
    }

    fun d(vararg messages: Any?) {
        log(Log.DEBUG, null, *messages)
    }

    fun i(vararg messages: Any?) {
        log(Log.INFO, null, *messages)
    }

    fun w(vararg messages: Any?) {
        log(Log.WARN, null, *messages)
    }

    fun w(t: Throwable?, vararg messages: Any?) {
        log(Log.WARN, t, *messages)
    }

    fun e(vararg messages: Any?) {
        log(Log.ERROR, null, *messages)
    }

    fun e(t: Throwable?, vararg messages: Any?) {
        log(Log.ERROR, t, *messages)
    }

    private fun log(level: Int, t: Throwable?, vararg messages: Any?) {
        val message: String
        message = if (t == null && messages != null && messages.size == 1) {
            // handle this common case without the extra cost of creating a stringBuffer:
            messages[0].toString()
        } else {
            val sb = StringBuilder()
            if (messages != null) for (m in messages) {
                if (sb.isNotEmpty()) sb.append("\n")
                sb.append(m)
            }
            if (t != null) {
                sb.append("\n").append(Log.getStackTraceString(t))
            }
            sb.toString()
        }
        Log.println(
            level,
            TAG,
            createMessage(message)
        )
    }

    /**
     * 获取有类名与方法名的logString
     *
     * @param rawMessage
     * @return
     */
    private fun createMessage(rawMessage: String): String {
        /*
         * Throwable().getStackTrace()获取的是程序运行的堆栈信息，也就是程序运行到此处的流程，以及所有方法的信息
         * 这里我们为什么取2呢？
         * 0代表当前层方法的信息：createMessage()
         * 1代表上一层方法的信息：LogUtil.log()
         * 2代表上两层方法的信息：LogUtil.*()
         * 3代表上三层方法的信息：实际调用位置
         */
        val stackTraceElement =
            Throwable().stackTrace[3]
        val fullClassName = stackTraceElement.className
        val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
        return """
            $className.${stackTraceElement.methodName}()==>
            $rawMessage
            """.trimIndent()
    }
}