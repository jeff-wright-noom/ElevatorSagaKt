external interface Elevator {
  fun on(event: String, handler: () -> Unit)
  @JsName("on")
  fun <T> on1(event: String, handler: (T) -> Unit)
  @JsName("on")
  fun <T, U> on2(event: String, handler: (T, U) -> Unit)

  /**
   * Queue the elevator to go to specified floor number. If you specify true as second argument, the elevator will go to that floor directly, and then go to any other queued floors.
   */
  fun goToFloor(floor: Int)
  fun goToFloor(floor: Int, immediately: Boolean)

  /**
   * Clear the destination queue and stop the elevator if it is moving. Note that you normally don't need to stop elevators - it is intended for advanced solutions with in-transit rescheduling logic. Also, note that the elevator will probably not stop at a floor, so passengers will not get out.
   */
  fun stop()

  /**
   * Gets the floor number that the elevator currently is on.
   */
  fun currentFloor(): Int

  /**
   * Gets or sets the going up indicator, which will affect passenger behaviour when stopping at floors.
   */
  @JsName("goingUpIndicator")
  fun getGoingUpIndicator()

  @JsName("goingUpIndicator")
  fun setGoingUpIndicator(goingUp: Boolean)

  /**
   * Gets or sets the going down indicator, which will affect passenger behaviour when stopping at floors.
   */
  @JsName("goingDownIndicator")
  fun getGoingDownIndicator()

  @JsName("goingDownIndicator")
  fun setGoingDownIndicator(goingDown: Boolean)

  /**
   * Gets the maximum number of passengers that can occupy the elevator at the same time.
   */
  fun maxPassengerCount()

  /**
   * Gets the load factor of the elevator. 0 means empty, 1 means full. Varies with passenger weights, which vary - not an exact measure.
   */
  fun loadFactor(): Float

  /**
   * Gets the direction the elevator is currently going to move toward. Can be "up", "down" or "stopped".
   */
  fun destinationDirection()

  /**
   * The current destination queue, meaning the floor numbers the elevator is scheduled to go to. Can be modified and emptied if desired. Note that you need to call checkDestinationQueue() for the change to take effect immediately.
   */
  var destinationQueue: Array<Int>

  /**
   * Checks the destination queue for any new destinations to go to. Note that you only need to call this if you modify the destination queue explicitly.
   */
  fun checkDestinationQueue()

  /**
   * Gets the currently pressed floor numbers as an array.
   */
  fun getPressedFloors(): Array<Int>
}

object ElevatorEvent {
  /**
   * Triggered when the elevator has completed all its tasks and is not doing anything.
   */
  val IDLE = "idle" // () -> Unit

  /**
   * Triggered when a passenger has pressed a button inside the elevator.
   */
  val FLOOR_BUTTON_PRESSED = "floor_button_pressed" // (floorNum) -> Unit

  /**
   * Triggered slightly before the elevator will pass a floor. A good time to decide whether to stop at that floor. Note that this event is not triggered for the destination floor. Direction is either "up" or "down".
   */
  val PASSING_FLOOR = "passing_floor" // (floorNum, "up" or "down") -> Unit

  /**
   * Triggered when the elevator has arrived at a floor.
   */
  val STOPPED_AT_FLOOR = "stopped_at_floor" // (floorNum) -> Unit
}


external class Floor {
  fun on(event: String, handler: () -> Unit)

  /**
   * Gets the floor number of the floor object.
   */
  fun floorNum(): Int
}

object FloorEvent {
  /**
   * Triggered when someone has pressed the up button at a floor. Note that passengers will press the button again if they fail to enter an elevator.
   */
  val UP_BUTTON_PRESSED = "up_button_pressed"

  /**
   * Triggered when someone has pressed the down button at a floor. Note that passengers will press the button again if they fail to enter an elevator.
   */
  val DOWN_BUTTON_PRESSED = "down_button_pressed"
}
