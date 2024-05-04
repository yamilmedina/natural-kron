grammar NaturalCron;

options {
    caseInsensitive = true;
}

// Parser rules
naturalCron: date at clock;
at: AT;
date: STANDARD_SCHEDULE | (DYNAMIC_SCHEDULE DAYS | DYNAMIC_FROM_NOW_SCHEDULE);
clock: HOUR;

// Lexer Rules
fragment INT: [0-9]+;
HOUR: INT ':' INT;
DAYS: ('monday' | 'tuesday' | 'wednesday' | 'thursday' | 'friday' | 'saturday' | 'sunday');
AT: ('at' | 'AT');
STANDARD_SCHEDULE: ('hourly' | 'daily' | 'weekly' | 'yearly');
DYNAMIC_SCHEDULE: ('every' | 'every' | 'each');
DYNAMIC_FROM_NOW_SCHEDULE: ('every day' | 'every week' | 'every month');
WHITESPACE: (' ' | '\t')+ -> skip;
NEWLINE: ('\r'? '\n' | '\r')+;