package com.mgroup.androidplayground.model

import android.content.Context
import androidx.work.Data
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mgroup.androidplayground.utils.GlobalConfiguration
import com.mgroup.androidplayground.utils.email.EmailSender
import com.mgroup.androidplayground.utils.email.model.Email
import com.mgroup.androidplayground.utils.logs.LogCollector
import timber.log.Timber
import java.io.IOException

class SendLogsWorker(
	val context: Context,
	workerParameters: WorkerParameters) :
	Worker(context, workerParameters) {
	override fun doWork(): Result {
		return try {
			val logCollector = LogCollector()
			logCollector.collectLogs()
			Timber.d("collecting logs")

			val emailSender = EmailSender(context)
			emailSender.sendEmail(
				Email(
					GlobalConfiguration.getGlobalConfiguration(
						context,
						"app_email"
					)!!,
					title = "logs",
					body = "Attaching log file",
					attachment = logCollector.getLogFile()
				)
			)
			Timber.d("Sending maiL!")
			Result.success()
		} catch (e : Exception){
			e.printStackTrace()
			Timber.e(e.localizedMessage)
			Result.failure()
		}

	}
}