/**
 * 汎用HTTPクライアントインターフェース
 */
interface IHttpClient {
    /**
     * ターゲットURI
     * @param uri
     */
    fun uri(uri: String): IHttpClient

    /**
     * GETリクエスト
     */
    fun get(): IHttpClient

    /**
     * POSTリクエストの実行
     * @param body
     * @param T
     */
    fun <T> post(body: T): IHttpClient

    /**
     * レスポンスを任意の型にバインド
     * @param type
     * @param V
     */
    fun <V> bind(type: Class<V>): V
}