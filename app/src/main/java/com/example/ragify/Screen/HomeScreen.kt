package com.example.ragify.Screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import com.example.ragify.model.ChatMessage
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ragify.R
import com.example.ragify.Retrofit.AskRequest
import com.example.ragify.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var selectedPdf by remember {
        mutableStateOf<Uri?>(null)
    }

    var question by remember {
        mutableStateOf("")
    }



    var messages by remember {
        mutableStateOf(listOf<ChatMessage>())
    }

    var pdfName by remember {
        mutableStateOf("")
    }



    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->

        if (uri != null) {

            selectedPdf = uri

            val file = uriToFile(context, uri)

            val requestBody = file.asRequestBody(
                "application/pdf".toMediaTypeOrNull()
            )

            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestBody
            )

            scope.launch {

                try {

                    val response = RetrofitInstance.api.uploadPdf(multipartBody)

                    if(response.isSuccessful){

                        pdfName = file.name

                        messages = messages + ChatMessage(
                            "PDF uploaded successfully. Ask me anything about it.",
                            false
                        )

                    } else {

                        println("Upload Failed")

                    }

                } catch (e: Exception) {

                    e.printStackTrace()

                }

            }

        }

    }

    Scaffold(
        topBar = {
            TopBar()
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {

                if (pdfName.isNotEmpty()) {

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text("📄 $pdfName")

                            Text("✅ Uploaded")

                        }

                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }


                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(
                        top = 4.dp,
                        bottom = 90.dp
                    )
                ) {

                    items(messages) { message ->

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                if (message.isUser)
                                    Arrangement.End
                                else
                                    Arrangement.Start
                        ) {

                            Card(
                                modifier = Modifier.widthIn(max = 280.dp),
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor =
                                        if (message.isUser)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {

                                Text(
                                    text = message.text,
                                    modifier = Modifier.padding(14.dp),
                                    color =
                                        if (message.isUser)
                                            MaterialTheme.colorScheme.onPrimary
                                        else
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                )

                            }

                        }

                    }

                }

            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = question,
                    onValueChange = {
                        question = it
                    },
                    label = {
                        Text("Ask Question")
                    },
                    modifier = Modifier.weight(1f),
                    leadingIcon = {

                        IconButton(
                            onClick = {
                                pdfLauncher.launch(arrayOf("application/pdf"))
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_add_24),
                                contentDescription = "Upload PDF"
                            )
                        }

                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {

                        scope.launch {

                            if (question.isBlank()) {
                                return@launch
                            }

                            try {

                                val response = RetrofitInstance.api.askQuestion(
                                    AskRequest(question)
                                )

                                if (response.isSuccessful) {

                                    val userQuestion = question

                                    question = ""

                                    messages = messages + ChatMessage(
                                        userQuestion,
                                        true
                                    )

                                    val aiAnswer = response.body()?.answer ?: "No Answer"

                                    messages = messages + ChatMessage(
                                        aiAnswer,
                                        false
                                    )

                                }else {

                                    messages = messages + ChatMessage(
                                        "Failed to get answer.",
                                        false
                                    )

                                }

                            } catch (e: Exception) {

                                messages = messages + ChatMessage(
                                    e.message ?: "Unknown Error",
                                    false
                                )
                            }

                        }

                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_send_24),
                        contentDescription = "Send"
                    )
                }

            }

        }

    }

}

fun uriToFile(context: Context, uri: Uri): File {

    val inputStream = context.contentResolver.openInputStream(uri)

    val file = File(context.cacheDir, "upload.pdf")

    inputStream?.use { input ->

        file.outputStream().use { output ->

            input.copyTo(output)

        }

    }

    return file
}