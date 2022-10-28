import UPDOWN.DOWN
import UPDOWN.UP
import kotlin.math.floor

class SmartElevator(private val el: Elevator) : Elevator by el {
  init {
    el.on(ElevatorEvent.IDLE) { onIdle() }
    el.on1<Int>(ElevatorEvent.STOPPED_AT_FLOOR) { onStopped(it) }
    el.on2<Int, String>(ElevatorEvent.PASSING_FLOOR) { f, d -> onPassing(f, if (d == "up") UP else DOWN) }
    el.on1<Int>(ElevatorEvent.FLOOR_BUTTON_PRESSED) { onButtonPressed(it) }
  }

  fun onIdle() {

    var dest = (0..numFloors)
      .map { (currentFloor() + it).rem(numFloors) }
      .firstOrNull { f ->
        f in getPressedFloors() || f in floorsWithWaiters
      }
      ?: 0

    goToFloor(dest)
    floorsWithWaiters.remove(dest)

  }

  fun onStopped(floorNum: Int) {
  }

  fun onPassing(floorNum: Int, direction: UPDOWN) {
  }

  fun onButtonPressed(floorNum: Int) {
  }
}

val floorsWithWaiters = mutableSetOf<Int>()
var numFloors: Int = 4
