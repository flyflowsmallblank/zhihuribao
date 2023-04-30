package com.example.zhihuribao

data class LatestMessage(
    val date: String,
    val stories: List<Story>,
    val top_stories: List<TopStory>
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

    data class TopStory(
        val ga_prefix: String,
        val hint: String,
        val id: Int,
        val image: String,
        val image_hue: String,
        val title: String,
        val type: Int,
        val url: String
    )
}