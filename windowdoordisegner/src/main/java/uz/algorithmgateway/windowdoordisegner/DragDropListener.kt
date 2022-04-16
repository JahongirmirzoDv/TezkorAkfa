package uz.algorithmgateway.windowdoordisegner

import android.content.ClipData
import android.content.ClipDescription
import android.view.DragEvent
import android.view.View
import android.widget.Toast
import uz.algorithmgateway.windowdoordisegner.ui.Area
import uz.algorithmgateway.windowdoordisegner.ui.Square
import uz.algorithmgateway.windowdoordisegner.ui.door.Door
import uz.algorithmgateway.windowdoordisegner.ui.window.Window

/**
 * Created by Abrorjon Berdiyorov on 24.02.2022
 */
class DragAndDropListener : View.OnDragListener {

    companion object {
        const val TAG_DIV_HOR_THREE = "divide_three_horizontal"
        const val TAG_DIV_HOR_TWO = "divide_two_horizontal"
        const val TAG_DIV_VER_TWO = "divide_two_vertical"
        const val TAG_DOOR_TURN_LEFT = "door_turn_left"
        const val TAG_DOOR_TURN_RIGHT = "door_turn_right"
        const val TAG_PANEL_HOR = "panel_horizontal"
        const val TAG_PANEL_VER = "panel_vertical"
        const val TAG_GLASS = "glass"
        const val TAG_WINDOW_TURN_LEFT = "window_turn_left"
        const val TAG_WINDOW_TURN_RIGHT = "window_turn_right"
        const val TAG_WINDOW_TILT_TOP_TURN_LEFT = "window_tilt_top_turn_left"
        const val TAG_WINDOW_TILT_TOP_TURN_RIGHT = "window_tilt_top_turn_right"
        const val TAG_WINDOW_TILT_TOP = "window_tilt_top"
    }

    override fun onDrag(view: View, event: DragEvent): Boolean {
        return when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION ->
                true
            DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                // Gets the item containing the dragged data.
                val item: ClipData.Item = event.clipData.getItemAt(0)

                // Gets the text data from the item.
                val dragData = item.text

                // Displays a message containing the dragged data.
                Toast.makeText(view.context, "Dragged data is $dragData", Toast.LENGTH_LONG).show()

                val destination = view as Area

                when (dragData) {
                    TAG_DIV_HOR_THREE -> {
                        destination.divideHorizontalThree()
                    }
                    TAG_DIV_HOR_TWO -> {
                        destination.divideHorizontalTwo()
                    }
                    TAG_DIV_VER_TWO -> {
                        destination.divideVerticalTwo()
                    }
                    TAG_DOOR_TURN_LEFT -> {
                        destination.addDoor(Door.Companion.DoorOrientation.TURN_LEFT)
                    }
                    TAG_DOOR_TURN_RIGHT -> {
                        destination.addDoor(Door.Companion.DoorOrientation.TURN_RIGHT)
                    }
                    TAG_PANEL_HOR -> {
                        destination.changeInsideSquare(Square.Companion.InsideTypes.HORIZONTAL_PANEL)
                    }
                    TAG_PANEL_VER -> {
                        destination.changeInsideSquare((Square.Companion.InsideTypes.VERTICAL_PANEL))
                    }
                    TAG_GLASS -> {
                        destination.changeInsideSquare((Square.Companion.InsideTypes.GLASS))
                    }
                    TAG_WINDOW_TILT_TOP -> {
                        destination.addWindow(Window.Companion.WindowOrientation.TILT_TOP)
                    }
                    TAG_WINDOW_TILT_TOP_TURN_LEFT -> {
                        destination.addWindow(Window.Companion.WindowOrientation.TILT_TOP_AND_TURN_LEFT)
                    }
                    TAG_WINDOW_TILT_TOP_TURN_RIGHT -> {
                        destination.addWindow(Window.Companion.WindowOrientation.TILT_TOP_AND_TURN_RIGHT)
                    }
                    TAG_WINDOW_TURN_LEFT -> {
                        destination.addWindow(Window.Companion.WindowOrientation.TURN_LEFT)
                    }
                    TAG_WINDOW_TURN_RIGHT -> {
                        destination.addWindow(Window.Companion.WindowOrientation.TURN_RIGHT)
                    }
                }


                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()
                true
            }
            else -> false
        }
    }
}