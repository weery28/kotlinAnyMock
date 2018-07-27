package com.github.weery28.kotlinanymock

import java.lang.reflect.Constructor


object KotlinAnyMock {

	private val defaults = mutableMapOf<Class<*>, Any>()

	private var mockitoAny: ((Class<*>) -> Unit)? = null


	init {
		assign(Int::class.java, 0)
		assign(Float::class.java, 0f)
		assign(Long::class.java, 0)
		assign(Double::class.java, 0.toDouble())
		assign(Boolean::class.java, true)
		assign(Set::class.java, setOf<Any>())
		assign(Map::class.java, mapOf<Any, Any>())
		assign(List::class.java, listOf<Any>())
		assign(Unit::class.java, Unit)
	}

	fun setupMockitoAny(mockitoAny: ((Class<*>) -> Unit)) {

		this.mockitoAny = mockitoAny
	}

	fun <T : Any> assign(clazz: Class<T>, value: T) {

		defaults[clazz] = value
	}

	inline fun <reified T> kotlinAny(changeMockitoState: Boolean = true): T {
		return kotlinAny(T::class.java, changeMockitoState)
	}

	fun <T> kotlinAny(clas: Class<T>, changeMockitoState: Boolean = true): T {

		if (changeMockitoState) {
			requireNotNull(mockitoAny)
			mockitoAny!!(clas)
		}

		defaults.toList().firstOrNull { it.first == clas }?.let { return it.second as T }


		return try {
			clas.newInstance()
		} catch (e: InstantiationException) {
			var instance: T? = null
			var exception: Exception = e
			clas.constructors.forEach {
				try {
					instance = instantiate(it as Constructor<T>)
				} catch (instantiateException: Exception) {
					exception = instantiateException
				}
			}
			instance ?: throw exception
		}
	}

	private fun <T> instantiate(constructor: Constructor<T>): T {

		val params = constructor.parameterTypes.map { kotlinAny(it, false) }.toTypedArray()

		return constructor.newInstance(*params)
	}

}