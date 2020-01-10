import com.google.firebase.FirebaseApp

private fun setup() {
    FirebaseApp.initializeApp()
}

fun main() {
    setup()
    registerHttp()
}