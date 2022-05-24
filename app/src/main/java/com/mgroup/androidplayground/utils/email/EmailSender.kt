package com.mgroup.androidplayground.utils.email

import android.content.Context
import com.mgroup.androidplayground.utils.email.model.Email
import timber.log.Timber
import java.nio.charset.StandardCharsets
import java.util.*
import javax.activation.DataHandler
import javax.activation.DataSource
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart

import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import javax.activation.FileDataSource





class EmailSender constructor(context: Context){

	private var session: Session

	init {
		val smtpHostServer = "smtp.googlemail.com"
		val properties = System.getProperties()
		properties["mail.smtp.host"] = smtpHostServer;
		properties["mail.smtp.starttls.enable"] = "true";
		properties["mail.smtp.auth"] = true;
		properties["mail.smtp.socketFactory.class"] = "javax.net.ssl" + ".SSLSocketFactory";
		properties["mail.smtp.socketFactory.fallback"] = "false";
		properties["mail.smtp.port"] = "465";
		properties["mail.smtp.socketFactory.port"] = "465";



		session = Session.getInstance(
			properties,
			SMTPAuthenticator(context)
		)
	}

		fun sendEmail(
			 email : Email
		){
			val msg = MimeMessage(session)
			//set message headers
			//set message headers
			msg.addHeader(
				"Content-type",
				"text/HTML; charset=UTF-8"
			)
			msg.addHeader(
				"format",
				"flowed"
			)
			msg.addHeader(
				"Content-Transfer-Encoding",
				"8bit"
			)

			msg.setFrom(
				InternetAddress(
					"no_reply@example.com",
					"NoReply-JD"
				)
			)

			msg.replyTo = InternetAddress.parse(
				"no_reply@example.com",
				false
			)

			msg.setSubject(
				email.title,
				"UTF-8"
			)


			msg.sentDate = Date()

			if(email.attachment != null){
				val fileBodyPart = MimeBodyPart()
				val multipart = MimeMultipart()

				fileBodyPart.attachFile(email.attachment)
				val source: DataSource = FileDataSource(email.attachment)
				fileBodyPart.dataHandler = DataHandler(source);
				fileBodyPart.fileName = "logs.txt"
				val textBodyPart = MimeBodyPart()
				textBodyPart.setText(email.body)
				multipart.addBodyPart(
					textBodyPart
				)
				multipart.addBodyPart(fileBodyPart)
				msg.setContent(multipart)
			} else {
				msg.setText(email.body, "UTF-8")
			}



			msg.setRecipients(
				Message.RecipientType.TO,
				InternetAddress.parse(
					email.recipient,
					false
				)
			)
			Timber.d("Sending mail!")
			Transport.send(msg)

			Timber.d("Email $email send sucessfully")
		}
}