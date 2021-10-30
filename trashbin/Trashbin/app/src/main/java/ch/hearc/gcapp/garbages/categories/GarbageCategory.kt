package ch.hearc.gcapp.garbages.categories

import android.text.TextUtils

/**
 * POJO class representing garbage categories in this application.
 *
 * A garbage category has:
 *
 * - a name;
 * - a garbage can name.
 */
class GarbageCategory
/*------------------------------------------------------------------------------------------------*\
 *                                                                                                *
 *                                          CONSTRUCTORS                                          *
 *                                                                                                *
\*------------------------------------------------------------------------------------------------*/
    (name: String, garbageCanName: String) {

    /*------------------------------------------------------------------------------------------------*\
 *                                                                                                *
 *                                           PROPERTIES                                           *
 *                                                                                                *
\*------------------------------------------------------------------------------------------------*/

    /**
     * The name of this garbage category.
     */
    var name: String? = name

    /**
     * The garbage can name in which this kind of garbage must be thrown in.
     */
    var garbageCanName: String? = garbageCanName

    init {

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

        if (obj !is GarbageCategory) {
            return false
        }

        val other = obj as GarbageCategory?

        return TextUtils.equals(name, other!!.name)
    }

    override fun hashCode(): Int {
        var result = 1

        result = 31 * result + if (name == null) 0 else name!!.hashCode()

        return result
    }
}
