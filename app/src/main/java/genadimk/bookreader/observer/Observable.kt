package genadimk.bookreader.observer

interface Observable {
//    val observers: MutableList<Observer>
//
//    fun subscribe(observer: Observer)
//    fun unsubscribe(observer: Observer)
//    fun sendUpdateEvent()
    val observers: MutableList<()->Unit>

    fun subscribe(newOpr: () -> Unit)
    fun unsubscribe(opr: () -> Unit)
    fun sendUpdateEvent()
}

class Broadcaster : Observable {
//    private val _observers: MutableList<Observer> = mutableListOf()
//    override val observers: MutableList<Observer>
//        get() = _observers

    private val _observers: MutableList<() -> Unit> = mutableListOf()
    override val observers: MutableList<() -> Unit>
        get() = _observers

    override fun subscribe(newOpr: () -> Unit) {
        observers.add(newOpr)
    }

    override fun unsubscribe(opr: () -> Unit) {
        observers.remove(opr)
    }

    override fun sendUpdateEvent() {
        observers.forEach { it.invoke() }
    }
}