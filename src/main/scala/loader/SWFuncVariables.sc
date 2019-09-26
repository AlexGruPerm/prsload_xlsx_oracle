/**
 * https://alvinalexander.com/scala/how-to-use-functions-as-variables-values-in-scala-fp
 *
 * // implicit approach
 * val add = (x: Int, y: Int) => { x + y }
 * val add = (x: Int, y: Int) => x + y
 *
 * // explicit approach
 * val add: (Int, Int) => Int = (x,y) => { x + y }
 * val add: (Int, Int) => Int = (x,y) => x + y
 *
 */

/**
 *
 * https://alvinalexander.com/scala/how-to-use-functions-as-variables-values-in-scala-fp
 *
 * val f: (Int) => Boolean = i => { i % 2 == 0 }
 * val f: Int => Boolean = i => { i % 2 == 0 }
 * val f: Int => Boolean = i => i % 2 == 0
 * val f: Int => Boolean = _ % 2 == 0
 *
 */