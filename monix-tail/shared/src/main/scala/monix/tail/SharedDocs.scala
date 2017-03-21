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

package monix.tail

/** Trait defined in order to get sharing of ScalaDoc definitions :-(
  *
  * @define NextDesc The [[monix.tail.Iterant.Next Next]] state
  *         of the [[Iterant]] represents a `head` / `rest`
  *         cons pair, where the `head` is a strict value.
  *
  *         Note the `head` being a strict value means that it is
  *         already known, whereas the `rest` is meant to be lazy and
  *         can have asynchronous behavior as well, depending on the `F`
  *         type used.
  *
  *         See [[monix.tail.Iterant.NextCursor NextCursor]]
  *         for a state where the head is a strict immutable list.
  *
  * @define nextSDesc Builds a stream state equivalent with
  *         [[Iterant.Next]].
  *
  *         $NextDesc
  *
  * @define NextCursorDesc The [[monix.tail.Iterant.NextCursor NextCursor]] state
  *         of the [[Iterant]] represents an `batch` / `rest` cons pair,
  *         where `batch` is an [[scala.collection.Iterator Iterator]]
  *         type that can generate a whole batch of elements.
  *
  *         Useful for doing buffering, or by giving it an empty iterator,
  *         useful to postpone the evaluation of the next element.
  *
  * @define nextCursorSDesc Builds a stream state equivalent with
  *         [[Iterant.NextCursor]].
  *
  *         $NextCursorDesc
  *
  * @define NextBatchDesc The [[monix.tail.Iterant.NextBatch NextBatch]] state
  *         of the [[Iterant]] represents an `batch` / `rest` cons pair,
  *         where `batch` is an [[scala.collection.Iterable Iterable]]
  *         type that can generate a whole batch of elements.
  *
  * @define nextBatchSDesc Builds a stream state equivalent with
  *         [[Iterant.NextBatch]].
  *
  *         $NextBatchDesc
  *
  * @define SuspendDesc The [[monix.tail.Iterant.Suspend Suspend]] state
  *         of the [[Iterant]] represents a suspended stream to be
  *         evaluated in the `F` context. It is useful to delay the
  *         evaluation of a stream by deferring to `F`.
  *
  * @define suspendSDesc Builds a stream state equivalent with
  *         [[Iterant.NextCursor]].
  *
  *         $SuspendDesc
  *
  * @define LastDesc The [[monix.tail.Iterant.Last Last]] state of the
  *         [[Iterant]] represents a completion state as an alternative to
  *         [[monix.tail.Iterant.Halt Halt(None)]], describing one
  *         last element.
  *
  *         It is introduced as an optimization, being equivalent to
  *         `Next(item, F.pure(Halt(None)), F.unit)`, to avoid extra processing
  *         in the monadic `F[_]` and to short-circuit operations such as
  *         concatenation and `flatMap`.
  *
  * @define lastSDesc Builds a stream state equivalent with [[Iterant.Last]].
  *
  *         $LastDesc
  *
  * @define HaltDesc The [[monix.tail.Iterant.Halt Halt]] state
  *         of the [[Iterant]] represents the completion state
  *         of a stream, with an optional exception if an error
  *         happened.
  *
  *         `Halt` is received as a final state in the iteration process.
  *         This state cannot be followed by any other element and
  *         represents the end of the stream.
  *
  *         @see [[Iterant.Last]] for an alternative that signals one
  *              last item, as an optimisation
  *
  * @define haltSDesc Builds a stream state equivalent with [[Iterant.Halt]].
  *
  *         $HaltDesc
  *
  * @define builderNow Lifts a strict value into the stream context,
  *         returning a stream of one element.
  *
  * @define builderEval Lifts a non-strict value into the stream context,
  *         returning a stream of one element that is lazily evaluated.
  *
  * @define builderSuspendByName Promote a non-strict value representing a
  *         stream to a stream of the same type, effectively delaying
  *         its initialisation.
  *
  * @define builderSuspendByF Defers the stream generation to the underlying
  *         evaluation context (e.g. `Task`, `Coeval`, etc), building
  *         a reference equivalent with [[Iterant.Suspend]].
  *
  *         $SuspendDesc
  *
  * @define builderEmpty Returns an empty stream.
  *
  * @define builderRaiseError Returns an empty stream that ends with an error.
  *
  * @define builderTailRecM Keeps calling `f` and concatenating the resulting
  *         iterants for each `scala.util.Left` event emitted by the source,
  *         concatenating the resulting iterants and generating `scala.util.Right[B]`
  *         batch.
  *
  *         Based on Phil Freeman's
  *         [[http://functorial.com/stack-safety-for-free/index.pdf Stack Safety for Free]].
  *
  * @define builderFromArray Converts any standard `Array` into a stream.
  *
  * @define builderFromList Converts any Scala `collection.immutable.LinearSeq`
  *         into a stream.
  *
  * @define builderFromIndexedSeq Converts any Scala `collection.IndexedSeq`
  *         into a stream (e.g. `Vector`).
  *
  * @define builderFromSeq Converts any `scala.collection.Seq` into a stream.
  *
  * @define builderFromIterator Converts a `scala.collection.Iterator`
  *         into a stream.
  **
  * @define builderFromIterable Converts a `scala.collection.Iterable`
  *         into a stream.
  *
  * @define builderRange Builds a stream that on evaluation will produce
  *         equally spaced values in some integer interval.
  *
  * @define headParamDesc is the current element to be signaled
  *
  * @define lastParamDesc is the last element being signaled, after which
  *         the consumer can stop the iteration
  *
  * @define cursorParamDesc is an [[scala.collection.Iterator Iterator]] type
  *         that can generate elements by traversing a collection, a standard
  *         array or any `Iterator`
  *
  * @define generatorParamDesc is a [[scala.collection.Iterable Iterable]]
  *         type that can generate elements by traversing a collection,
  *         a standard array or any `Iterable`
  *
  * @define restParamDesc is the next state in the sequence that
  *         will produce the rest of the stream when evaluated
  *
  * @define stopParamDesc is a computation to be executed in case
  *         streaming is stopped prematurely, giving it a chance
  *         to do resource cleanup (e.g. close file handles)
  *
  * @define exParamDesc is an error to signal at the end of the stream,
  *        or `None` in case the stream has completed normally
  *
  * @define suspendByNameParam is the by-name parameter that will generate
  *         the stream when evaluated
  *
  * @define rangeFromParam the start value of the stream
  * @define rangeUntilParam the end value of the stream (exclusive from the stream)
  * @define rangeStepParam the increment value of the tail (must be positive or negative)
  * @define rangeReturnDesc the tail producing values `from, from + step, ...` up to, but excluding `until`
  */
private[tail] trait SharedDocs extends Any
