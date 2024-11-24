# Natural Kron

[![codecov](https://codecov.io/gh/yamilmedina/natural-kron/graph/badge.svg?token=ZOCYYKQ0VP)](https://codecov.io/gh/yamilmedina/natural-kron)
[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.yamilmedina/natural-kron?style=flat-square&color=green)](https://central.sonatype.com/artifact/io.github.yamilmedina/natural-kron/overview)

A parser that converts natural (English) language to a cron quartz expressions, written in Kotlin.

## Installation ##

You can add the library to your project using gradle:

```kotlin
implementation("io.github.yamilmedina:natural-kron:1.0.0")
```

## Motif ##

This library is intended to be used in applications where the user can define cron expressions in a more natural way.
Some examples, where you can use this library are, but not limited to:

- Chatbots
- Scheduling applications for non-technical users
- Cron expression generators
- Scheduling tasks in a more human-readable way

#### Why Quartz like style and not Unix like?

If you worked before with scheduling tasks for the jvm, you probably heard/know
about [Quartz](https://github.com/quartz-scheduler). Quartz is a powerful library for this job. But there is a small
catch, Quartz uses a different cron expression style
than Unix.

This means they start counting from seconds, adding an initial field to the left. Also, you can not use
the `day of the week` and `day of the month` fields at the same time[^1].

[^1]: [Quartz Notes Section](https://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html#notes)

#### Usage

To use the library, you can specify the style in the parser, like this:

```kotlin
import io.github.yamilmedina.kron.NaturalKronParser

val input = "every saturday at 10:11"
val cronExpression = NaturalKronParser().parse(input)

assertEquals("0 11 10 ? * SAT", cronExpression)---> This is a valid Quartz cron expression
```

## Implementation details ##

This library uses an ANTLR4 grammar to parse the input. The grammar is defined in the `CronGrammar.g4` file.
The generated files are included in the project, but are not intended to be used directly, that's why they are in
the `internal` package (good old sun days xD), therefore, **don't use it**, there might be gone, or changed in the
future.

## Known limitations ##

This library is a side quest project born into the requirements of a bigger project.
This is a starting point for now, so these things are not supported yet (most likely I missed some):

- yearly expressions (e.g. `every day during 2005` -> `0 0 0 ? * * 2005`)
- step every x minutes/hours/days/weeks/months (e.g. `every 5 minutes` -> `0 0/5 * ? * *`)
- ranges of values (e.g. `every day between 10 and 20` -> `0 0 0 10-20 * ?`)
- last day of the month (e.g. `every last day of the month` -> `0 0 0 L * ?`)

## License ##

The MIT license. See LICENSE.