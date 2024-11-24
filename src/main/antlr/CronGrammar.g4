grammar CronGrammar;

options {
    caseInsensitive = true;
}

// Parser rules
cron    : everyWorkday | everySpecificDay;
everyWorkday : 'every' 'workday' 'at' TIME;
everySpecificDay : 'every' SPECIFIC_DAY 'at' TIME;

// Lexer rules
SPECIFIC_DAY          : 'monday' | 'tuesday' | 'wednesday' | 'thursday' | 'friday' | 'saturday' | 'sunday' | 'day';
TIME         : [0-2][0-9] ':' [0-5][0-9];
INT          : [0-9]+;
TIME_UNIT    : 'seconds' | 'minutes' | 'hours';
WS           : [ \t\r\n]+ -> skip;
