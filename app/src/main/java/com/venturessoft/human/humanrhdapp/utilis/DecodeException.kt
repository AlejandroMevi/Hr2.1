package com.venturessoft.human.humanrhdapp.utilis

import java.lang.RuntimeException

class DecodeException: RuntimeException  {
    internal constructor(message: String?) : super(message) {}
    internal constructor(message: String?, cause: Throwable?) : super(message, cause) {}
}