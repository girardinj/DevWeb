package ch.hearc.gcapp.garbages.categories.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText

import ch.hearc.gcapp.R
import ch.hearc.gcapp.garbages.GarbageStore
import ch.hearc.gcapp.garbages.categories.GarbageCategory

/**
 * Show the form for a new garbage category.
 *
 * Within this activity, we can:
 *
 * - create a new garbage category.
 */
class AddGarbageCategoryActivity : AppCompatActivity() {

    private var addGarbageCategoryButton: Button? = null

    private var nameEditText: EditText? = null
    private var trashCanEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_garbage_category_activity)

        retrieveViews()
        setUpViews()
    }

    /**
     * Retrieve all views inside res/layout/add_garbage_category_activity.xml.
     */
    private fun retrieveViews() {
        addGarbageCategoryButton = findViewById<View>(R.id.addGarbageCategoryButton) as Button
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        trashCanEditText = findViewById<View>(R.id.trashCanEditText) as EditText
    }

    /**
     * Construct our logic. What we wants is the following:
     *
     * - being able to create a new garbage by clicking the "Add category" button.
     */
    private fun setUpViews() {
        // Create our garbage category when clicking the "Add category" button
        addGarbageCategoryButton!!.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(nameEditText!!.text) || TextUtils.isEmpty(trashCanEditText!!.text)) {
                // Do not continue if one field is missing data

                return@OnClickListener
            }

            val garbageCategory = GarbageCategory(
                nameEditText!!.text.toString(),
                trashCanEditText!!.text.toString()
            )

            GarbageStore.GARBAGE_CATEGORIES.add(garbageCategory)

            finish()
        })
    }
}
