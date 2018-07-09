package cn.saymagic.begonia.exception

class UnexpectedCodeException: Exception {

    constructor(code: Int?) : super( "unexpected code $code")

}