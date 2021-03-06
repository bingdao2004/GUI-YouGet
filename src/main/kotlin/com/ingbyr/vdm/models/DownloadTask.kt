package com.ingbyr.vdm.models

import com.ingbyr.vdm.utils.VDMConfig
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

class DownloadTaskModel(val vdmConfig: VDMConfig, val url: String, createdAt: LocalDateTime? = null, formatID: String? = null, checked: Boolean? = null, title: String? = null, size: String? = null, progress: Double? = null, status: String? = null) : ViewModel() {
    init {
        messages = ResourceBundle.getBundle("i18n/MainView")
    }
    val checkedProperty = SimpleBooleanProperty(this, "checked", checked ?: false)
    var checked: Boolean by checkedProperty
    val titleProperty = SimpleStringProperty(this, "title", title ?: messages["ui.analyzing"])
    var title: String by titleProperty
    val sizeProperty = SimpleStringProperty(this, "size", size ?: messages["ui.analyzing"])
    var size: String by sizeProperty
    val statusProperty = SimpleStringProperty(this, "status", status ?: messages["ui.analyzing"])
    var status: String by statusProperty
    val progressProperty = SimpleDoubleProperty(this, "progress", progress ?: 0.0)
    var progress: Double by progressProperty
    val createdAtProperty = SimpleObjectProperty<LocalDateTime>(this, "createdAt", createdAt)
    var createdAt: LocalDateTime by createdAtProperty
    var formatID = formatID ?: ""

    fun toData(): DownloadTaskData {
        return DownloadTaskData(vdmConfig, url, createdAt, formatID, checked, title, size, progress, status)
    }
}

data class DownloadTaskData(val vdmConfig: VDMConfig, val url: String, var createdAt: LocalDateTime? = null, var formatID: String? = null, var checked: Boolean? = null, var title: String? = null, var size: String? = null, var progress: Double? = null, var status: String? = null) : Serializable {
    fun toModel(): DownloadTaskModel {
        return DownloadTaskModel(vdmConfig, url, createdAt, formatID, checked, title, size, progress, status)
    }
}