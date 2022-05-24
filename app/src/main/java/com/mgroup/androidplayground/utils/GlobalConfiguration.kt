package com.mgroup.androidplayground.utils

import android.content.Context
import com.mgroup.androidplayground.R
import java.lang.Exception
import java.util.*

class GlobalConfiguration {
	companion object{
		fun getGlobalConfiguration(context : Context, name : String) : String? {
			return try{
				val stream = context.resources.openRawResource(R.raw.config)
				val properties = Properties()
				properties.load(stream)
				properties.getProperty(name)
			} catch (e: Exception){
				e.printStackTrace()
				null
			}
		}
	}
}