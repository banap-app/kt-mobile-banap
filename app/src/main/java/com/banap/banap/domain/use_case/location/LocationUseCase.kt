package com.banap.banap.domain.use_case.location

import com.banap.banap.common.Resource
import com.banap.banap.data.model.location.Location
import com.banap.banap.data.repository.location.LocationRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class LocationUseCase @Inject constructor(
    private val repository: LocationRepositoryImpl
) {
    operator fun invoke() : Flow<Resource<Location>> = flow {
        try {
            emit(Resource.Loading())
            val weatherResponse = repository.getCurrentLocation()
            emit(Resource.Success(weatherResponse))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Um erro inesperado aconteceu"))
        } catch (e: IOException) {
            emit(Resource.Error("Não foi possível acessar o servidor. Verifique sua conexão com a internet"))
        }
    }
}