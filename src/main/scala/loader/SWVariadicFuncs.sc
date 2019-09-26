val g: (Seq[Int]) => Int = si => {
  if (si.isEmpty) 0
  else si.head + g(si.tail)
}
g(Seq(1,2,3))
//res0: Int = 6

def calcRecursIf(i :Int*) :Int = {
  if (i.size==0) 0
  else
    i.head + calcRecursIf(i.tail : _*)
}
calcRecursIf(List(1,2,3,4,5) :_ *)


val f: Seq[Int] => Int = {
  case s if s.isEmpty => 0
  case s if s.nonEmpty => s.head + f(s.tail)
}
f(Seq(1,2,3))



//f(Seq(1,2,3))


def vfunc1(i :Int*) :Int = {
  if (i.size==0) 0
  else
  i.head + vfunc1(i.tail : _*)
}

//we have to use : _* to convert it to an argument sequence
vfunc1(1 to 5: _*)
vfunc1((1 to 5).toList :_ *)

/*
val f: Seq[Int] => Int = {
  case s if s.isEmpty => 0
  case s if s.nonEmpty => s.head + f(s.tail)
}
*/
def vfunc2(xs :Int*) :Int = {
  xs match {
    case xs if xs.isEmpty => 0
    case xs if xs.nonEmpty => xs.head + vfunc2(xs.tail: _*)
  }
}

vfunc2(1 to 5: _*)


def vfunc3(xs :Int*) :Int = {
    case xs :Seq[Int] if xs.isEmpty => 0
    case xs :Seq[Int] if xs.nonEmpty => xs.head + vfunc3(xs.tail: _*)
}

def vfunc3(a :Int,s :String, xs :Int*) :Int = {
  case xs :Seq[Int] if xs.isEmpty => 0
  case xs :Seq[Int] if xs.nonEmpty => xs.head + vfunc3(1,"abc",xs.tail: _*)
}




/**
 * 1)
 * myStrings.foreach(println( _ )) -> expanded to
 * myStrings.foreach(println( x => x.toString ))
 * _ it's not simple x, println need String on input => x -> x => x.toString
 *
 * 2) If we write form:
 * myStrings.foreach(println(_.toString))
 * it expanded into:
 * myStrings.foreach(x => println(x.toString))
 *
 * The placeholder syntax for anonymous functions replaces the smallest
 * possible containing expression with a function!
 *
*/

