package my.mvp.view

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.animation.LinearInterpolator

class CustomView(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs),
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private val gestureDetector = GestureDetector(context, this)
    private var isRotating = false
    private val rotationAnimator: ObjectAnimator =
        ObjectAnimator.ofFloat(this, "rotation", 0f, 360f).apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
        }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_UP) {
            performClick()
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        if (isRotating) {
            rotationAnimator.cancel()
            this.rotation = 0f
        } else {
            rotationAnimator.start()
        }
        isRotating = !isRotating
        return true
    }

    override fun onDown(e: MotionEvent): Boolean = false
    override fun onShowPress(e: MotionEvent) {}
    override fun onSingleTapUp(e: MotionEvent): Boolean = false
    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean = false
    override fun onLongPress(e: MotionEvent) {}
    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean = false
    override fun onSingleTapConfirmed(e: MotionEvent): Boolean = false
    override fun onDoubleTapEvent(e: MotionEvent): Boolean = false
}
