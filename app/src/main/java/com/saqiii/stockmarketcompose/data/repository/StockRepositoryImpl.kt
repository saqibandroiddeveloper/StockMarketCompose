package com.saqiii.stockmarketcompose.data.repository

import com.saqiii.stockmarketcompose.data.csv.CSVParser
import com.saqiii.stockmarketcompose.data.local.StockDatabase
import com.saqiii.stockmarketcompose.data.mapper.toCompanyInfo
import com.saqiii.stockmarketcompose.data.mapper.toCompanyListing
import com.saqiii.stockmarketcompose.data.mapper.toCompanyListingEntity
import com.saqiii.stockmarketcompose.data.remote.StockApi
import com.saqiii.stockmarketcompose.domain.model.CompanyInfo
import com.saqiii.stockmarketcompose.domain.model.CompanyListing
import com.saqiii.stockmarketcompose.domain.model.IntraDayInfo
import com.saqiii.stockmarketcompose.domain.repository.StockRepository
import com.saqiii.stockmarketcompose.domain.utills.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intraDayInfoParser: CSVParser<IntraDayInfo>
) : StockRepository {
    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(
                Resource.Success(
                    data = localListings.map {
                        it.toCompanyListing()
                    }
                )
            )

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parser(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listing ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    companyListingEntities = listing.map { it.toCompanyListingEntity() }
                )
                emit(
                    Resource.Success(
                        data = dao.searchCompanyListing("")
                            .map { it.toCompanyListing() }
                    ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>> {
        return try {
            val response = api.getIntraDayInfo(symbol)
            val result = intraDayInfoParser.parser(response.byteStream())
            Resource.Success(result)

        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraDayInfo"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraDayInfo"
            )
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())

        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load Company Info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load Company Info"
            )
        }
    }


}