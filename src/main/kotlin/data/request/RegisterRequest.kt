package data.request
import data.User

data class RegisterRequest(
    var userId: String = "",
    var userPassword: String = "",
    var userName: String = "",
    var userPhoneNumber: String = ""
) {
}
