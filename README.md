# kotlinAnyMock
Mockito.any() for using in Kotlin 
[![](https://jitpack.io/v/weery28/kotlinAnyMock.svg)](https://jitpack.io/#weery28/kotlinAnyMock)

1. Provide mathcer (Mockito.any(clas)) with KotlinAnyMock.setupMockitoAny.
2. If you wanted to setup own default values, you can use KotlinAnyMock.assign for this.
3. Use KotlinAnyMock.kotlinAny() for mocks.
4. If you want mock any object not in mockito context, then use KotlinAnyMock.kotlinAny(false) for save Mockito state.
