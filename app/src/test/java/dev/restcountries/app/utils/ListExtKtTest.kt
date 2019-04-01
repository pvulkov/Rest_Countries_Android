package dev.restcountries.app.utils

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ListExtKtTest {

    private val testList = mutableListOf<Any>()

    @Before
    fun setUp() {
        //NOTE (pvalkov) ensure list is empty before each test
        testList.clear()
    }

    @Test
    fun listEmptyDataSet_success() {
        testList.setData(mutableListOf())
        Assert.assertTrue(testList.isEmpty())
    }

    @Test
    fun listSomeDataSet_success() {
        testList.setData(listOf("1", "4", Any()))
        Assert.assertTrue(testList.isNotEmpty())
    }
}