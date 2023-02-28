package genadimk.bookreader.observer

class BroadcasterProxy : Observable {
    private val _observers: MutableList<Observer> = mutableListOf()
    private val observers: List<Observer>
        get() = _observers.toList()

    override fun subscribe(observer: Observer) {
        _observers.add(observer)
    }

    override fun unsubscribe(observer: Observer) {
        _observers.remove(observer)
    }

    override fun sendUpdateEvent(args: Observable.Arguments?) {
        observers.forEach { it.update(args) }
    }
}