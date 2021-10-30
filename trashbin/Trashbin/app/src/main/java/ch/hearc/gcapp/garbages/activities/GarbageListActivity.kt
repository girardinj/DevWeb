package ch.hearc.gcapp.garbages.activities

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
import ch.hearc.gcapp.garbages.Garbage
import ch.hearc.gcapp.garbages.GarbageAdapter

/**
 * Show the garbage list.
 *
 * Within this activity, we can:
 *
 * - see all garbages in our application;
 * - filter the garbages with a search view;
 * - start an intent to create a garbage;
 * - start an intent to see a garbage details.
 */
class GarbageListActivity : AppCompatActivity() {

    private var addGarbageButton: Button? = null

    private var garbageListView: ListView? = null

    private var garbageSearchView: SearchView? = null

    private var garbageAdapter: GarbageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.garbage_list_activity)

        retrieveViews()
        setUpViews()
    }

    /**
     * Retrieve all views inside res/layout/garbage_list_activity.xml.
     */
    private fun retrieveViews() {
        addGarbageButton = findViewById<View>(R.id.addGarbageButton) as Button
        garbageListView = findViewById<View>(R.id.garbageListView) as ListView
        garbageSearchView = findViewById<View>(R.id.garbageSearchView) as SearchView
    }

    /**
     * Construct our logic. What we wants is the following:
     *
     * - being able to filter the garbage list;
     * - being able to see a garbage details by clicking an item in the list;
     * - being able to start the creation of a new garbage by clicking the "Add" button.
     */
    private fun setUpViews() {
        garbageAdapter = GarbageAdapter(this)

        // Tell by which adapter we will handle our list
        garbageListView!!.adapter = garbageAdapter

        // See a garbage details when clicking on it
        garbageListView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val garbage = garbageListView!!.getItemAtPosition(position) as Garbage

            val intent = Intent(this@GarbageListActivity, GarbageDetailsActivity::class.java)

            intent.putExtra("garbageName", garbage.name)

            startActivity(intent)
        }

        // Miscellaneous configuration for our search view
        garbageSearchView!!.isSubmitButtonEnabled = true
        garbageSearchView!!.queryHint = "Garbage name..."

        // The core for the search view: what to do when the text change!
        garbageSearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                // Do nothing when clicking the submit button (displayed ">") -> return false

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // When the text change, filter our list of garbages

                val filter = garbageAdapter!!.filter!!

                if (TextUtils.isEmpty(newText)) {
                    // Empty search field = no filtering
                    filter.filter(null)
                } else {
                    filter.filter(newText)
                }

                // Something was done -> return true instead of false
                return true
            }
        })

        // Start the activity to add a garbage when clicking the "Add" button
        addGarbageButton!!.setOnClickListener {
            val intent = Intent(this@GarbageListActivity, AddGarbageActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onResume() {
        // Important! Refresh our list when we return to this activity (from another one)
        garbageAdapter!!.notifyDataSetChanged()

        super.onResume()
    }
}
