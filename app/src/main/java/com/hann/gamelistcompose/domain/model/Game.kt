package com.hann.gamelistcompose.domain.model

data class Game(
  val gameId: String,
  val name: String,
  val released: String,
  val rating: Double,
  val background_image: String,
  val playtime: String,
  val isFavorite: Boolean
)