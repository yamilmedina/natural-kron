# Natural Kron

[![codecov](https://codecov.io/gh/yamilmedina/natural-kron/graph/badge.svg?token=ZOCYYKQ0VP)](https://codecov.io/gh/yamilmedina/natural-kron)
![Maven Central Version](https://img.shields.io/maven-central/v/io.github.yamilmedina/natural-kron?style=flat-square&color=green)

A parser that converts natural (English) language to a cron expression in Kotlin.

## Installation ##

You can add the library to your project using gradle:

```kotlin
implementation("io.github.yamilmedina:natural-kron:0.0.1")
```

## Usage ##

```kotlin
import io.github.yamilmedina.naturalkron.NaturalKron

val expression = "every day at 9am"
val parsed = NaturalKronExpressionParser().parse(expression)

val expectedKronExpressionEveryDayAt9am = "0 0 9 * * *"
assertEquals("*", parsed.dayOfWeek) // --> every day cron expression
assertEquals(expectedKronExpressionEveryDayAt9am, parsed.toString())  // --> TRUE
```

## Known limitations ##

This project is ported "as-is" from the original project, and it has some limitations:

If you provide an invalid expression and a valid time or day part, the parser will return a valid cron expression with
default to every day. For example, `evaery venus at 5am` (note the typo) will return `0 0 5 * * *` because the time part
is valid.

The plan is to fix this in the future, and enhance the code.

## Credits ##

This is a port from the project https://github.com/bpolaszek/natural-cron-expression/ written in PHP, to the kotlin
language.

## License ##

The MIT license. See LICENSE.