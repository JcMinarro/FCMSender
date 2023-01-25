import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import java.io.File
import java.lang.IllegalStateException

fun main() {
    val deviceToken = ""
    val payload = mapOf(
        "sender" to "stream.video",
        "type" to "call_incoming",
        "call_cid" to "call:123",
        "user_names" to "Jc, Santhosh",
    )

    initFirebase()
    sendPayloadPN(payload, deviceToken)
}

private fun sendPayloadPN(payload: Map<String, String>, deviceToken: String) {
    val messageId = FirebaseMessaging
        .getInstance()
        .send(
            Message
                .builder()
                .putAllData(payload)
                .setToken(deviceToken)
                .build()
        )
    print("Message sent: $messageId")
}

private fun initFirebase() {

    val inputStream = ClassLoader.getSystemResource("service-account.json")
        ?.let { File(it.toURI()).inputStream() }
        ?: throw IllegalStateException("No `service-account.json` file found on resources folder.")

    FirebaseApp.initializeApp(
        FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(inputStream))
            .build()
    )
}