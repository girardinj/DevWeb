package ch.hearc.gcapp.garbages.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

import java.util.ArrayList

import ch.hearc.gcapp.R
import ch.hearc.gcapp.garbages.GarbageStore

/**
 * Our garbage category adapter for list views containing garbage categories.
 *
 * We want to display the following text per row:
 *
 * - GARBAGE_CATEGORY_NAME (GARBAGE_CAN_NAME)
 */
class GarbageCategoryAdapter(
    /**
     * The context (activity) in which this adapter is used.
     */
    private val context: Context
) : BaseAdapter(), Filterable {

    /**
     * The filtered garbage categories; the garbage categories currently shown on the list.
     */
    private var filteredGarbageCategories: MutableList<GarbageCategory>? = null

    /**
     * The filter object, handling the filtering of our garbage categories.
     */
    private var garbageCategoryFilter: Filter? = null

    init {

        construct()
    }

    private fun construct() {
        // Display all garbage categories by default
        filteredGarbageCategories = GarbageStore.GARBAGE_CATEGORIES

        // Create our garbage categories' filter
        garbageCategoryFilter = object : Filter() {

            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                if (constraint == null) {
                    // No constraint -> show all

                    filteredGarbageCategories = GarbageStore.GARBAGE_CATEGORIES
                } else {
                    filteredGarbageCategories = ArrayList()

                    for (garbageCategory in GarbageStore.GARBAGE_CATEGORIES) {
                        // Filter by garbage category name

                        if (garbageCategory.name!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredGarbageCategories!!.add(garbageCategory)
                        }
                    }
                }

                val filterResults = Filter.FilterResults()

                filterResults.values = filteredGarbageCategories

                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                // New filtering -> notify the list adapter (BaseAdapter) that its content changed
                notifyDataSetChanged()
            }
        }
    }

    override fun getCount(): Int {
        return filteredGarbageCategories!!.size
    }

    override fun getItem(position: Int): Any {
        return filteredGarbageCategories!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: GarbageCategoryHolder

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.garbage_category_list_row, parent, false)

            holder = GarbageCategoryHolder()

            holder.nameTextView = convertView!!.findViewById<View>(R.id.nameTextView) as TextView
            holder.garbageCanTextView = convertView.findViewById<View>(R.id.garbageCanTextView) as TextView

            /*
             * We have 2 views, but we only can set one object (tag) into our convertView object.
             *
             * Now we see the whole purpose of our "XyzHolder" wrapper classes.
             */
            convertView.tag = holder
        } else {
            holder = convertView.tag as GarbageCategoryHolder
        }

        val garbageCategory = filteredGarbageCategories!![position]

        holder.nameTextView!!.text = garbageCategory.name
        holder.garbageCanTextView!!.text = String.format(
            context.resources.getString(R.string.garbage_can_list_row),
            garbageCategory.garbageCanName
        )

        return convertView
    }

    override fun getFilter(): Filter? {
        return garbageCategoryFilter
    }

    /**
     * Wrapper class for our garbage category views.
     */
    private class GarbageCategoryHolder {
        internal var nameTextView: TextView? = null
        internal var garbageCanTextView: TextView? = null
    }
}
