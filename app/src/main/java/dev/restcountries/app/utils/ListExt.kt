package dev.restcountries.app.utils


fun <T> MutableList<T>.setData(data: List<T>) {
    this.clear()
    this.addAll(data)
}