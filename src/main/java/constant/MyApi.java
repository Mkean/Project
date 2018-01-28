package constant;

import bean.AddrBean;
import bean.Banner_Bean;
import bean.DelBean;
import bean.GoodsBean;
import bean.InsertAddrBean;
import bean.LeftBean;
import bean.NickBean;
import bean.OrdersBean;
import bean.Right;
import bean.ShopBean;
import bean.UpdateAddrBean;
import bean.UpdateBean;
import bean.UpdateOrdersBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者：王庆
 * 时间：2018/1/4
 */

public interface MyApi {
    @GET("user/updateNickName")
    Observable<NickBean> updateNick(@Query("uid") String uid, @Query("nickname") String nickname);


    @GET("user/getAddrs")
    Observable<AddrBean> getAddrList(@Query("uid") String uid);

    @GET("user/setAddr")
    Observable<UpdateAddrBean> updateAddr(@Query("uid") String uid, @Query("addrid") String addrid, @Query("status") String status);

    @GET("user/updateAddr")
    Observable<UpdateBean> addrUpdate(@Query("uid") String uid, @Query("addrid") String addrid, @Query("mobile") String mobile, @Query("name") String name, @Query("addr") String addr);

    @GET("user/addAddr")
    Observable<InsertAddrBean> insertAddr(@Query("uid") String uid, @Query("mobile") String mobile, @Query("name") String name, @Query("addr") String addr);




    @GET("ad/getAd")
    Observable<Banner_Bean> getBanner();

    @GET("product/getProductDetail")
    Observable<GoodsBean> getGoodsData(@Query("pid") String pid, @Query("source") String source);

    @GET("product/getCatagory")
    Observable<LeftBean> getLeftName();

    @GET("product/getOrders")
    Observable<OrdersBean> getOrders(@Query("uid") String uid, @Query("source") String source, @Query("page") int page);

    @GET("product/getProductCatagory")
    Observable<Right> getRightName(@Query("cid") int cid);

    @GET("product/getProducts")
    Observable<ShopBean> getShopData(@Query("pscid") int pscid);

    @GET("product/updateOrder")
    Observable<UpdateOrdersBean> updateOrders(@Query("uid") String uid, @Query("status") String status, @Query("orderId") String orderId);

    @GET("product/deleteCart")
    Observable<DelBean> delCart(@Query("uid") String uid, @Query("pid") String pid);
}
