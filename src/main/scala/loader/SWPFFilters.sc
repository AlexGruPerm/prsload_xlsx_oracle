case class Car(name :String, year :Int){
  override def toString :String = name+" "+year
}
case class Country(name :String)
case class Person(name: String, car :Car, country :Country){
  override def toString :String =
    s"$name - $country -  $car"
}

type pfPP = PartialFunction[Person,Person]

val pfCarFilter :pfPP ={
  case p if p.car.year==2013 => p
}

val pfCountryFilter :pfPP ={
  case p if p.country.name=="russia" => p
}

val pfPersonNameFilter :pfPP = {
  case p if p.name.startsWith("J") => p
}

val listpers :Seq[Person] = Seq(
  Person("John",Car("mers",2013),Country("russia")),
  Person("Mark",Car("mers",2013),Country("usa")),
  Person("Jenya",Car("mers",2013),Country("russia")),
  Person("James",Car("mers",2013),Country("usa")),
  Person("Alex",Car("mers",2012),Country("russia")),
  Person("Smith",Car("mers",2013),Country("usa")),
  Person("Jasper",Car("mers",2013),Country("russia")),
  Person("Jastin",Car("mers",2013),Country("usa"))
)

//manual chaining
val commonFilter = pfPersonNameFilter andThen pfCountryFilter andThen pfCarFilter
listpers collect commonFilter map println

//fold filers seq in chain
val seqPfF :Seq[pfPP] = Seq(pfPersonNameFilter, pfCountryFilter, pfCarFilter)
val combFilters = seqPfF.tail.foldLeft(seqPfF.head)(_ andThen _)

listpers collect combFilters map println

//================= OPTIMIZATION OF FILTERING DATA =======================
//create big seq of Persons with random data.

val r = scala.util.Random
val rs = scala.util.Random.alphanumeric

def randomStringFromCharList(length: Int, chars: Seq[Char]): String = {
  val sb = new StringBuilder
  for (i <- 1 to length) {
    val randomNum = util.Random.nextInt(chars.length)
    sb.append(chars(randomNum))
  }
  sb.toString
}

def randomAlphaNumericString(length: Int): String = {
  val chars = ('a' to 'z')
  randomStringFromCharList(length, chars)
}

val seqCarNames :Seq[String] = Seq("mers","bmw","honda","toyota","lexus")
val seqCountry :Seq[Country] = Seq(Country("russia"),Country("usa"))

val rndListPerson :Seq[Person] = (1 to 1000000).toList.iterator.map(v =>
  Person(
    randomAlphaNumericString(5).capitalize,
    Car(seqCarNames(r.nextInt(4)), (2011 + r.nextInt(3))),
    seqCountry(r.nextInt(2)))
).toSeq

rndListPerson.size

rndListPerson.groupBy(_.name(0)).foreach{
  case (k,v) => println(k+" count = "+v.size)
}

rndListPerson.groupBy(_.car.year).foreach{
  case (k,v) => println(k+" count = "+v.size)
}

rndListPerson.groupBy(_.country).foreach{
  case (k,v) => println(k+" count = "+v.size)
}

/*
1) not ordered at all
 slow = 171
 fast = 122
2) order by first letterin name
 slow = 601
 fast = 454
*/

val SortedSeqPers = rndListPerson.sortBy(p => p.country.name).toList

val t1Slow :Long = System.currentTimeMillis
val slowFilterComb = pfPersonNameFilter andThen pfCountryFilter
val resSlow :Seq[Person] = SortedSeqPers collect slowFilterComb
println(s"slow filtering size=${resSlow.size} duration = ${System.currentTimeMillis()-t1Slow}")

val t1Fast :Long = System.currentTimeMillis
val fastFilterComb = pfCountryFilter andThen pfPersonNameFilter
val resFast :Seq[Person] = SortedSeqPers collect fastFilterComb
println(s"fast filtering size=${resFast.size} duration = ${System.currentTimeMillis()-t1Fast}")




