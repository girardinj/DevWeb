package ch.hearc.gcapp.garbages

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

/**
 * Our garbage adapter for list views containing garbages.
 *
 * We want to display the following text per row:
 *
 * - GARBAGE_NAME (GARBAGE_CATEGORY_NAME)
 */
class GarbageAdapter(
    /**
     * The context (activity) in which this adapter is used.
     */
    private val context: Context
) : BaseAdapter(), Filterable {

    /**
     * The filtered garbages; the garbages currently shown on the list.
     */
    private var filteredGarbages: MutableList<Garbage>? = null

    /**
     * The filter object, handling the filtering of our garbages.
     */
    private var garbageFilter: Filter? = null

    init {

        construct()
    }

    private fun construct() {
        // Display all garbages by default
        filteredGarbages = GarbageStore.GARBAGES

        // Create our garbages' filter
        garbageFilter = object : Filter() {

            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                if (constraint == null) {
                    // No constraint -> show all

                    filteredGarbages = GarbageStore.GARBAGES
                } else {
                    filteredGarbages = ArrayList()

                    for (garbage in GarbageStore.GARBAGES) {
                        // Filter by garbage name

                        if (garbage.name!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredGarbages!!.add(garbage)
                        }
                    }
                }

                val filterResults = Filter.FilterResults()

                filterResults.values = filteredGarbages

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
                // New filtering -> notify the list adapter (BaseAdapter) that its content changed
                notifyDataSetChanged()
            }
        }
    }

    override fun getCount(): Int {
        return filteredGarbages!!.size
    }

    override fun getItem(position: Int): Any {
        return filteredGarbages!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: GarbageHolder

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.garbage_list_row, parent, false)

            holder = GarbageHolder()

            holder.nameTextView = convertView!!.findViewById<View>(R.id.nameTextView) as TextView
            holder.categoryNameTextView = convertView.findViewById<View>(R.id.categoryNameTextView) as TextView

            /*
             * We have 2 views, but we only can set one object (tag) into our convertView object.
             *
             * Now we see the whole purpose of our "XyzHolder" wrapper classes.
             */
            convertView.tag = holder
        } else {
            holder = convertView.tag as GarbageHolder
        }

        val garbage = filteredGarbages!![position]

        holder.nameTextView!!.text = garbage.name
        holder.categoryNameTextView!!.text = String.format(
            context.resources.getString(R.string.garbage_category_list_row),
            garbage.category!!.name
        )

        return convertView
    }

    override fun getFilter(): Filter? {
        return garbageFilter
    }

    /**
     * Wrapper class for our garbage views.
     */
    private class GarbageHolder {
        internal var nameTextView: TextView? = null
        internal var categoryNameTextView: TextView? = null
    }
}
