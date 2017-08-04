package co.rgp.rgptest;

import co.rgp.rgptest.Const.constValue;
import co.rgp.rgptest.Vo.searchUserVo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bslee on 2017-08-03.
 */

public interface GithubApiInfo {

    //글 가져오기
    @GET(constValue.USER)
    Call<searchUserVo> searchUser(@Query("q") String user, @Query("page") int page, @Query("per_page") int per_page);
}
