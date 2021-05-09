package com.cslibrary.client.data.request

data class RegisterRequest(
    var userId: String,
    var userPassword: String,
    var userName: String,
    var userPhoneNumber: String
)