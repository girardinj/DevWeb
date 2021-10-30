package ch.hearc.gcapp.garbages.categories.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Filter
import android.widget.ListView
import android.widget.SearchView

import ch.hearc.gcapp.R
import ch.hearc.gcapp.garbages.categories.GarbageCategory
import ch.hearc.gcapp.garbages.categories.GarbageCategoryAdapter

/**
 * Show the garbage category list.
 *
 * Within this activity, we can:
 *
 * - see all garbage categories in our application;
 * - filter the garbage categories with a search view;
 * - choose a category (return the result to the garbage list);
 * - start an intent to create a new garbage category.
 */
class ChooseGarbageCategoryActivity : AppCompatActivity() {

    private var addGarbageCategoryButton: Button? = null

    private var garbageCategoryListView: ListView? = null

    private var garbageCategorySearchView: SearchView? = null

    private var garbageCategoryAdapter: GarbageCategoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.choose_garbage_category_activity)

        retrieveViews()
        setUpViews()
    }

    /**
     * Retrieve all views inside res/layout/choose_garbage_category_activity.xml.
     */
    private fun retrieveViews() {
        addGarbageCategoryButton = findViewById<View>(R.id.addGarbageCategoryButton) as Button
        garbageCategoryListView = findViewById<View>(R.id.garbageCategoryListView) as ListView
        garbageCategorySearchView = findViewById<View>(R.id.garbageCategorySearchView) as SearchView
    }

    /**
     * Construct our logic. What we wants is the following:
     *
     * - being able to filter the garbage category list;
     * - being able to choose a garbage category to continue the creation of our new garbage;
     * - being able to add a new garbage category by clicking the "Add" button.
     */
    private fun setUpViews() {
        garbageCategoryAdapter = GarbageCategoryAdapter(this)

        // Tell by which adapter we will handle our list
        garbageCategoryListView!!.adapter = garbageCategoryAdapter

        garbageCategoryListView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val garbageCategory = garbageCategoryListView!!.getItemAtPosition(position) as GarbageCategory

            val intent = intent

            intent.putExtra("garbageCategoryName", garbageCategory.name)

            setResult(Activity.RESULT_OK, intent)

            finish()
        }

        // Miscellaneous configuration for our search view
        garbageCategorySearchView!!.isSubmitButtonEnabled = true
        garbageCategorySearchView!!.queryHint = "Category name..."

        // The core for the search view: what to do when the text change!
        garbageCategorySearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                // Do nothing when clicking the submit button (displayed ">") -> return false

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // When the text change, filter our list of garbage categories

                val filter = garbageCategoryAdapter!!.filter

                if (TextUtils.isEmpty(newText)) {
                    // Empty search field = no filtering
                    filter!!.filter(null)
                } else {
                    filter!!.filter(newText)
                }

                // Something was done -> return true instead of false
                return true
            }
        })

        // Start the activity to add a garbage category when clicking the "Add" button
        addGarbageCategoryButton!!.setOnClickListener {
            val intent = Intent(this@ChooseGarbageCategoryActivity, AddGarbageCategoryActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onResume() {
        // Important! Refresh our list when we return to this activity (from another one)
        garbageCategoryAdapter!!.notifyDataSetChanged()

        super.onResume()
    }
}
