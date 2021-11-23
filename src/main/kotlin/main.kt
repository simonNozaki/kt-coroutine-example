import java.util.logging.Logger


val logger: Logger = Logger.getLogger("kt-coroutine-example")

fun main(args: Array<String>) {
    App(DefaultHttpClient()).execute()
}

