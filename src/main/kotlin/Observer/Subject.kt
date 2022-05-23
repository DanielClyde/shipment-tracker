package Observer

abstract class Subject {
    protected val observers = mutableListOf<Observer>()
    fun addObserver(observer: Observer) { observers.add(observer) }
    fun removeObserver(observer: Observer) { observers.remove(observer) }
    protected fun notifyObservers() {
        for (obs in observers) {
            obs.update()
        }
    }
}