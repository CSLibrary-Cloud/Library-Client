package com.cslibrary.client.data.response

data class UserLeftTimeResponse(
    var reservedSeat: SeatSelectResponse,
    var leftTime: Long
)
