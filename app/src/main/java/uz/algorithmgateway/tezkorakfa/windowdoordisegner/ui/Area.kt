package uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.get
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.DragAndDropListener
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.door.Door
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.door.DoorLayout
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.window.Window
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.window.WindowLayout
import uz.algorithmgateway.windowdoordisegner.ui.Square
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class Area @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defaultAttrs: Int = 0,
) : LinearLayout(ctx, attrs, defaultAttrs) {

    private var childLayout: LinearLayout? = null
    private var viewScale: Float = 0f
    private var W: Int = 0
    private var H: Int = 0
    var canAddDoorOrWindow = false

    init {

        inflate(ctx, R.layout.file_disegner, this)

        setBackgroundColor(Color.WHITE)

        childLayout = findViewById<View>(R.id.child_layout) as LinearLayout

        setOnDragListener(DragAndDropListener())

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addSquare()
    }


    private fun addSquare() {
        val square = Square(ctx)
        square.setH(H)
        square.setW(W)
        square.setViewScale(viewScale)
        square.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        childLayout?.addView(square, 0)
    }

    fun setW(number: Int?) {
        if (number != null && number >= 0) {
            W = number
            invalidate()
        }
    }

    fun setH(number: Int?) {
        if (number != null && number >= 0) {
            H = number
            invalidate()
        }
    }

    fun setViewScale(number: Float?) {
        if (number != null && number >= 0f) {
            viewScale = number
            invalidate()
        }
    }

    fun addDoor(orientation: Door.Companion.DoorOrientation) {
        if (canAddDoorOrWindow) {
            val door = DoorLayout(ctx)
            door.setViewScale(viewScale)
            door.setH(H)
            door.setW(W)
            door.setOrientation(orientation)
            door.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

            childLayout?.removeAllViews()
            childLayout?.addView(door)
        }
    }

    fun addWindow(orientation: Window.Companion.WindowOrientation) {
        if (canAddDoorOrWindow) {
            val window = WindowLayout(ctx)
            window.setViewScale(viewScale)
            window.setW(W)
            window.setH(H)
            window.setOrientation(orientation)
            window.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

            childLayout?.removeAllViews()
            childLayout?.addView(window)
        }
    }

    fun removeWindow() {
        childLayout = findViewById<View>(R.id.child_layout) as LinearLayout
        val saveImage = childLayout?.let { saveImage(it) }
        saveImage?.let { saveBitmap(it, "rasm") }
    }

    private fun saveImage(view: View): Bitmap {
        val specWidth =
            View.MeasureSpec.makeMeasureSpec(1324, View.MeasureSpec.AT_MOST)
        val specHeight =
            View.MeasureSpec.makeMeasureSpec(521, View.MeasureSpec.AT_MOST)
        view.measure(specWidth, specHeight)
        val width = 1920
        val height = 1080
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(canvas)
        return bitmap
    }

    @Throws(IOException::class)
    private fun saveBitmap(bitmap: Bitmap, name: String) {
        val saved: Boolean
        val fos: OutputStream?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver: ContentResolver = ctx.contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/Camera")
            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).toString() + File.separator + "Camera"
            val file = File(imagesDir)
            if (!file.exists()) {
                file.mkdir()
            }
            val image = File(imagesDir, "$name.png")
            fos = FileOutputStream(image)
        }
        saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos?.flush()
        fos?.close()
    }



    fun divideHorizontalTwo() {
        val layout = LayoutInflater.from(ctx)
            .inflate(R.layout.item_two_square_horizontal, this, false)

        val subArea1 = layout.findViewById<View>(R.id.sub_layout_1) as Area
        val subArea2 = layout.findViewById<View>(R.id.sub_layout_2) as Area

        val mullion = layout.findViewById<View>(R.id.mullion) as Mullion

        val halfW: Int = (W - Mullion.thickness) / 2
        subArea1.setViewScale(this.viewScale)
        subArea2.setViewScale(this.viewScale)
        subArea1.setW(halfW)
        subArea2.setW(W - Mullion.thickness - halfW)
        subArea1.setH(this.H)
        subArea2.setH(this.H)
        subArea1.canAddDoorOrWindow = canAddDoorOrWindow
        subArea2.canAddDoorOrWindow = canAddDoorOrWindow

        mullion.setViewScale(viewScale)

        childLayout?.removeAllViews()
        childLayout?.addView(layout)
    }

    fun divideVerticalTwo() {
        val layout = LayoutInflater.from(ctx)
            .inflate(R.layout.item_two_square_vertical, this, false)

        val subArea1 = layout.findViewById<View>(R.id.sub_layout_1) as Area
        val subArea2 = layout.findViewById<View>(R.id.sub_layout_2) as Area

        val mullion = layout.findViewById<View>(R.id.mullion) as Mullion

        val halfH: Int = (H - Mullion.thickness) / 2
        subArea1.setViewScale(viewScale)
        subArea2.setViewScale(viewScale)
        subArea1.setW(W)
        subArea2.setW(W)
        subArea1.setH(halfH)
        subArea2.setH(H - halfH - Mullion.thickness)
        subArea1.canAddDoorOrWindow = canAddDoorOrWindow
        subArea2.canAddDoorOrWindow = canAddDoorOrWindow

        mullion.setViewScale(viewScale)

        childLayout?.removeAllViews()
        childLayout?.addView(layout)
    }

    fun divideHorizontalThree() {
        val layout = LayoutInflater.from(ctx)
            .inflate(R.layout.item_three_square_horizontal, this, false)

        val subArea1 = layout.findViewById<View>(R.id.sub_layout_1) as Area
        val subArea2 = layout.findViewById<View>(R.id.sub_layout_2) as Area
        val subArea3 = layout.findViewById<View>(R.id.sub_layout_3) as Area

        val mullion1 = layout.findViewById<View>(R.id.mullion_1) as Mullion
        val mullion2 = layout.findViewById<View>(R.id.mullion_2) as Mullion

        val squareW: Int = (W - 2 * Mullion.thickness) / 3
        subArea1.setViewScale(viewScale)
        subArea2.setViewScale(viewScale)
        subArea3.setViewScale(viewScale)
        subArea1.setW(squareW)
        subArea2.setW(squareW)
        subArea3.setW(W - 2 * Mullion.thickness - 2 * squareW)
        subArea1.setH(H)
        subArea2.setH(H)
        subArea3.setH(H)
        subArea1.canAddDoorOrWindow = canAddDoorOrWindow
        subArea2.canAddDoorOrWindow = canAddDoorOrWindow
        subArea3.canAddDoorOrWindow = canAddDoorOrWindow


        mullion1.setViewScale(viewScale)
        mullion2.setViewScale(viewScale)

        childLayout?.removeAllViews()
        childLayout?.addView(layout)
    }

    fun changeInsideSquare(insideType: Square.Companion.InsideTypes) {
        val square = childLayout?.get(0) as Square?
        square?.changeInsideType(insideType)
    }
}