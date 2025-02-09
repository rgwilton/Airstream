package com.raquo.airstream.extensions

import com.raquo.airstream.core.{BaseObservable, Observable}

/** See also: [[OptionStream]] */
class OptionObservable[A, Self[+_] <: Observable[_]](val observable: BaseObservable[Self, Option[A]]) extends AnyVal {

  /** Maps the value in Some(x) */
  def mapSome[B](project: A => B): Self[Option[B]] = {
    observable.map(_.map(project))
  }

  /** Filters the value in Some(x) */
  def mapFilterSome(passes: A => Boolean): Self[Option[A]] = {
    observable.map(_.filter(passes))
  }

  /** Maps the value in Some(x), and the None value, to a common type. */
  def foldOption[B](ifEmpty: => B)(some: A => B): Self[B] = {
    observable.map(_.fold(ifEmpty)(some))
  }

  /** Maps Option[A] to Either[L, A] - you need to provide the L. */
  def mapToRight[L](left: => L): Self[Either[L, A]] = {
    observable.map(_.toRight(left))
  }

  /** Maps Option[A] to Either[A, R] - you need to provide the R. */
  def mapToLeft[R](right: => R): Self[Either[A, R]] = {
    observable.map(_.toLeft(right))
  }

}
