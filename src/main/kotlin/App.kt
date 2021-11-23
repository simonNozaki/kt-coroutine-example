import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * メインクラス
 */
class App(
    private val iHttpClient: IHttpClient
) {
    /**
     * アプリケーションを実行
     */
    fun execute() {
        val ids = mutableListOf<Id>()
        repeat(10) {
            ids.add(Id.create())
        }
        // スレッドをブロックしてコルーチンを起動
        val persons = blockingScope(ids)

        persons.forEach { println(it) }
    }

    private fun blockingScope(ids: List<Id>) = runBlocking<List<Person>> {
        logger.info("ブロッキング処理開始")
        // 時間のかかるコルーチンを起動しておく
        async {
            delay(2000L)
            logger.info("少し待つ...")
        }

        val deferred = mutableListOf<Deferred<Person>>()
        for (id: Id in ids) {
            val asyncScope: Deferred<Person> = async { return@async getKeyValue(id) }
            deferred.add(asyncScope)
        }

        // すべてのコルーチンを待ち受け、結果を格納する
        val responses = deferred.awaitAll()

        logger.info("ブロッキング処理終了")

        return@runBlocking responses
    }

    /**
     * APIをコールする待機可能関数
     */
    private suspend fun getKeyValue(id: Id) = coroutineScope {
//        val target = "https://raw.githubusercontent.com/simonNozaki/jsons/main/simple-kv.json"
        // 空文字にするとクライアントで実行時例外をスローする
        val target = ""
        logger.info("APIコール開始 => id: ${id.value}")
        val result: KV = try {
            iHttpClient
                .uri(target)
                .get()
                .bind(KV::class.java)
        } catch (e: Exception) {
            logger.severe("id: $id APIリクエストに失敗 => $e")
            KV(id.value, "")
        }
        logger.info("APIコール終了 => id: ${id.value}")
        return@coroutineScope Person(id, Name(result.value))
    }
}
