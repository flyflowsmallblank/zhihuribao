package com.example.zhihuribao

data class OldMessage(
    val date: String,
    val stories: List<Story>
) {
    data class Story(
        val ga_prefix: String,
        val hint: String,
        val id: Int,
        val image_hue: String,
        val images: List<String>,
        val title: String,
        val type: Int,
        val url: String
    )
}