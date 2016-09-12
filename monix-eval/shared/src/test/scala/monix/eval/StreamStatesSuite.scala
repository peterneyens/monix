/*
 * Copyright (c) 2014-2016 by its authors. Some rights reserved.
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

package monix.eval

import scala.util.{Failure, Success}

object StreamStatesSuite extends BaseTestSuite {
  test("TaskStream.suspend(Task(list))") { implicit s =>
    val list = List(1,2,3)
    val deferred = Task.eval(TaskStream.fromSeq[Int](list))
    val result = TaskStream.suspend(deferred).toListL.runAsync
    s.tick()
    assertEquals(result.value, Some(Success(list)))
  }

  test("CoevalStream.suspend(Coeval(list))") { implicit s =>
    val list = List(1,2,3)
    val deferred = Coeval.eval(CoevalStream.fromSeq[Int](list))
    val result = CoevalStream.suspend(deferred).toListL.runTry
    assertEquals(result, Success(list))
  }

  test("TaskStream.suspend(TaskStream)") { implicit s =>
    val list = List(1,2,3)
    val deferred = TaskStream.fromSeq[Int](list)
    val result = TaskStream.suspend(deferred).toListL.runAsync
    s.tick()
    assertEquals(result.value, Some(Success(list)))
  }

  test("CoevalStream.suspend(CoevalStream)") { implicit s =>
    val list = List(1,2,3)
    val deferred = CoevalStream.fromSeq[Int](list)
    val result = CoevalStream.suspend(deferred).toListL.runTry
    assertEquals(result, Success(list))
  }

  test("TaskStream.cons") { implicit s =>
    val list = List(1,2,3)
    val deferred = Task.eval(TaskStream.fromSeq[Int](list))
    val result = TaskStream.cons(0, deferred).toListL.runAsync
    s.tick()
    assertEquals(result.value, Some(Success(0 :: list)))
  }

  test("CoevalStream.cons") { implicit s =>
    val list = List(1,2,3)
    val deferred = Coeval.eval(CoevalStream.fromSeq[Int](list))
    val result = CoevalStream.cons(0, deferred).toListL.runTry
    assertEquals(result, Success(0 :: list))
  }

  test("TaskStream.consLazy") { implicit s =>
    val list = List(1,2,3)
    val deferred = Task.eval(TaskStream.fromSeq[Int](list))
    val result = TaskStream.consLazy(Task(0), deferred).toListL.runAsync
    s.tick()
    assertEquals(result.value, Some(Success(0 :: list)))
  }

  test("CoevalStream.consLazy") { implicit s =>
    val list = List(1,2,3)
    val deferred = Coeval.eval(CoevalStream.fromSeq[Int](list))
    val result = CoevalStream.consLazy(Coeval(0), deferred).toListL.runTry
    assertEquals(result, Success(0 :: list))
  }

  test("TaskStream.consSeq") { implicit s =>
    val list = List(1,2,3)
    val deferred = Task.eval(TaskStream.fromSeq[Int](list))
    val result = TaskStream.consSeq(List(0), deferred).toListL.runAsync
    s.tick()
    assertEquals(result.value, Some(Success(0 :: list)))
  }

  test("CoevalStream.consSeq") { implicit s =>
    val list = List(1,2,3)
    val deferred = Coeval.eval(CoevalStream.fromSeq[Int](list))
    val result = CoevalStream.consSeq(List(0), deferred).toListL.runTry
    assertEquals(result, Success(0 :: list))
  }

  test("TaskStream.halt(None)") { implicit s =>
    val result = TaskStream.halt(None).toListL.runAsync
    s.tick()
    assertEquals(result.value, Some(Success(Nil)))
  }

  test("CoevalStream.halt(None)") { implicit s =>
    val result = CoevalStream.halt(None).toListL.runTry
    assertEquals(result, Success(Nil))
  }

  test("TaskStream.halt(Some(ex))") { implicit s =>
    val ex = DummyException("dummy")
    val result = TaskStream.halt(Some(ex)).toListL.runAsync
    s.tick()
    assertEquals(result.value, Some(Failure(ex)))
  }

  test("CoevalStream.halt(Some(ex))") { implicit s =>
    val ex = DummyException("dummy")
    val result = CoevalStream.halt(Some(ex)).toListL.runTry
    assertEquals(result, Failure(ex))
  }
}

