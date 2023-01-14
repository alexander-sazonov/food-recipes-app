package com.alexandersazonov.foodrecipesapp.data.repository

import com.alexandersazonov.foodrecipesapp.data.entities.Recipe
import com.alexandersazonov.foodrecipesapp.data.entities.RecipesApiState
import com.alexandersazonov.foodrecipesapp.data.entities.RecipesResponse
import com.alexandersazonov.foodrecipesapp.data.remote.RecipesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class RecipesRemoteDataSourceImpl @Inject constructor(private val service: RecipesService) : RecipesRemoteDataSource {
//    override suspend fun getRandomRecipes(): Flow<RecipesApiState<List<Recipe>>> = flow {
//        val response = service.randomRecipes()
//        when {
//            isResponseSuccess(response) -> emit(RecipesApiState.Result(response.body()!!.recipes))
//            else -> emit(RecipesApiState.Error)
//        }
//
//    }.catch {
//        emit(RecipesApiState.Error)
//    }.flowOn(Dispatchers.IO)

//    override suspend fun getRecipeById(id: Int): Flow<RecipesApiState<Recipe>> = flow {
//        val response = service.recipeById(id)
//        if (response.isSuccessful){
//            emit(RecipesApiState.Result(response.body()!!))
//        } else{
//            emit(RecipesApiState.Error)
//        }
//
//    }.catch {
//        emit(RecipesApiState.Error)
//    }.flowOn(Dispatchers.IO)

    private fun isResponseSuccess(response: Response<RecipesResponse>) =
        response.isSuccessful && response.body() != null && response.body()!!.recipes.isNotEmpty()

    override suspend fun getRandomRecipes(): RecipesApiState<List<Recipe>> =
        withContext(Dispatchers.IO){
            try {
                val response = service.randomRecipes()
                if (response.isSuccessful){
                    return@withContext RecipesApiState.Result(response.body()!!.recipes)
                } else {
                    return@withContext RecipesApiState.Error
                }

            }catch (exception: Exception){
                return@withContext RecipesApiState.Error
            }
        }


    override suspend fun getRecipeById(id: Int): RecipesApiState<Recipe> =
        withContext(Dispatchers.IO){
            try {
                val response = service.recipeById(id)
                if (response.isSuccessful){
                    return@withContext RecipesApiState.Result(response.body()!!)
                } else {
                    return@withContext RecipesApiState.Error
                }

            }catch (exception: Exception){
                return@withContext RecipesApiState.Error
            }
        }
}