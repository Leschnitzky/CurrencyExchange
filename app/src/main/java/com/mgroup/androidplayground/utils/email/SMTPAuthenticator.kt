package com.mgroup.androidplayground.utils.email

import android.content.Context
import com.mgroup.androidplayground.utils.GlobalConfiguration
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication

public class SMTPAuthenticator constructor(val context: Context)
	: Authenticator() {
	override fun getPasswordAuthentication(): PasswordAuthentication {
		return PasswordAuthentication(
			GlobalConfiguration.getGlobalConfiguration(context,"app_email"),
			GlobalConfiguration.getGlobalConfiguration(context,"app_password")
		)
	}
}