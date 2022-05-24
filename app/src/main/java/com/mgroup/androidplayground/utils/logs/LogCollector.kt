package com.mgroup.androidplayground.utils.logs

import android.os.Environment
import java.io.*
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class LogCollector {
	private var file : File = File(
		Environment.getExternalStorageDirectory(),
		"logcat.txt"
	)

	fun collectLogs() {
			Runtime.getRuntime()
					.exec(
						"logcat -f " + file.absolutePath
					)

		}

	fun getLogFile() : File {
		return file
	}

}