package ch.hearc.gcapp.garbages.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

import ch.hearc.gcapp.R
import ch.hearc.gcapp.garbages.GarbageStore

/**
 * Show the details for a garbage.
 *
 * Withing this activity, we can... do nothing, except reading.
 */
class GarbageDetailsActivity : AppCompatActivity() {

    private var garbageDetailsTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.garbage_details_activity)

        retrieveViews()
        setUpViews()
    }

    /**
     * Retrieve all views inside res/layout/garbage_details_activity.xml.
     */
    private fun retrieveViews() {
        garbageDetailsTextView = findViewById<View>(R.id.garbageDetailsTextView) as TextView
    }

    /**
     * Construct our logic.
     *
     * No listener to create, only populate fields.
     */
    private fun setUpViews() {
        val garbageName = intent.getStringExtra("garbageName")

        if (garbageName == null) {
            Log.e(TAG, "Error while retrieving the garbage.")

            finish()
        }

        val garbage = GarbageStore.findGarbageByName(garbageName)

        garbageDetailsTextView!!.text = String.format(
            resources.getString(R.string.garbage_details),
            garbage!!.name,
            garbage.category!!.name,
            garbage.category!!.garbageCanName
        )
    }

    companion object {

        private val TAG = GarbageDetailsActivity::class.java.simpleName
    }
}
