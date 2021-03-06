package com.happycomp.weatherforecast.model.room.repo

import androidx.lifecycle.LiveData
import com.happycomp.weatherforecast.model.interfaces.FavoriteActions
import com.happycomp.weatherforecast.model.interfaces.NetworkHandler
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import com.happycomp.weatherforecast.model.retrofit.WeatherInterface
import com.happycomp.weatherforecast.model.room.data.FavoritesDao
import com.happycomp.weatherforecast.model.extra.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteRepo @Inject constructor(
    private val weatherInterface: WeatherInterface,
    private val favoritesDao: FavoritesDao
) : FavoriteActions {

    override fun observeAllFavorites(): LiveData<List<BaseWeather>> =
        favoritesDao.observeAllFavorites()

    override suspend fun addNewFavorite(lat: Double, long: Double, networkHandler: NetworkHandler) {
        try {
            networkHandler.showIndicator()
            val response = weatherInterface.getWeatherData(
                lat,
                long,
                "minutely,hourly,daily",
                Constants.currentUnits.value!!.value
            )
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val baseWeather: BaseWeather = response.body()!!
                    addFavorite(baseWeather)
                    GlobalScope.launch(Dispatchers.Main) {
                        networkHandler.onSuccess()
                    }
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    networkHandler.onErrorOccurred()
                }
            }
        } catch (e: Exception) {
            GlobalScope.launch(Dispatchers.Main) {
                networkHandler.onConnectionFailed()
            }
        }
    }

    private suspend fun addFavorite(baseWeather: BaseWeather) {
        favoritesDao.addFavorite(baseWeather)
    }

    override suspend fun deleteFavorite(baseWeather: BaseWeather) {
        favoritesDao.deleteFavorite(baseWeather)
    }

    override suspend fun updateFavorite(baseWeather: BaseWeather) {
        try {
            val response = weatherInterface.getWeatherData(
                baseWeather.lat,
                baseWeather.lon,
                "minutely,hourly,daily",
                Constants.currentUnits.value!!.value
            )
            if (response.isSuccessful && response.body() != null) {
                val baseWeatherResult: BaseWeather = response.body()!!
                favoritesDao.updateFavorite(baseWeatherResult)
            }
        } catch (e: Exception) { }
    }
}