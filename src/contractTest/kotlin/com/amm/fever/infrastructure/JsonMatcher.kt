package com.amm.fever.infrastructure

import assertk.Assert
import net.javacrumbs.jsonunit.fluent.JsonFluentAssert
import org.hamcrest.BaseMatcher
import org.hamcrest.Description

class JsonMatcher(private val path: String) : BaseMatcher<String>() {
  override fun describeTo(description: Description?) {
    // not needed
  }
  override fun matches(actual: Any?): Boolean {
    JsonFluentAssert.assertThatJson(actual).isEqualTo(this.javaClass.getResource(path)?.readText())
    return true
  }
}

fun matchesJson(path: String) = JsonMatcher(path)
fun Assert<String>.matchesJson(path: String) = given {
  JsonMatcher(path).matches(it)
}
