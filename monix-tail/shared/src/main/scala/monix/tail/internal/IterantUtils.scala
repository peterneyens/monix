/*
 * Copyright (c) 2014-2017 by its authors. Some rights reserved.
 * See the project homepage at: https://monix.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package monix.tail.internal

import monix.tail.Iterant
import monix.tail.Iterant._
import monix.types.Functor
import monix.types.syntax._

private[tail] object IterantUtils {
  def signalError[F[_], A, B](source: Iterant[F, A], ex: Throwable)(implicit F: Functor[F]): Iterant[F,B] = {
    val halt = Iterant.haltS[F,B](Some(ex))
    source match {
      case Next(_,_,stop) =>
        Suspend(stop.map(_ => halt), stop)
      case NextCursor(_,_,stop) =>
        Suspend(stop.map(_ => halt), stop)
      case NextBatch(_,_,stop) =>
        Suspend(stop.map(_ => halt), stop)
      case Suspend(_,stop) =>
        Suspend(stop.map(_ => halt), stop)
      case Last(_) | Halt(_) =>
        halt
    }
  }
}
