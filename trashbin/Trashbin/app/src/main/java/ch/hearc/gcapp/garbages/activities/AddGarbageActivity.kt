package ch.hearc.gcapp.garbages.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import ch.hearc.gcapp.garbages.Garbage
import ch.hearc.gcapp.garbages.GarbageStore
import ch.hearc.gcapp.garbages.categories.GarbageCategory
import ch.hearc.gcapp.garbages.categories.activities.ChooseGarbageCategoryActivity
import ch.hearc.gcapp.R

/**
 * Show the form for a new garbage.
 *
 * Within this activity, we can:
 *
 * - create a new garbage;
 * - start an intent to choose a garbage category.
 */
class AddGarbageActivity : AppCompatActivity() {

    private var addGarbageButton: Button? = null
    private var chooseCategoryButton: Button? = null

    private var nameEditText: EditText? = null

    private var categoryNameTextView: TextView? = null

    private var currentCategory: GarbageCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_garbage_activity)

        retrieveViews()
        setUpViews()
    }

    /**
     * Retrieve all views inside res/layout/add_garbage_activity.xml.
     */
    private fun retrieveViews() {
        addGarbageButton = findViewById<View>(R.id.addGarbageButton) as Button
        chooseCategoryButton = findViewById<View>(R.id.chooseCategoryButton) as Button
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        categoryNameTextView = findViewById<View>(R.id.categoryNameTextView) as TextView
    }

    /**
     * Construct our logic. What we wants is the following:
     *
     * - being able to choose a garbage category by clicking the "Choose category" button;
     * - being able to create a new garbage by clicking the "Add garbage" button.
     */
    private fun setUpViews() {
        // Create our garbage when clicking the "Add garbage" button
        addGarbageButton!!.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(nameEditText!!.text) || currentCategory == null) {
                // Do not continue if one field is missing data
                Log.w(TAG, "Unable to create a new garbage: one or more fields are empty.")

                return@OnClickListener
            }

            val garbage = Garbage(
                nameEditText!!.text.toString(),
                currentCategory!!
            )

            GarbageStore.GARBAGES.add(garbage)

            finish()
        })

        // Start the activity to choose a category when clicking the "Choose category" button
        chooseCategoryButton!!.setOnClickListener {
            val intent = Intent(this@AddGarbageActivity, ChooseGarbageCategoryActivity::class.java)

            startActivityForResult(intent, CHOOSE_CATEGORY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Here we retrieve our activity results
        if (requestCode == CHOOSE_CATEGORY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The user choose a garbage category and everything is ok

            val garbageCategoryName = data!!.getStringExtra("garbageCategoryName")

            if (garbageCategoryName != null) {
                currentCategory = GarbageStore.findGarbageCategoryByName(garbageCategoryName)

                categoryNameTextView!!.text = currentCategory!!.name
            }
        }
    }

    companion object {

        private val TAG = AddGarbageActivity::class.java.simpleName

        /**
         * Activity result code for "ChooseGarbageCategoryActivity".
         */
        private val CHOOSE_CATEGORY_REQUEST_CODE = 0
    }
}
