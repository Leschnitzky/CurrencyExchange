package com.mgroup.androidplayground.utils.email.model

import java.io.File

data class Email(val recipient: String, val title: String, val body:
String, val attachment : File?
)
