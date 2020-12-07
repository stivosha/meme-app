package ru.stivosha.developers_life

import org.junit.Test

import org.junit.Assert.*
import ru.stivosha.developers_life.entity.Post
import ru.stivosha.developers_life.helpers.structures.PostHistory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testPostHistoryStructures(){
        val postHistory = PostHistory()
        for (i in 1..5)
            postHistory.add(
                Post(
                    "tittle_$i",
                    "gif_url_$i"
                )
            )
        assertEquals(postHistory.size, 5)

        postHistory.prev()
        postHistory.prev()

        assertEquals(postHistory.currentIndex, 2)

        postHistory.next()
        postHistory.next()

        assertEquals(postHistory.currentIndex, 4)
        assertEquals(postHistory.currentNode!!.value.gifURL, "gif_url_5")
    }
}
