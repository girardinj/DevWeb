package ch.hearc.gcapp.garbages

import android.text.TextUtils

import ch.hearc.gcapp.garbages.categories.GarbageCategory

/**
 * POJO class representing garbages in this application.
 *
 * A garbage has:
 *
 * - a name;
 * - a category.
 */
class Garbage (name: String, category: GarbageCategory) {
/*------------------------------------------------------------------------------------------------*\
 *                                                                                                *
 *                                          CONSTRUCTORS                                          *
 *                                                                                                *
\*------------------------------------------------------------------------------------------------*/


    /*------------------------------------------------------------------------------------------------*\
 *                                                                                                *
 *                                           PROPERTIES                                           *
 *                                                                                                *
\*------------------------------------------------------------------------------------------------*/

    /**
     * The name for this garbage.
     */
    var name: String? = null

    /**
     * The category in which belongs this garbage.
     */
    var category: GarbageCategory? = null

    init {

        this.name = name
        this.category = category
    }

    /*------------------------------------------------------------------------------------------------*\
 *                                                                                                *
 *                                    OBJECT METHOD OVERRIDES                                     *
 *                                                                                                *
\*------------------------------------------------------------------------------------------------*/

    override fun equals(obj: Any?): Boolean {
        if (this === obj) {
            return true
        }

        if (obj !is Garbage) {
            return false
        }

        val other = obj as Garbage?

        return TextUtils.equals(name, other!!.name)
    }

    override fun hashCode(): Int {
        var result = 1

        result = 31 * result + if (name == null) 0 else name!!.hashCode()

        return result
    }
}
