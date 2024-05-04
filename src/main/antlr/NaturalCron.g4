grammar NaturalCron;

options {
    caseInsensitive = true;
}

// Parser rules
naturalCron: date at clock;
at: AT;
date: STANDARD_SCHEDULE | (DYNAMIC_SCHEDULE DAYS | DYNAMIC_FROM_NOW_SCHEDULE);
clock: FULL_HOUR | SHORT_HOUR;

// Lexer Rules
fragment INT: [0-9];
FULL_HOUR: INT INT? ':' INT INT;
SHORT_HOUR: [0-2]? INT? 'hr' 's'? ;
DAYS: ('monday' | 'tuesday' | 'wednesday' | 'thursday' | 'friday' | 'saturday' | 'sunday');
AT: ('at' | 'AT');
STANDARD_SCHEDULE: ('hourly' | 'daily' | 'weekly' | 'yearly');
DYNAMIC_SCHEDULE: ('every' | 'every' | 'each');
DYNAMIC_FROM_NOW_SCHEDULE: ('every day' | 'every week' | 'every month');
WHITESPACE: (' ' | '\t')+ -> skip;
NEWLINE: ('\r'? '\n' | '\r')+;