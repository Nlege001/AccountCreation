package com.example.accounttcreation

data class IndividualCourseDataClass(
    val proffessor:String ?=null,
    val courseNumber:String ?=null,
    val semester:String ?=null,
    val difficlty:String ?=null,
    val courseRating:String ?=null,
    val grade:String ?=null,
    val comments:String ?=null,
    val email: String ?=null,
    val downloadURLPATH : String ?= null,
    val likeCount : Int ?= null,
    val dislikeCount : Int ?= null
) {

}
