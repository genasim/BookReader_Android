package genadimk.bookreader.observer

interface Observable {
    val observers: List<Observer>

    fun subscribe(observer: Observer)

    fun unsubscribe(observer: Observer)

    fun sendUpdateEvent()

    data class Arguments(
        val args: List<Any>
    )
}

class Broadcaster : Observable {
    private val _observers: MutableList<Observer> = mutableListOf()
    override val observers: List<Observer>
        get() = _observers.toList()

    override fun subscribe(observer: Observer) {
        _observers.add(observer)
    }

    override fun unsubscribe(observer: Observer) {
        _observers.remove(observer)
    }

    override fun sendUpdateEvent() {
        observers.forEach { it.update() }
    }
}