# Natural Kron

[![codecov](https://codecov.io/gh/yamilmedina/natural-kron/graph/badge.svg?token=ZOCYYKQ0VP)](https://codecov.io/gh/yamilmedina/natural-kron)
[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.yamilmedina/natural-kron?style=flat-square&color=green)](https://central.sonatype.com/artifact/io.github.yamilmedina/natural-kron/overview)

A parser that converts natural (English) language to a cron expression written in Kotlin.

## Installation ##

You can add the library to your project using gradle:

```kotlin
implementation("io.github.yamilmedina:natural-kron:1.0.0")
```

## Usage ##

### Using the Unix cron style

This is the default if you don't specify the style of the output:

```kotlin
import io.github.yamilmedina.kron.NaturalKronParser

val expression = "every day at 9am"
val parsed = NaturalKronParser().fromString(expression)

val expectedKronExpressionEveryDayAt9am = "0 9 * * *"
assertEquals(expectedKronExpressionEveryDayAt9am, parsed)  // --> TRUE
```

### Using the Quartz style

Why Quartz like style? If you work with Quartz before, you know that the cron expressions are different from the Unix
style.
This means they start counting from seconds, adding an initial field to the left. Also, you can not use
the `day of the week`
and `day of the month` fields at the same time[^1].

[^1]: [Quartz Notes Section](https://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html#notes)

Therefore, in case you want to use Quartz compatible expressions, you can specify the style in the parser, like this:

```kotlin
import io.github.yamilmedina.kron.NaturalKronParser
import io.github.yamilmedina.kron.KronStyle

val expression = "every day at 9am"
val parsed = NaturalKronParser().fromString(expression, KronStyle.QUARTZ)

val expectedKronExpressionEveryDayAt9am = "0 0 9 * * ?"
assertEquals(expectedKronExpressionEveryDayAt9am, parsed)  // --> TRUE
```

## Known limitations ##

This project is ported "as-is" from the original project, and it has some limitations:

If you provide an invalid expression and a valid time or day part, the parser will return a valid cron expression with
default to every day. For example, `evaery venus at 5am` (note the typo) will return `0 5 * * *` because the time part
is valid.

The plan is to fix this in the future, and enhance the code.

## Credits ##

This is a port from the project https://github.com/bpolaszek/natural-cron-expression/ written in PHP, to the kotlin
language.

## License ##

The MIT license. See LICENSE.