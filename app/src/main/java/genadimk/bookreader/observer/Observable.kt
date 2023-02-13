package genadimk.bookreader.observer

interface Observable {
    fun subscribe(observer: Observer)

    fun unsubscribe(observer: Observer)

    fun sendUpdateEvent(args: Arguments? = null)

    data class Arguments(
        val args: Map<String, Any?>
    ) {
        operator fun get(key: String): Any? {
            return args[key]
        }
    }
}