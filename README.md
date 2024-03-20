# Natural Kron #

A parser that converts natural (English) language to a cron expression in Kotlin.

## Installation ##

TODO:

- [ ] Add code to publish the library to maven central
- [ ] Tag the first release
- [ ] Add instructions to include the library in your project

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