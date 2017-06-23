package kr.hs.e_mirim.politicsteens;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by User on 2017-06-21.
 */

public interface NaverNewsClient {

    @Headers({
            "X-Naver-Client-Id: UO9JvYiYOzMu9ckdjqlu",
            "X-Naver-Client-Secret: UbDi5EVeza",
    })
    @GET("v1/search/news.json?query=청소년정치")
    Call<NaverNewsRepo> getAllNews();

}
