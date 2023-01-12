package genadimk.bookreader.observer

interface Observable {
    val observers: MutableList<Observer>

    fun subscribe(observer: Observer)
    fun unsubscribe(observer: Observer)
    fun sendUpdateEvent()
}

class Broadcaster : Observable {
    private val _observers: MutableList<Observer> = mutableListOf()
    override val observers: MutableList<Observer>
        get() = _observers

    override fun subscribe(observer: Observer) {
        observers.add(observer)
    }

    override fun unsubscribe(observer: Observer) {
        observers.remove(observer)
    }

    override fun sendUpdateEvent() {
        observers.forEach { it.update() }
    }
}