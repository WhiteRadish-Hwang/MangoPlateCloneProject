package com.example.mangoplate_mock_aos_radi.src.main.review.model

import java.io.Serializable

data class ReviewReplyResultData (val commentUserList: ArrayList<Int>,
                                  val replyContents: String
): Serializable

