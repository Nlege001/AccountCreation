package com.example.accounttcreation

data class DisplayDataClass(
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
    val dislikeCount : Int ?= null,
    val docId : String ?= null,
    val courseName : String ?= null

){
    operator fun get(input : Int): String? {
        return this.downloadURLPATH

    }


}
