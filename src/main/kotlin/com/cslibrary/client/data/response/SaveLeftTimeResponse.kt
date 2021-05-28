package com.cslibrary.client.data.response

import com.cslibrary.client.data.LeaderBoard
import com.cslibrary.client.data.UserNotification

data class SaveLeftTimeResponse(
    var leaderBoardList: List<LeaderBoard>, // Leaderboard as List
    var userNotificationList: List<UserNotification>
)
