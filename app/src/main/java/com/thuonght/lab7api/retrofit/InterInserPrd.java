package com.thuonght.lab7api.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterInserPrd {
    @FormUrlEncoded
    @POST("create_product.php")
    Call<SvrResponsePrd> insertPrd(
            @Field("name") String name,
            @Field("price") String price,
            @Field("description") String description
    );
}
