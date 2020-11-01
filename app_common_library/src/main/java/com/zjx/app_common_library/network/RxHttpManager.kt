package com.zjx.app_common_library.network

import com.blankj.utilcode.util.Utils
import com.zjx.app_common_library.BuildConfig
import com.zjx.app_common_library.constant.AppConstant
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.cookie.CookieStore
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.ssl.HttpsUtils
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSession

/**
 * 本类所有配置都是非必须的，根据自己需求选择就好
 * User: ljx
 * Date: 2019-11-26
 * Time: 20:44
 */
object RxHttpManager {
    //    @Converter(name = "XmlConverter")  //非必须
    //    public static IConverter xmlConverter = XmlConverter.create();
    //    @Converter(name = "FastJsonConverter")  //非必须
    //    public static IConverter fastJsonConverter = FastJsonConverter.create();
    //
    //    @OkClient(name = "SimpleClient", className = "Simple")  //非必须
    //    public static OkHttpClient simpleClient = new OkHttpClient.Builder().build();
    fun init() {
        //cookie缓存目录
        val file = File(Utils.getApp().externalCacheDir, AppConstant.APP_HTTP_COOKIE_CACHE_DIR)
        val sslParams = HttpsUtils.getSslSocketFactory()
        val client = OkHttpClient.Builder()
            .cookieJar(CookieStore(file))
            .connectTimeout(AppConstant.APP_HTTP_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(AppConstant.APP_HTTP_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(AppConstant.APP_HTTP_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
            .hostnameVerifier { hostname: String?, session: SSLSession? -> true } //忽略host验证
            //            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
            //            .addInterceptor(new RedirectInterceptor())
            //            .addInterceptor(new TokenInterceptor())
            .build()
        //RxHttp初始化，自定义OkHttpClient对象，非必须
        RxHttp.init(client, BuildConfig.DEBUG)
        //Http缓存目录
        val cacheFile = File(Utils.getApp().externalCacheDir, AppConstant.APP_HTTP_CACHE_DIR)
        //设置缓存策略，非必须 （当前设置：先请求网络，失败后再读取缓存  (网络请求成功，写缓存)）
        RxHttpPlugins.setCache(
            cacheFile,
            1024 * 100.toLong(),
            CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
        )
        //        RxHttpPlugins.setExcludeCacheKeys("time"); //设置一些key，不参与cacheKey的组拼
        //设置数据解密/解码器，非必须
//        RxHttp.setResultDecoder(s -> s);
        //设置全局的转换器，非必须
//        RxHttp.setConverter(FastJsonConverter.create());
        //设置公共参数，非必须
//        RxHttp.setOnParamAssembly(p -> {
//            /*根据不同请求添加不同参数，子线程执行，每次发送请求前都会被回调
//            如果希望部分请求不回调这里，发请求前调用Param.setAssemblyEnabled(false)即可
//             */
//            Method method = p.getMethod();
//            if (method.isGet()) { //Get请求
//
//            } else if (method.isPost()) { //Post请求
//
//            }
//            return p.add("versionName", "1.0.0")//添加公共参数
//                    .add("time", System.currentTimeMillis())
//                    .addHeader("deviceType", "android"); //添加公共请求头
//        });
    }
}