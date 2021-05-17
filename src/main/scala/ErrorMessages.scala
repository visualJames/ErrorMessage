abstract sealed class SubsetCase()
final case class This_isSubset() extends SubsetCase
final case class Other_isSubset() extends SubsetCase
final case class None_isSubset() extends SubsetCase

abstract sealed class AnsiColor_enum()
final case class BLACK() extends AnsiColor_enum{
  override def toString: String = "\u001B[30m"
}
final case class RED() extends AnsiColor_enum{
  override def toString: String = "\u001B[31m"
}
final case class GREEN() extends AnsiColor_enum{
  override def toString: String = "\u001B[32m"
}
final case class YELLOW() extends AnsiColor_enum{
  override def toString: String = "\u001B[33m"
}
final case class BLUE() extends AnsiColor_enum{
  override def toString: String = "\u001B[34m"
}
final case class MAGENTA() extends AnsiColor_enum{
  override def toString: String = "\u001B[35m"
}
final case class CYAN() extends AnsiColor_enum{
  override def toString: String = "\u001B[36m"
}
final case class WHITE() extends AnsiColor_enum{
  override def toString: String = "\u001B[37m"
}
final case class BLACK_B() extends AnsiColor_enum{
  override def toString: String = "\u001B[40m"
}
final case class RED_B() extends AnsiColor_enum{
  override def toString: String = "\u001B[41m"
}
final case class GREEN_B() extends AnsiColor_enum{
  override def toString: String = "\u001B[42m"
}
final case class YELLOW_B() extends AnsiColor_enum{
  override def toString: String = "\u001B[43m"
}
final case class BLUE_B() extends AnsiColor_enum{
  override def toString: String = "\u001B[44m"
}
final case class MAGENTA_B() extends AnsiColor_enum{
  override def toString: String = "\u001B[45m"
}
final case class CYAN_B() extends AnsiColor_enum{
  override def toString: String = "\u001B[46m"
}
final case class WHITE_B() extends AnsiColor_enum{
  override def toString: String = "\u001B[47m"
}
final case class RESET() extends AnsiColor_enum{
  override def toString: String = "\u001B[0m"
}
final case class BOLD() extends AnsiColor_enum{
  override def toString: String = "\u001B[1m"
}
final case class UNDERLINED() extends AnsiColor_enum{
  override def toString: String = "\u001B[4m"
}
final case class BLINK() extends AnsiColor_enum{
  override def toString: String = "\u001B[5m"
}
final case class REVERSED() extends AnsiColor_enum{
  override def toString: String = "\u001B[7m"
}
final case class INVISIBLE() extends AnsiColor_enum{
  override def toString: String = "\u001B[8m"
}

object ErrorMessage {
  case class Range(begin: Location, end: Location){
    def isAfter(other:Range):Boolean={
      if(other.end.column<this.begin.column) return true
      if(other.end.column>this.begin.column) return false

      if(other.end.row<=this.begin.row) return true
      if(other.end.row>this.begin.row) return false

      throw new IllegalStateException("The Function isAfter in Span should never reach this point")
    }

    def isSubsetOf(other:Range):Boolean={
      if(this.begin.column>=other.begin.column){
        if(this.end.column<=other.end.column){
          true
        }else{
          if(this.end.row<=other.end.row){
            true
          }else{
            false
          }
        }
      }else{
        false
      }
    }

    def whoIsSubset(other:Range):SubsetCase={
      if(this.isSubsetOf(other)){
        This_isSubset()
      }else if(this.isSubsetOf(other)){
        Other_isSubset()
      }else{
        None_isSubset()
      }
    }
  }
  case class Location (column: Int, row: Int){
    //row and line positive numbers
    require(row >= 0, "row is negative")
    require(column >= 0, "column is negative")
    val c = column+1
    val r = row+1

    override def toString: String = s"($c,$r)"
    def ==(end:Location) = this.column == end.column && this.row == end.row
  }
  def give_error(): String ={
    ""
  }

  private def give_code(): String ={
    ""
  }

  //alternative we can underline with 'Console.UNDERLINED', which looks great too, but with this method
  //we have more freedom how it looks like and we can use it later for more variants of underlining
  abstract sealed class Underline()
  final case class Underline_With_Char(char:Char, colour: AnsiColor_enum) extends Underline
  /*
  give the complete column back
  importantRange is important to know what to higlight
  colour defines with which colour it should be highlighted
   */
  def give_line_of_code(what_exp:String,
                        codeLines:Array[String], column:Int, underl: Option[Underline],
                        important_row_Begin:Int, important_row_End:Int,
                        identationBefore: Int, indentationAfter: Int): String={
    val row_begin = 0
    val row_end = codeLines(column).length
    val viewed = Range(Location(column, row_begin),Location(column, row_end))
    val important = Range(Location(column, important_row_Begin),Location(column, important_row_End))
    val which_case= viewed.whoIsSubset(important)
    val real_indentationBefore = identationBefore-column.toString.size
    if(real_indentationBefore<=0) throw new IllegalArgumentException("ident was chosen too small")
    var res = Console.BLUE + column + give_char_n_times(real_indentationBefore, ' ') +
      "|" + give_char_n_times(indentationAfter, ' ') + Console.RESET
    val res_of_code_underlining = underl match {
      case Some(Underline_With_Char(char, colour)) => {
        val (underlined_code, pos_start_underline,length_to_underline) = which_case match {
          case This_isSubset() =>
            throw new IllegalStateException("this should not happen, because the whole line is always a overset to an part of the line")
          case Other_isSubset() =>
            val code = codeLines(column)
            (code.substring(0,important_row_Begin)+ colour+code.substring(important_row_Begin,important_row_End)+
              Console.RESET+code.substring(important_row_End), res.length+important_row_Begin,
              code.substring(important_row_Begin,important_row_End).length)
          case None_isSubset() =>
            (colour + codeLines(column) + Console.RESET, res.length, codeLines(column).length)
        }
        underlined_code + "\n" + give_char_n_times(pos_start_underline, ' ') +
          colour + give_char_n_times(length_to_underline, char)+" " + what_exp + Console.RESET
      }
      case None => codeLines(column)
    }
    res + "\n"
  }


  def give_char_n_times(n:Int, c:Char): String={
    val arr = Array.fill(n)('^')
    String.valueOf(arr)
  }
}
