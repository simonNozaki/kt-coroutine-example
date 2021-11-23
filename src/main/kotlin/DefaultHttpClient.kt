import org.apache.http.NameValuePair
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpClient.Version
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 * HTTPクライアント基本実装クラス
 */
class DefaultHttpClient : IHttpClient {
    private val httpClient = HttpClient.newBuilder()
        .version(Version.HTTP_1_1)
        .build()
    private var uri = ""
    private var rawBody = ""

    override fun uri(uri: String): IHttpClient = apply {
        this.uri = uri
    }

    override fun get(): IHttpClient = apply {
        if (this.uri == "") {
            throw RuntimeException("URIが設定されていません")
        }
        val httpRequest = HttpRequest.newBuilder(URI.create(uri))
            .GET()
        this.rawBody = this.httpClient
            .send(httpRequest.build(), HttpResponse.BodyHandlers.ofString())
            .body()
    }

    override fun <T> post(body: T): IHttpClient = apply {
        if (this.uri == "") {
            throw RuntimeException("URIが設定されていません")
        }
        val httpRequest = HttpRequest.newBuilder(URI.create(this.uri))
            .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(body)))
            .build()
        this.rawBody = this.httpClient
            .send(httpRequest, HttpResponse.BodyHandlers.ofString())
            .body()
    }

    override fun <V> bind(type: Class<V>): V {
        return gson.fromJson(this.rawBody, type)
    }
}