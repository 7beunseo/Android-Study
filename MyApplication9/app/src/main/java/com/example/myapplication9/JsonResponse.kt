package com.example.myapplication9

data class myJsonItems(val districtName: String, val issueDate: String, val issueType: String, val issueGbn: String)
data class myJsonBody(val items: MutableList<myJsonItems>)
data class myJsonREsponse(val body: myJsonBody)
data class JsonResponse(val response: myJsonREsponse) {

}
