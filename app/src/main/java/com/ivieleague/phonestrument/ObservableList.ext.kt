package com.ivieleague.phonestrument

import com.lightningkite.kotlin.observable.list.ObservableList
import com.lightningkite.kotlin.observable.property.MutableObservableProperty
import java.util.HashMap

fun <T> ObservableList<T?>.indexObservable(index:Int): MutableObservableProperty<T?>
        = object : MutableObservableProperty<T?> {
    val actualObservable = this@indexObservable.onUpdate
    val actionToWrapper = HashMap<(T?) -> Unit, Wrapper>()

    inner class Wrapper(val func: (T?) -> Unit) : (ObservableList<T?>) -> Unit {
        override fun invoke(p1: ObservableList<T?>) {
            func(p1.getOrNull(index))
        }
    }

    override val size: Int get() = actualObservable.size
    override fun contains(element: (T?) -> Unit): Boolean =
            actualObservable.contains(actionToWrapper[element] ?: throw IllegalArgumentException("Function not a wrapper in this KObservableMapped."))

    override fun containsAll(elements: Collection<(T?) -> Unit>): Boolean = actualObservable.containsAll(
            elements.map { actionToWrapper[it] ?: throw IllegalArgumentException("Function not a wrapper in this KObservableMapped.") }
    )

    override fun isEmpty(): Boolean = actualObservable.isEmpty()
    override fun clear() {
        actualObservable.clear()
    }

    override fun iterator(): MutableIterator<(T?) -> Unit> = actionToWrapper.keys.iterator()
    override fun remove(element: (T?) -> Unit): Boolean {
        val wrapper = actionToWrapper[element] ?: throw IllegalArgumentException("Function not a wrapper in this KObservableMapped.")
        actionToWrapper.remove(wrapper.func)
        return actualObservable.remove(wrapper)
    }

    override fun removeAll(elements: Collection<(T?) -> Unit>): Boolean = actualObservable.removeAll(
            elements.map {
                val wrapper = actionToWrapper[it] ?: throw IllegalArgumentException("Function not a wrapper in this KObservableMapped.")
                actionToWrapper.remove(wrapper.func)
                wrapper
            }
    )

    override fun retainAll(elements: Collection<(T?) -> Unit>): Boolean = throw UnsupportedOperationException()

    override fun add(element: (T?) -> Unit): Boolean {
        val wrapper = Wrapper(element)
        actionToWrapper[element] = wrapper
        return actualObservable.add(wrapper)
    }

    override fun addAll(elements: Collection<(T?) -> Unit>): Boolean {

        return actualObservable.addAll(elements.map {
            val wrapper = Wrapper(it)
            actionToWrapper[it] = wrapper
            wrapper
        })
    }

    override fun update() {
        actualObservable.update()
    }


    override var value: T?
        get() = this@indexObservable.getOrNull(index)
        set(value) {
            while(index >= this@indexObservable.size){
                this@indexObservable.add(null)
            }
            this@indexObservable[index] = value
        }
}