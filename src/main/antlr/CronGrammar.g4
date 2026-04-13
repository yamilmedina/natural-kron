grammar CronGrammar;

options {
    caseInsensitive = true;
}

cron        : schedule EOF;
schedule    : EVERY daySelector AT timeClause;
daySelector : WORKDAY | WEEKDAY | WEEKDAYS | DAY_ALIAS | SPECIFIC_DAY;
timeClause  : TIME;

EVERY : 'every';
AT : 'at';

WORKDAY : 'workday';
WEEKDAY : 'weekday';
WEEKDAYS : 'weekdays';
DAY_ALIAS : 'day' | 'daily' | 'everyday';

SPECIFIC_DAY
    : 'monday'
    | 'tuesday'
    | 'wednesday'
    | 'thursday'
    | 'friday'
    | 'saturday'
    | 'sunday'
    | 'mon'
    | 'tue'
    | 'wed'
    | 'thu'
    | 'fri'
    | 'sat'
    | 'sun'
    ;

TIME : DIGIT DIGIT? ':' DIGIT DIGIT;

fragment DIGIT : [0-9];

WS : [ \t\r\n]+ -> skip;
