package com.droidcon.authenticate.utils

import java.util.regex.Pattern

object Patterns {
    val PASSWORD_PATTERN: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z]).{8,64}\$")
    val EMAIL_ADDRESS: Pattern = Pattern.compile(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}