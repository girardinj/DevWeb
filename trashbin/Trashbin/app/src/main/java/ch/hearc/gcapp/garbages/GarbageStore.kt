package ch.hearc.gcapp.garbages

import java.util.ArrayList

import ch.hearc.gcapp.garbages.categories.GarbageCategory

/**
 * Since we didn't learn (yet) how to store data in an Android application, we will use this class
 * to store our data in memory.
 */
object GarbageStore {

    val GARBAGES: MutableList<Garbage> = ArrayList()

    val GARBAGE_CATEGORIES: MutableList<GarbageCategory> = ArrayList()

    init {
        // Let's create some data on the fly...
        val categoryPet = GarbageCategory("PET", "PET recycle bin")
        val categoryIron = GarbageCategory("Iron", "Iron container")
        val categoryAluminum = GarbageCategory("Aluminum", "Aluminum container")

        GARBAGE_CATEGORIES.add(categoryPet)
        GARBAGE_CATEGORIES.add(categoryIron)
        GARBAGE_CATEGORIES.add(categoryAluminum)

        GARBAGES.add(Garbage("Valser Classic", categoryPet))
        GARBAGES.add(Garbage("Lipton Lemon Ice Tea", categoryPet))
        GARBAGES.add(Garbage("Coca Cola", categoryPet))

        GARBAGES.add(Garbage("Canned food", categoryIron))
        GARBAGES.add(Garbage("Pot lid", categoryIron))
        GARBAGES.add(Garbage("Canned sardines", categoryIron))

        GARBAGES.add(Garbage("Yogurt lid", categoryAluminum))
        GARBAGES.add(Garbage("Knife blade", categoryAluminum))
        GARBAGES.add(Garbage("Aluminum foil", categoryAluminum))
    }

    /**
     * Utility function to find a garbage by its name.
     */
    fun findGarbageByName(name: String): Garbage? {
        var result: Garbage? = null

        for (garbage in GARBAGES) {
            if (garbage.name == name) {
                result = garbage
            }
        }

        return result
    }

    /**
     * Utility function to find a garbage category by its name.
     */
    fun findGarbageCategoryByName(name: String): GarbageCategory? {
        var result: GarbageCategory? = null

        for (garbageCategory in GARBAGE_CATEGORIES) {
            if (garbageCategory.name == name) {
                result = garbageCategory
            }
        }

        return result
    }
}
