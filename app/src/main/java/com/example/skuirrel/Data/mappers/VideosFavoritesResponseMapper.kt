package com.example.skuirrel.Data.mappers

import com.example.skuirrel.Data.model.VideosResults
import com.example.skuirrel.Model.Videos
import javax.inject.Inject

class VideosFavoritesResponseMapper @Inject constructor() {

    fun mapToDomain(entity: VideosResults): Videos {
        return Videos(
            id = entity.id,
            key = entity.key,
            name = entity.name
        )
    }

    fun mapToEntity(domainModel: Videos): VideosResults {
        return VideosResults(
            id = domainModel.id,
            key = domainModel.key,
            name = domainModel.name
        )
    }

    fun mapToDomainList(videosResults: List<VideosResults>): List<Videos> {
        return videosResults.map {
            mapToDomain(it) }
    }

    fun mapToEntityList(videos: List<Videos>): List<VideosResults> {
        return videos.map {
            mapToEntity(it) }
    }
}