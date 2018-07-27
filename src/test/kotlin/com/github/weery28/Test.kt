package com.github.weery28

import com.github.weery28.kotlinanymock.KotlinAnyMock
import com.github.weery28.kotlinanymock.KotlinAnyMock.kotlinAny
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class Test{


	@Before
	fun before(){

		KotlinAnyMock.setupMockitoAny { Mockito.any(it) }
	}

	@Test
	fun test(){

		class A

		class B (a : A)

		class C (i : Int , b : B)

		class D (s : Set<A>, m : Map<B,C>)

		class E (a : A, d : D, s : String, f : Float)


		open class Service {

			open fun check(e : E) : Boolean{
				requireNotNull(e)
				return true
			}
		}

		val service = Mockito.mock(Service::class.java)

		Mockito.`when`(service.check(kotlinAny())).thenReturn(false)

		service.check(E(A(), D(setOf(), mapOf()), "", 1f))

		Mockito.verify(service, Mockito.times(1)).check(kotlinAny())

	}

	@Test
	fun checkNotNull(){
		Assert.assertNotNull(kotlinAny<Pair<Int, String>>(false))
	}

}
