package data.request

data class LoginRequest(
    var userId: String = "",
    var userPassword: String = ""
)