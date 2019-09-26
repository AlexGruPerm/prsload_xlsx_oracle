
def recursiveSum(xs :Seq[Int]) :Int = {
    case _: Seq[Int] if _.isEmpty => 0
    case _: Seq[Int] if xs.nonEmpty => xs.head + recursiveSum(xs.tail)
}

recursiveSum(Seq(1,2,3,4,5))