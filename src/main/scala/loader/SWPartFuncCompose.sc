/**
 * https://medium.com/@chaitanyawaikar1993/playing-with-partial-functions-in-scala-ba57546f4afb
 * https://alvinalexander.com/scala/how-to-define-use-partial-functions-in-scala-syntax-examples
 * https://medium.com/@thejasbabu/partial-partially-applied-functions-in-scala-a0d179e7df3
 *
 *
*/
val pfLess7 : PartialFunction[Int,Int]={
 case i :Int if i<=7 => i*10
}

val pfMore4 : PartialFunction[Int,Int]={
  case i :Int if i>=4 => i+2
}

//#0
/**
 *  def    map[B](f: A => B)  :List[B]
 * def collect[B](pf: PartialFunction[A, B])  :List[B]
 *
 * Compare this two methods of
 *
*/

//#1
/**
 * Composes another partial function `k` with this partial function so that this
 * partial function gets applied to results of `k`.
*/
//compose make appling from right to left
val fpCompose = pfLess7 compose pfMore4
(1 to 10).toList collect fpCompose foreach(println)
/** Steps:
 * 1. pfMore4 filter rows and make calc. +2
 *  1,2,3....,9,10 -> 4,5,6,7,8,9,10 -> 6,7,8,9,10,11,12
 * 2. pfLess7 filter values and we have only 6,7
 *  calc. * 10 -> 60,70
*/

//#2
/**
 * Composes this partial function with another partial function that
 * gets applied to results of this partial function.
*/
//andThen make applying from left to right
val fpAndThen = pfLess7 andThen pfMore4
(1 to 10).toList collect fpAndThen foreach(println)
/** Steps:
 * 1. pfLess7 after filtering there are: 1,2,3,4,5,6,7
 * 2. *10 -> 10,20,30,40,50,60,70
 * 3. pfMore4, filer, each rows in step 2 more 4.
 * 4. +2 -> 12,22,32,42,52,62,72
*/

/**
 * andThen and compose do equal work but compose from Right into Left and
 * andThen from Left into Right.
*/

/** Composes this partial function with a fallback partial function which
 *  gets applied where this partial function is not defined.
*/
//#3.1
(1 to 10).toList collect (pfMore4 orElse pfLess7) foreach(println)
//10,20,30,6,7,8,9,10,11,12
/** Steps:
 * pfMore4 is not defined in interval 1,2,3 it means that fallback p.f used for this values
 * 10,20,30 and next for values 4,5,6,7,8,9,10 used pfMore4  (+2) -> 6,7,8,9,10,11,12
*/

//#3.2
(1 to 10).toList collect (pfLess7 orElse pfMore4) foreach(println)
//10,20,30,40,50,60,70,10,11,12
/** Steps:
 * pfLess7 is defined on interval 1-7 and do *10
 * here we have 10,20,30,40,50,60,70, and next 8,9,10 more then 4 and pfMore4 using
 * res of pfMore4 is 10,11,12
 */

//#4. lift
/**
 * Turns this partial function into a plain function returning an `Option` result.
*/
(1 to 10).toList collect pfLess7
//res4: List[Int] = List(10, 20, 30, 40, 50, 60, 70)

//(1 to 10).toList collect pfLess7.lift
//Error. because collect wait partial function and not plain function, try map

(1 to 10).toList map pfLess7
//List(Some(10), Some(20), Some(30), Some(40), Some(50), Some(60), Some(70), None, None, None)

//to eliminate Nones we can use flatten or flatMap
import scala.language.postfixOps
(1 to 10).toList map pfLess7.lift flatten

(1 to 10).toList flatMap pfLess7.lift

//#5. condOpt
import PartialFunction._
/** Transforms a PartialFunction[T, U] `pf` into Function1[T, Option[U]] `f`
 *  whose result is `Some(x)` if the argument is in `pf`'s domain and `None`
 *  otherwise
 */
(1 to 10).toList.map(e => condOpt(e){pfLess7})
(1 to 10).toList.flatMap(e => condOpt(e){pfLess7})

//#6. runWith
/** Composes this partial function with an action function which
 *  gets applied to results of this partial function.
 *  The action function is invoked only for its side effects; its result is ignored.
*/
(1 to 10).toList.map(p => pfLess7.runWith(println)(p))

//opposite
//#7. ApplyOrElse
/** Applies this partial function to the given argument when it is contained in the function domain.
 *  Applies fallback function where this partial function is not defined.
 */
(1 to 10).toList.map(p => pfLess7.applyOrElse(p,println))

//two way of define p.f
//1.
val pf1 :PartialFunction[Int,Int] = {case i :Int if i<=2 => i}
//2.
val pf2 = new PartialFunction[Int,Int] {
  def apply(x: Int) = x
  def isDefinedAt(x: Int) = x >= 2
}

pf1.isDefinedAt(1)
pf1.isDefinedAt(10)

val pfValMore4 :PartialFunction[Int,Int] = {case i :Int if i>=4 => i}
val pfValLess6 :PartialFunction[Int,Int] = {case i :Int if i<=6 => i}
val pfValOrM4L6 = pfValMore4 orElse pfValLess6

//each values is in domain of pfValBtw46, because OR
pfValOrM4L6.isDefinedAt(3)//true
pfValOrM4L6.isDefinedAt(5)//true
pfValOrM4L6.isDefinedAt(7)//true

val pfValMore6 :PartialFunction[Int,Int] = {case i :Int if i>=6 => i}
val pfValLess4 :PartialFunction[Int,Int] = {case i :Int if i<=4 => i}
val pfValOrM6L4 = pfValMore6 orElse pfValLess4

pfValOrM6L4.isDefinedAt(3)//true
pfValOrM6L4.isDefinedAt(5)//false
pfValOrM6L4.isDefinedAt(7)//true

//common domain area of two P.F.
val pfValOrBtw46 = pfValMore4 andThen pfValLess6
pfValOrBtw46.isDefinedAt(3)//false
pfValOrBtw46.isDefinedAt(5)//true
pfValOrBtw46.isDefinedAt(7)//false

//direct call without collect
pfValMore4(5)
//pfValMore4(2) //error scala.MatchError: 2 (of class java.lang.Integer)
//or

pfValMore4.lift(2)//Option[Int] = None

List(2) collect pfValMore4 //List[Int] = List() //no errors no Option.

List(2).map(v => condOpt(v)(pfValMore4)) //List[Option[Int]] = List(None)

val finalPF = pfValMore4.orElse[Int,Int]{
  case i if i <=2 => i
}

finalPF.isDefinedAt(1)//true
finalPF.isDefinedAt(3)//false
finalPF.isDefinedAt(5)//true

//Partially Applied Functions
def paf(v1 :Int, v2 :Int)={
  v1+v2
}

val paf1 = paf(_,1)
//or
val paf2 : (Int => Int) = paf(_,1)
//or
val paf3 : (Int) => Int = paf(_,1)
/**
 * paf1: Int => Int = <function>
 * paf2: Int => Int = <function>
 * paf3: Int => Int = <function>
*/

paf1(2) //3
paf3(5) //6

/**
 * Both Currying and Partially applied functions reduce the arity of the function.
 * Currying is the process of decomposing a function that takes multiple arguments into
 * a sequence of functions, each with a single argument.
*/



