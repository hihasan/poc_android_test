package xyz.hihasan.ledgerlite.core.network.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.hihasan.ledgerlite.core.network.dto.AccountDto
import xyz.hihasan.ledgerlite.core.network.dto.AuthRequest
import xyz.hihasan.ledgerlite.core.network.dto.AuthResponse
import xyz.hihasan.ledgerlite.core.network.dto.CreateTransactionRequest
import xyz.hihasan.ledgerlite.core.network.dto.PageDto
import xyz.hihasan.ledgerlite.core.network.dto.TransactionDto

interface LedgerApi {

    @POST("auth/login")
    suspend fun login(@Body body: AuthRequest): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body body: AuthRequest): AuthResponse

    @GET("accounts")
    suspend fun getAccounts(): List<AccountDto>

    @GET("transactions")
    suspend fun getTransactions(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("query") query: String? = null,
    ): PageDto<TransactionDto>

    @GET("transactions/{id}")
    suspend fun getTransaction(@Path("id") id: String): TransactionDto

    @POST("transactions")
    suspend fun createTransaction(@Body body: CreateTransactionRequest): TransactionDto
}
