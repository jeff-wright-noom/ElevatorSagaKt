
@JsExport // Tell Kotlin/JS to add this class to our module, so we can instantiate one as the last step
class ElevatorCallbacks {
  @JsName("init")
  fun init(elevators: Array<Elevator>, floors: Array<Floor>) {
    numFloors = floors.size

    elevators.mapIndexed { idx, el ->
      SmartElevator(el)
    }

    floors.forEach { floor ->
      floor.on(FloorEvent.UP_BUTTON_PRESSED) {
        floorsWithWaiters.add(floor.floorNum())
        //floorStates[floor.floorNum() to UPDOWN.UP] = true
      }
      floor.on(FloorEvent.DOWN_BUTTON_PRESSED) {
        floorsWithWaiters.add(floor.floorNum())
        //floorStates[floor.floorNum() to UPDOWN.DOWN] = true
      }
    }
  }

  @JsName("update")
  fun update(dt: dynamic, elevators: dynamic, floors: dynamic) {
  }

  private val floorStates : MutableMap<Pair<Int, UPDOWN>, Boolean> = mutableMapOf()

  private fun lowestPressedFloor() : Int {
    return floorStates.filter { (k, v) -> v == true }
      .keys
      .minOfOrNull { it.first }
      ?: 4
  }
}

enum class UPDOWN { UP, DOWN }
