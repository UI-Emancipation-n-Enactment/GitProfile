package demo.gitprofiles.repos.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import demo.gitprofiles.R

class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    isVisible: Boolean = false
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var customTView : TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_progress_bar, this, true)
        customTView = findViewById(R.id.customViewText)
        setTextVisible(isVisible)
    }

    fun setTextVisible(isVisible: Boolean) {
        customTView.visibility = if(isVisible) View.VISIBLE else View.GONE
    }

}