package space.xiaoceng.pixiv.creawler


import cn.hutool.core.util.StrUtil
import org.apache.http.HttpHost
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.TrustStrategy
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import org.apache.http.util.EntityUtils
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

//@Component
class PixivRunner implements ApplicationRunner {
    @Override
    void run(ApplicationArguments args) throws Exception {
        def file = new File(args.getSourceArgs()[0])
        file.readLines("UTF-8").each {
            if (StrUtil.isBlank(it)) return
            runJob it
        }

    }
    def client = createSSLClientDefault()

    private void runJob(String url) {
        println url
        get url
    }

    private String get(String url) {
        def get = new HttpGet(url)
//        get.setHeader "authority", "www.pixiv.net"
//        get.setHeader "accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
        get.setHeader "Cookie", "login_ever=yes; first_visit_datetime_pc=2021-03-04+11:37:18; p_ab_id=9; p_ab_id_2=4; p_ab_d_id=138603080; yuid_b=GQZxg1I; _ga=GA1.2.547753102.1614825456; PHPSESSID=4100242_n9ZzQFcqhMQUno66fIcJHlNlYmCPEPUm; a_type=0; b_type=1; __utmv=235335808.|2=login ever=no=1^3=plan=normal=1^5=gender=male=1^6=user_id=4100242=1^9=p_ab_id=9=1^10=p_ab_id_2=4=1^11=lang=zh=1; adr_id=2M87GCvhqlgKjdHC8WmA3S5Pq20oi3L3gvlDZwgPCaiA8YaS; ki_s=214027:0.0.0.0.2; ki_r=; __utma=235335808.547753102.1614825456.1616638986.1617081592.14; ki_t=1614825771053;1617081595439;1617081694123;9;186; privacy_policy_notification=0; login_ever=yes; privacy_policy_agreement=3; c_type=35; __cf_bm=4hRPEnBHYP19QIV1nv7y1nbUrqMuBgz5n0tLTDco8bc-1654842413-0-AaieCq6tbBHn275Jv4SDc3cYwjdIwDjoBbATrYTHlDDU1iHj0cgcoFwp6rPYoUjuUW/MNfbIhlODGt1QTZZsjEH2F3tN406LNEteLna4lYX1; tag_view_ranking=0xsDLqCEW6~yUv36CqH7A~RTJMXD26Ak~fAloiTkhNQ~1DozjYpU3L~_hSAdpN9rx~n51s-79lX2~JXguVGpfOO~_uYw7rRMGo~kffGOewQdf~xP-fADzNOU~9d2KESXaym~pI5AVg4olJ~O0RTa3uxaa~pTej19Xt5X~4W3a9Pkd9S~ty-1tuj3tk~skG_89y2Br~7xvNFDSIDd~pGv7p05oAU~E3IgsUPkXu~engSCj5XFq~liM64qjhwQ~Ie2c51_4Sp~aTJy9_QGbQ~jvA9JeUmxT~rPVMorl3DQ~V00jq7NuIW~oSou9dlsDv~4Mfoeo5ek1~qKHPDN09e1~ETjPkL0e6r~q3eUobDMJW~h1WsTXPpcB~OTX4_u4Ie0~Vv3BMezZX0~yTfty1xk17~LLQ7jQZ8-m~GSarirD3Xl~Y4YauSPBrz~jjVAJCBCtW~dpVZy0tuWL~KStfxQxolW~EdT3eTEJ00~VYboF1_s4i~xgzhHEbOiJ~uC2yUZfXDc~ayjyWDaRVl~EThrUvPhiK~5oPIfUbtd6~c-PgtNcTwD~cIvr1j-nV5~A_su-3B4IX~utYkfreA8f~OeGEAgxZgp~dBhq3384HI~sAwDH104z0~EWR7JDW6jH~MWWoQ-PzdL~QL2G1t5h_V~wkeH5cxBys~rOnsP2Q5UN~wiFLKwksO3~itKHtm3ngJ~msnM7v3qDS~ms7VmZeFpM~a6iatBgjr5~Lt-oEicbBr~_jLJqnNLj9~Op8TUt7vri~ApHjjZo9OY~YpVnaoL4_F~3FC8USYldz~-7FomUpsNH~vJE0w_K6F_~wZ1vXN_Tjl~qWFESUmfEs~0M0zAeslDb~wBGL4weiaI~KdHn8SnH1y~SE3p4pb2d0~MM6RXH_rlN~Xyw8zvsyR4~faHcYIP1U0~AI_aJCDFn0~ZBoVMjk2oM~_EOd7bsGyl~QniSV1XRdS~YHRjLHL-7q~sqGkVxMuMR~qpHbTQdj1t~LnpvKwAQRa~z6i8Mevt17~_4ENo2IHhD~tWxm3U4n5x~413HwmflIA~AItiOtAvT5~CrFcrMFJzz~aV7Ke9FORn~_wzgPj1TuA; QSI_S_ZN_5hF4My7Ad6VNNAi=r:10:123"

        def res = client.execute get
        def str = EntityUtils.toString(res.getEntity())
        println str
        return str
    }

    static Proxy proxy() {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10800))
        return proxy
    }

    static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true
                }
            }).build()
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier)
            HttpHost pr = new HttpHost("localhost", 10800, "http")
            RequestConfig defaultRequestConfig = RequestConfig.custom().setProxy(pr).build()
            return HttpClients.custom()
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .setSSLSocketFactory(sslsf).build()
        } catch (Exception e) {
            throw new RuntimeException(e)
        }
    }


}
