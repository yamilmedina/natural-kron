grammar NaturalCron;

// Parser rules
naturalCron: date at clock;
at: AT;
date: DAYS;
clock: HOUR;

// Lexer Rules
fragment INT: [0-9]+;
HOUR: INT ':' INT;
AT: ('at'|'AT');
DAYS: ('hourly'|'daily'|'weekly'); // expand this to every, on and so on...
WHITESPACE: (' ' | '\t')+ -> skip;
NEWLINE: ('\r'? '\n' | '\r')+;