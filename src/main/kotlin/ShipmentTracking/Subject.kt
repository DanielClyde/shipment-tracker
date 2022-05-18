package ShipmentTracking

abstract class Subject {
    private val observers: MutableList<Observer> = mutableListOf();
    abstract fun registerObserver(obs: Observer);
    abstract fun removeObserver(obs: Observer);
    private fun notifyObservers() {
        for (obs in observers) {
            obs.onUpdate();
        }
    }
}