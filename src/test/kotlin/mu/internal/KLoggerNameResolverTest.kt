package mu.internal


import org.junit.Assert.*
import org.junit.Test


class KLoggerNameResolverTest {

    @Test
    fun testNames() {
        assertEquals("mu.internal.BaseClass", KLoggerNameResolver.name(BaseClass::class.java))
        assertEquals("mu.internal.ChildClass", KLoggerNameResolver.name(ChildClass::class.java))
        assertEquals("mu.internal.BaseClass", KLoggerNameResolver.name(BaseClass.Companion::class.java))
        assertEquals("mu.internal.ChildClass", KLoggerNameResolver.name(ChildClass.Companion::class.java))
        assertEquals("mu.internal.Singleton", KLoggerNameResolver.name(Singleton::class.java))
        assertEquals("mu.internal.MyInterface", KLoggerNameResolver.name(MyInterface::class.java))
        assertEquals("java.lang.Object", KLoggerNameResolver.name(Any().javaClass))
        assertEquals("mu.internal.KLoggerNameResolverTest\$testNames$1", KLoggerNameResolver.name(object {}.javaClass))
        assertEquals("mu.internal.BaseClass\$InnerClass\$Obj", KLoggerNameResolver.name(BaseClass.InnerClass.Obj::class.java))
        assertEquals("mu.internal.BaseClass\$InnerClass\$Obj", KLoggerNameResolver.name(BaseClass.InnerClass.Obj.javaClass))
        assertEquals("mu.internal.BaseClass\$InnerClass", KLoggerNameResolver.name(BaseClass.InnerClass.CmpObj::class.java))
        assertEquals("mu.internal.BaseClass\$InnerClass", KLoggerNameResolver.name(BaseClass.InnerClass.CmpObj::class.java))
        assertEquals("mu.internal.Foo", KLoggerNameResolver.name(Foo.Bar::class.java))
        assertEquals("""
                        This is a known issue that we currently do not have a solution for
                        Foo.Bar2 is not a companion object, but still unwrapping occurs
                        """, "mu.internal.Foo", KLoggerNameResolver.name(Foo.Bar2::class.java))
    }
}

open class BaseClass{
    companion object
    class InnerClass {
        object Obj
        companion object CmpObj
    }
}
class ChildClass: BaseClass(){
    companion object
}
object Singleton
interface MyInterface


@Suppress("unused")
class Foo {
    object Bar
    object Bar2
    val z = Bar2

    companion object {
        @JvmField
        val Bar = this

        @JvmField
        val Bar2 = Foo().z
    }
}
