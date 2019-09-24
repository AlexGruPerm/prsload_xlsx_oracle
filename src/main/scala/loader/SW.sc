//trait ext
abstract class L1
class L2 extends L1
class L3 extends L2
class L4 extends L3
class L5 extends L4
class L6 extends L5
class L7 extends L6
class L8 extends L7
class L9 extends L8
class L10 extends L9


object IMplCombine {
  implicit class chiledL5[T >: L5](v: T) {
    def getSN: Option[T] = None
  }
  implicit class parentsL5[T <: L5](v: T) {
    def getSN: Option[T] = Some(v)
  }
}

val manList :List[Option[L1]] = List(new L2, new L3, new L4, new L5, new L6, new L7, new L8, new L9, new L10)

/*
//for each types T (L6,L7,L8...) that subtype of supertype L5
implicit def upBnd[T <: L5](v :T) :Option[L1] = Some(v)

//for each types T that supertype (L4,L3,....) of type L5
implicit def lwBnd[T >: L5](v :T) :Option[L1] = None

val manList :List[Option[L1]] = List(new L2, new L3, new L4, new L5, new L6, new L7, new L8, new L9, new L10)

val results :List[L1] = manList.flatten
results.foreach(e => println(e.getClass.getName))
*/



//val lx = List(new L2, new L3, new L4, new L5, new L6, new L7, new L8, new L9, new L10)
/*
val manList :List[Option[L1]] = List(new L2, new L3, new L4, new L5, new L6, new L7, new L8, new L9, new L10)
val results :List[L1] = manList.flatten

results.foreach(e => println(e.getClass.getName))
*/

/*
val lstRes :List[Option[L1]] = for {
  l <- lx
  rr :Option[L1] = l
} yield rr
*/


/*
val res :List[Option[L1]] = for {
  l <- lx
} yield l

val res2 = for {
  l <- lx
} yield l

res2.foreach{
  r => println(r.getClass.getName)
}

*/

/*
trait ext

abstract class A
class B extends A
class C extends A with ext
class D extends A
class E extends A with ext
class F extends A

val xs = List(new B,new C, new D, new E, new F, new B, new C)

(xs diff xs.collect {
  case x@(_: B | _: C) => x
}).foreach(elm => println(elm.getClass.getName))

implicit def withExt[T <: ext](v :T) :Option[T] = Some(v)
implicit def withoutExt[T >: ext](v :T) :Option[T] = None
*/
