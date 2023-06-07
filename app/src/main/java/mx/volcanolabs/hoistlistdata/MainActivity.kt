package mx.volcanolabs.hoistlistdata

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.volcanolabs.hoistlistdata.ui.theme.HoistListDataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HoistListDataTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainTodoView()
                }
            }
        }
    }
}

@Composable
fun MainTodoView(viewModel: MainActivityViewModel = MainActivityViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = uiState.userItems

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(listState) { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    text = item.name
                )

                Checkbox(checked = item.checked, onCheckedChange = {
                    val index = listState.indexOf(item)
                    viewModel.updateItem(index, !item.checked)
                })
            }
        }

        item {
            Button(onClick = {
                viewModel.sendData()
            }) {
                Text("Send")
            }
        }
    }
}

data class MainActivityUiState(
    val userItems: SnapshotStateList<UserItem> = mutableStateListOf(
        UserItem("Emma", false),
        UserItem("Liam", false),
        UserItem("Olivia", false),
        UserItem("Noah", false),
        UserItem("Ava", false),
        UserItem("Isabella", false),
        UserItem("Sophia", false),
        UserItem("Mia", false),
        UserItem("Isabella", false),
        UserItem("Olivia", false),
        UserItem("Noah", false),
        UserItem("Liam", false),
        UserItem("Mia", false),
        UserItem("Ava", false),
        UserItem("Sophia", false),
        UserItem("Mia", false),
        UserItem("Noah", false),
        UserItem("Liam", false),
        UserItem("Mia", false),
        UserItem("Ava", false),
        UserItem("Sophia", false),
        UserItem("Mia", false),
        UserItem("Noah", false),
        UserItem("Liam", false),
        UserItem("Mia", false),
        UserItem("Ava", false),
        UserItem("Sophia", false),
        UserItem("Mia", false),
    ),
)

class MainActivityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        MainActivityUiState()
    )
    val uiState: StateFlow<MainActivityUiState> get() = _uiState

    fun updateItem(index: Int, checked: Boolean) {
        _uiState.value.userItems[index] = _uiState.value.userItems[index].copy(checked = checked)
    }

    fun sendData() {
        Log.d("MainActivityViewModel", "list: ${_uiState.value.userItems.size}")
    }
}

data class UserItem(
    val name: String,
    var checked: Boolean
)