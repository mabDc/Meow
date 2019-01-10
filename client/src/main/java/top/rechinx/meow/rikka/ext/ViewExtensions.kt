package top.rechinx.meow.rikka.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes


/**
 * View visibility extensions
 */
inline fun View.visible() {
    visibility = View.VISIBLE
}

inline fun View.inVisible() {
    visibility = View.INVISIBLE
}

inline fun View.gone() {
    visibility = View.GONE
}

/**
 * Set view visibility by check condition [block]
 */
inline fun View.visibleIf(block: () -> Boolean) {
    visibility = if (block()) View.VISIBLE else View.GONE
}

/**
 * ViewGroup extension for inflate a layout file
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}