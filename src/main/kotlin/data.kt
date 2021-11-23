import java.util.UUID

data class KV(
    val key: String,
    val value: String
)

/**
 * IDオブジェクト
 */
data class Id(
    val value: String
) {
    companion object {
        fun create(): Id = Id(UUID.randomUUID().toString())
    }
}

/**
 * 名前オブジェクト
 */
data class Name(
    val value: String
)

/**
 * 人間オブジェクト
 */
data class Person(
    val id: Id,
    val name: Name
)