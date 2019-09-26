case class TField(name: String)
type ValidationError = String
def invalidInput(s: String, v: String): Seq[String] = Seq(v)

val isPositive : PartialFunction[Seq[Any], String] = {
  case value if value.size < 2 => "Value need to have at least 2 values"
  case _ => "Ok"
}

def assert(name: TField, validator: PartialFunction[Seq[Any], String], input: Any*): Seq[ValidationError] = {
  if (validator.isDefinedAt(input)) {
    invalidInput(name.name, validator(input))
  } else {
    Seq.empty
  }
}

assert(TField("s"), isPositive, 5L)
assert(TField("s"), isPositive, "s1", 1, -2L, "s3")

