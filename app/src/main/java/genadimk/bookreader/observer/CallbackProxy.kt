package genadimk.bookreader.observer

class CallbackProxy : Observable {
    private var observer: Observer? = null

    override fun subscribe(observer: Observer) {
        if (this.observer == null)
            this.observer = observer
        else return
    }

    override fun unsubscribe(observer: Observer) {
        this.observer = null
    }

    override fun sendUpdateEvent(args: Observable.Arguments?) {
        observer?.update(args)
    }
}