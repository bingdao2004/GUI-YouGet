package com.ingbyr.vdm.engine

import com.beust.klaxon.JsonObject
import com.ingbyr.vdm.models.DownloadTaskModel
import com.ingbyr.vdm.utils.DownloadType
import com.ingbyr.vdm.utils.EngineType
import com.ingbyr.vdm.utils.VDMProxy
import org.slf4j.Logger
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean


abstract class AbstractEngine {
    /**
     * Adapt to the different platform
     * Build the args for the command line
     * Exec the command line
     * Parse the output and update the main UI thread
     * Stop thread
     */
    protected abstract val logger: Logger
    protected val running: AtomicBoolean = AtomicBoolean(false)
    protected val argsMap: MutableMap<String, String> = mutableMapOf()
    protected abstract val remoteVersionUrl: String
    abstract val engineType: EngineType
    abstract fun url(url: String): AbstractEngine
    abstract fun addProxy(proxy: VDMProxy): AbstractEngine
    abstract fun fetchMediaJson(): JsonObject
    abstract fun format(formatID: String): AbstractEngine
    abstract fun output(outputPath: String): AbstractEngine
    abstract fun downloadMedia(downloadTaskModel: DownloadTaskModel, message: ResourceBundle)
    abstract fun parseDownloadOutput(line: String)
    abstract fun execCommand(command: MutableList<String>, downloadType: DownloadType): StringBuilder?
    abstract fun parseFormatsJson(json: JsonObject): List<MediaFormat>

    abstract fun updateUrl(version: String): String
    abstract fun existNewVersion(localVersion: String): Boolean

    open fun stopTask() {
        /**
         * Please overwrite this if need extra operation.
         */
        running.set(false)
    }

    protected fun MutableMap<String, String>.build(): MutableList<String> {
        /**
         * Generate the command line from argsMap
         */
        val args = mutableListOf<String>()
        this.forEach {
            if (it.key.startsWith("-")) {
                args.add(it.key)
                args.add(it.value)
            } else {
                args.add(it.value)
            }
        }
        logger.debug("exec $args")
        return args
    }
}