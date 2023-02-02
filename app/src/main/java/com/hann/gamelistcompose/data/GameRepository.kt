package com.hann.gamelistcompose.data

import com.hann.core.data.GameSearchBoundResource
import com.hann.core.utils.AppExecutors
import com.hann.core.utils.DataMapper
import com.hann.gamelistcompose.data.source.local.LocalDataSource
import com.hann.gamelistcompose.data.source.remote.RemoteDataSource
import com.hann.gamelistcompose.data.source.remote.network.ApiResponse
import com.hann.gamelistcompose.data.source.remote.response.GameResponse
import com.hann.gamelistcompose.domain.model.Game
import com.hann.gamelistcompose.domain.repository.IGameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GameRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IGameRepository {

    override fun getAllGame(): Flow<Resource<List<Game>>> =
        object : com.hann.core.data.NetworkBoundResource<List<Game>, List<GameResponse>>(){
            override fun loadFromDB(): Flow<List<Game>> {
                return localDataSource.getAllGame().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Game>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<GameResponse>>> =
                remoteDataSource.getAllGame()

            override suspend fun saveCallResult(data: List<GameResponse>) {
                val gameList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGame(gameList)
            }
        }.asFlow()

    override fun getGame(query: String): Flow<Resource<List<Game>>> {
        return GameSearchBoundResource(query, remoteDataSource, localDataSource).asFlow()
    }


    override fun getFavoriteGame(): Flow<List<Game>> {
        return localDataSource.getFavoriteGame().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }


    override fun setFavoriteGame(game: Game, state: Boolean) {
        val gameEntity = DataMapper.mapDomainToEntity(game)
        appExecutors.diskIO().execute { localDataSource.setFavoriteGame(gameEntity, state) }
    }
}