package ErrorMessage

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.equal
import org.scalatest.matchers.should.Matchers._

class ErrorMessageTest extends  AnyFlatSpec {
  val what_exp = "mismatched closing delimiter"
  val codeLines = Array("fn main() {", "println(\"Hello, world!\");", "(", "}")

  "give_line_of_code" should "work for mismatched closing delimiter" in {
    val column = 3
    val underline = Underline_With_Char('^', BLUE())
    val important_row_begin = 0
    val important_row_end = 0
    val indentionBefore = 5
    val indentionAfter = 3
    val res = give_line_of_code(what_exp, codeLines, column, underline, important_row_begin, important_row_end, indentionBefore, indentionAfter)
    println(res)
  }
}

object ErrorMessageTest {

}