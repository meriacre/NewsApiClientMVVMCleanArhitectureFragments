package com.test.newsapiclient.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

class NewsApiServiceTest {

    private lateinit var service: NewsApiService
    private lateinit var server: MockWebServer


    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    @After
    fun tearDown() {
       server.shutdown()
    }

    private fun enqueueMockResponse(fileName: String){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadlines_sendRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody =  service.getTopHeadLines("us", 1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()

            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=99e55908231c47229d4c522018e7f08a")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody =  service.getTopHeadLines("us", 1).body()
            val articlesList = responseBody!!.articles
            assertThat(articlesList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody =  service.getTopHeadLines("us", 1).body()
            val articlesList = responseBody!!.articles
            val article = articlesList[1]
            assertThat(article.author).isEqualTo("Justin Barney")
            assertThat(article.url).isEqualTo("https://www.news4jax.com/sports/2023/01/15/jaguars-cap-historic-comeback-with-walkoff-win-over-chargers/")
        }
    }


}