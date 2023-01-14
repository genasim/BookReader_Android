package genadimk.bookreader.observer

interface Observer {
    fun update(args: Observable.Arguments? = null)
}