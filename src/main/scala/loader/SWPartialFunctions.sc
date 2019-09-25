case class Car(name :String, nyear: Int)
case class Person(name :String, car :Car){
  override def toString: String = s"${name} ${car.name} [${car.nyear}]"
}
val listPersons :List[Person] = List(  Person("John",Car("mers",2013)),  Person("Mark",Car("honda",2010)),
  Person("Bob",Car("mers",2013)),  Person("JDilan",Car("honda",2012)), Person("Karl",Car("mers",2011)),  Person("Devid",Car("bmw",2012)),
  Person("Mohamed",Car("mers",2011)),  Person("James",Car("bmw",2013)))

val selByOwner1 : PartialFunction[Person,Person]={
  case p: Person if p.name.startsWith("J") => p
}

val sel2013 : PartialFunction[Person,Person]={
  case p:Person if p.car.nyear == 2013 => p
}

val sel2012 : PartialFunction[Person,Person]={
  case p:Person if p.car.nyear == 2012 => p
}
/*
listPersons collect(selByOwner1 compose sel2013)
listPersons collect(selByOwner1 andThen sel2013)
listPersons collect(selByOwner1 compose sel2013 orElse sel2012)
*/

listPersons.map(p => selByOwner1.runWith(println)(p))

import PartialFunction._
/*
listPersons.flatMap(e => condOpt(e){case p if p.name.startsWith("J") => p})
*/
listPersons flatMap selByOwner1.lift
listPersons.flatMap(e => condOpt(e){case p if p.name.startsWith("J") => p})
listPersons.flatMap(e => condOpt(e){selByOwner1})

/*
List(Some(1),None,Some(2)).flatten
List(Some(1),None,Some(2)).flatMap(e => e)
*/