package uz.algorithmgateway.tezkorakfa.measurer.viewmodel

import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.ResponseBody
import uz.algorithmgateway.tezkorakfa.data.retrofit.api.ApiService
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.Responce
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.accessory.Accessory
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.profile.Profile
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.shelf.Shelf
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.window.Windows
import uz.algorithmgateway.tezkorakfa.data.retrofit.repository.NetworkRepository
import uz.algorithmgateway.tezkorakfa.ui.utils.UIState
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject


class NetworkViewmodel @Inject constructor(
    val apiService: ApiService,
    var networkRepository: NetworkRepository,
) : ViewModel() {
    init {
        getProfile()
        getWindow()
        getShelf()
        getAccessory()
    }

    private val _order = MutableStateFlow<Profile?>(null)
    val order: StateFlow<Profile?>
        get() = _order

    private fun getProfile() {
        viewModelScope.launch {
            networkRepository.getProfile().catch {

            }.collect {
                if (it.isSuccess) {
                    _order.emit(it.getOrNull())
                } else if (it.isFailure) _order.emit(null)
            }
        }
    }

    private val _window = MutableStateFlow<Windows?>(null)
    val window: StateFlow<Windows?>
        get() = _window

    private fun getWindow() {
        viewModelScope.launch {
            networkRepository.getWindow().catch {

            }.collect {
                if (it.isSuccess) {
                    _window.emit(it.getOrNull())
                } else if (it.isFailure) _window.emit(null)
            }
        }
    }


    private val _shelf = MutableStateFlow<Shelf?>(null)
    val shelf: StateFlow<Shelf?>
        get() = _shelf

    private fun getShelf() {
        viewModelScope.launch {
            networkRepository.getShelf().catch {

            }.collect {
                if (it.isSuccess) {
                    _shelf.emit(it.getOrNull())
                } else if (it.isFailure) _shelf.emit(null)
            }
        }
    }


    private val _accessory = MutableStateFlow<Accessory?>(null)
    val accessory: StateFlow<Accessory?>
        get() = _accessory


    private fun getAccessory() {
        viewModelScope.launch {
            networkRepository.getAccsessory().catch {

            }.collect {
                if (it.isSuccess) {
                    _accessory.emit(it.getOrNull())
                } else if (it.isFailure) _accessory.emit(null)
            }
        }
    }


    fun updateUser(id: String, body: RequestBody) {
        viewModelScope.launch {
            apiService.updateUserData(body)
        }
    }

    suspend fun acceptOrder(body: HashMap<String, Any>?) {
        viewModelScope.launch {
            networkRepository.acceptOrder(body)
        }
    }

    suspend fun sendData(id: String, body: RequestBody): MutableStateFlow<UIState<Responce?>> {
        val flow = MutableStateFlow<UIState<Responce?>>(UIState.Loading)
        viewModelScope.launch {
            networkRepository.sendData(id, body)
                .catch {
                    Log.e("Throw", "sendData: ${it.message}")
                }.collect {
                    if (it.isSuccess) {
                        flow.value = UIState.Success(it.getOrNull())
                    } else if (it.isFailure) flow.emit(UIState.Error((it.exceptionOrNull()?.message
                        ?: it.exceptionOrNull()).toString()))
                }
        }
        return flow
    }

    fun confirm(path: String, body: HashMap<String, Any>?): MutableStateFlow<String> {
        val flow = MutableStateFlow("Error")
        viewModelScope.launch {
            networkRepository.confirm(path, body)
                .catch {

                }.collect {
                    if (it.isSuccess) {
                        flow.emit("Succes")
                    } else if (it.isFailure) flow.emit("Error")
                }
        }
        return flow
    }

    suspend fun downloadFile(url: String) {
        viewModelScope.launch {
            if (networkRepository.downloadFile(url).isSuccessful) {
                val body = networkRepository.downloadFile(url).body()
                body?.let {
                    saveFile(it,
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .toString())
                }
            }
        }
    }
}

fun saveFile(body: ResponseBody, pathWhereYouWantToSaveFile: String){
    try {
        val `is`: InputStream = body.byteStream()
        val fos = FileOutputStream(
            File(pathWhereYouWantToSaveFile, "image.pdf")
        )
        var read = 0
        val buffer = ByteArray(32768)
        while (`is`.read(buffer).also { read = it } > 0) {
            fos.write(buffer, 0, read)
        }
        fos.close()
        `is`.close()
    } catch (e: Exception) {
        Log.e("pdf", "saveFile: ${"Exception: $e"}")
    }
}
