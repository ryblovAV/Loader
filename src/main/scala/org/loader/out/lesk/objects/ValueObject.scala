package org.loader.out.lesk.objects

import java.util.Date

abstract class ObjectWithDate {

  val dt:Date

  def < (obj:ObjectWithDate):Boolean = {
    dt.before(obj.dt)
  }

  def equalDate(obj:ObjectWithDate) = dt.compareTo(obj.dt) == 0

  def getType:Int
}

case class TypeA(dt:Date) extends ObjectWithDate {
  override def getType: Int = 1
}

case class TypeB(dt:Date) extends ObjectWithDate {
  override def getType: Int = 2
}

object ObjectWithDateZip {

  def zip(listA:List[ObjectWithDate], listB:List[ObjectWithDate]):List[ObjectWithDate] = {
    (listA:::listB).sortWith((a:ObjectWithDate,b:ObjectWithDate) => a < b)
  }

  def addTypeA(mergeList:List[ObjectWithDate],
               a:TypeA,
               bOption:Option[TypeB],
               resList:List[(TypeA,TypeB)] ) = {
    bOption match {
      case None => createPair(mergeList,Option(a),bOption,resList)
      case Some(b) => createPair(mergeList,Option(a),bOption,resList :+ (a,b))
    }
  }

  def addTypeB(mergeList:List[ObjectWithDate],
               aOption:Option[TypeA],
               b:TypeB,
               resList:List[(TypeA,TypeB)] ) = {
    aOption match {
      case None => createPair(mergeList,aOption,Option(b),resList)
      case Some(a) => createPair(mergeList,aOption,Option(b),resList :+ (a,b))
    }
  }

  def createPair(mergeList:List[ObjectWithDate],
                 optA:Option[TypeA],
                 optB:Option[TypeB],
                 resList:List[(TypeA,TypeB)]):List[(TypeA,TypeB)] = {
    mergeList match {
      case Nil => resList
      case head :: tail =>
        head match {
          case firstA:TypeA => addTypeA(tail,firstA,optB,resList)
          case firstB:TypeB => addTypeB(tail,optA,firstB,resList)
        }
    }
  }

}

object ValueObject {

  def t(a: AnyRef) = {
    a.equals()
  }

  def deleteRepeat[T](values: List[T], equalFunc: (T, T) => Boolean): List[T] = {
    values match {
      case first :: second :: tail => if (equalFunc(first, second))
                                        deleteRepeat(first :: tail, equalFunc)
                                      else
                                        first :: deleteRepeat(second :: tail, equalFunc)
      case first :: Nil => values
      case Nil => values
    }
  }

}
