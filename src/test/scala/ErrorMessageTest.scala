import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.equal
import org.scalatest.matchers.should.Matchers._

class ErrorMessageTest extends  AnyFlatSpec {
  "give_error" should "work for mismatched closing delimiter" in {
    val what_exp = "mismatched closing delimiter"
    val codeLines = Array("fn main() {", "println(\"Hello, world!\");", "       (", "} //here after is a comment")

    val column = 3
    val underline = ErrorMessage.Underline_With_Char('^', RED())
    val important_row_begin = 0
    val important_row_end = 1
    val indentionBefore = 2
    val indentionAfter = 1
    val name_of_error = "error"
    val fileName = "src/test/files/main.rs"
    val res = ErrorMessage.give_error(fileName,what_exp,name_of_error,0,codeLines.length,codeLines,underline,
      column,important_row_begin,important_row_end)
    println(res)
  }

  "give_error" should "work for expected type" in {
    val what_exp = "expected type (f32 -> (f64 -> bool)) but found type of form `(_dt31 -> (_dt31 -> _dt31))"
    val codeLines = Array("f::F32->F64->Bool", "f=\\x->\\y->+ x y", "g::F32->F32", "g::\\x->x")

    val column = 1
    val underline = ErrorMessage.Underline_With_Char('^', RED())
    val important_row_begin = 2
    val important_row_end = codeLines(1).length
    val indentionBefore = 2
    val indentionAfter = 1
    val name_of_error = "InferExeption"
    val fileName = "src/test/files/AddWithTwoDifferentScalarTypes6.rise"
    val res = ErrorMessage.give_error(fileName,what_exp,name_of_error,column,column+1,codeLines,underline,column,
      important_row_begin,important_row_end)
    println(res)
  }
}