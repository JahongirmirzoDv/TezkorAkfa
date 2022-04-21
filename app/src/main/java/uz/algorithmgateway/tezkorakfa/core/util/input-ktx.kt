package uz.algorithmgateway.tezkorakfa.core.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

val EditText.input: String?
    get() = text?.toString()

@OptIn(ExperimentalCoroutinesApi::class)
fun EditText.textChanges(): Flow<String?> = callbackFlow {
    val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            trySend(s?.toString())
        }

    }
    addTextChangedListener(watcher)
    awaitClose { removeTextChangedListener(watcher) }
}